<template>
	<div v-for="product in products" class="card">
		<Card>
			<template #title>{{ product[1] }}</template>
			<template #content>
				<p>Cantidad: {{ product[2] }} unidad(es)</p>
				<p>Precio: {{ product[3] }}$</p>
			</template>
		</Card>
	</div>
	<div class="text">
		<p>Costo total: {{ precio }}</p>
	</div>
	<div class="map-container">
		<h4>Dirección de envio:</h4>
		<div id="map" style="height: 500px;"></div>
	</div>
	<div class="button">
		<Button @click="guardarOrden">Guardar orden</Button>
	</div>
	<div class="button">
		<Button @click="comprarOrden">Comprar orden</Button>
	</div>
	<div class="button">
		<Button @click="verCoordenadas">Ver coordenadas</Button>
	</div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { Button, InputNumber, Card } from "primevue";
import { jwtDecode } from "jwt-decode";
import ordenService from '../services/orden.service';
import detalleOrdenService from '../services/detalleOrden.service';
import zonaService from "@/services/zona.service";
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

import 'leaflet-control-geocoder/dist/Control.Geocoder.css';
import 'leaflet-control-geocoder';
import axios from "axios";

const products = ref([]);
const precio = ref([]);
const zonasRestringidas = ref([]);
const values = new Array(products.length).fill(null);
var map;
var latitud;
var longitud;


onMounted(async () => {
	try {
		// Importar Leaflet y Geocoder desde la CDN
    const L = window.L;

		const misProductos = sessionStorage.getItem('carrito')
		products.value = misProductos ? JSON.parse(misProductos) : [];
		var price = 0;
		for (const product of products.value) {
			price += product[3]
		}
		precio.value = price;

		
		const response = await zonaService.getByEstado("Restringida")
		zonasRestringidas.value = response.data
		console.log(response.data)

		// 1️⃣ Inicializar el mapa
    map = L.map('map').setView([0, 0], 2); // Vista inicial (lat, lng, zoom)

    // 2️⃣ Agregar la capa de OpenStreetMap
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(map);

    // 3️⃣ Agregar la barra de búsqueda (Leaflet Control Geocoder)
    const geocoder = L.Control.geocoder({
      defaultMarkGeocode: true
    }).addTo(map);

		function wktToLeafletCoordinates(wkt) {
    const coordinatesText = wkt.match(/\(\(([^)]+)\)\)/)[1]; // Extraer el texto entre (())
    const coordinatePairs = coordinatesText.split(','); // Separar las coordenadas
    const leafletCoordinates = coordinatePairs.map(pair => {
      const [lng, lat] = pair.trim().split(' ').map(Number);
      return [lat, lng]; // Leaflet usa [lat, lng]
    });
    return leafletCoordinates;
  }

  // 5️⃣ Mostrar cada polígono en el mapa
  zonasRestringidas.value.forEach(zona => {
    const polygonCoordinates = wktToLeafletCoordinates(zona.area); // Convertir WKT a coordenadas Leaflet
    const polygon = L.polygon(polygonCoordinates, {
      color: 'red',               // Color del borde
      fillColor: 'orange',        // Color de fondo
      fillOpacity: 0.5            // Opacidad del relleno
    }).addTo(map);

    // 6️⃣ Agregar un popup al hacer clic en el polígono
    polygon.bindPopup(`<b>${zona.nombre}</b><br>Estado: ${zona.estado}`);
  });

    // 4️⃣ Evento para manejar la acción de búsqueda
    geocoder.on('markgeocode', (e) => {
      const latlng = e.geocode.center; // Coordenadas de la dirección encontrada
      const popupContent = `Dirección encontrada: ${e.geocode.name}`;
			latitud = latlng.lat;
			longitud = latlng.lng;

      // Agregar un marcador en la ubicación encontrada
      L.marker(latlng).addTo(map)
        .bindPopup(popupContent)
        .openPopup();

      // Centrar el mapa en la ubicación encontrada
      map.setView(latlng, 16);
    });
	} catch (error) {
		console.error('Error fetching products:', error);
	}
});

async function guardarOrden() {
	const idUsuario = sessionStorage.getItem('userId');
	const token = sessionStorage.getItem('user');
	const tokenId = jwtDecode(token).user_id;
	console.log(tokenId);
	console.log(tokenId === idUsuario);

	if (true) {
		const fecha = new Date();
		const fechaISO = fecha.toISOString(); // Formato 'yyyy-MM-ddTHH:mm:ss.SSSZ'
		const fechaSinMilisegundos = fechaISO.split('.')[0];
		console.log(fechaSinMilisegundos);

		const orden = {
			fecha_orden: "",
			estado: "pendiente",
			id_cliente: idUsuario,
			total: precio.value,
			latitud: latitud,
			longitud: longitud
		};

		try {
			// Crear la orden
			const response = await ordenService.create(orden);

			const misProductos = sessionStorage.getItem('carrito');
			const productos = misProductos ? JSON.parse(misProductos) : [];
			for (const producto of productos) {
				const detalle = {
					id_orden: response.data.id_orden,
					id_producto: producto[0],
					cantidad: producto[2],
					precio_unitario: producto[3] / producto[2],
				};

				try {
					// Crear cada detalle de la orden
					const response2 = await detalleOrdenService.create(detalle);
				} catch (error) {
					// Capturar errores del trigger por falta de stock
					console.error("Error al crear detalle de la orden:", error.response.data.message || error.message);
					alert(`Error: ${error.response.data.message || 'La orden no se pudo crear debido a falta de stock'}`);
					return; // Salir para evitar guardar la orden en caso de error
				}
			}

			

			// Limpiar el carrito y recargar la página
			sessionStorage.setItem('carrito', []);
			alert("Orden creada correctamente");
			window.location.reload(); // Recargar la página

		} catch (error) {
			console.error("Error al crear la orden:", error.response?.data?.message || error.message);
			alert(`Error: ${error.response?.data?.message || 'No se pudo crear la orden'}`);
		}
	}
}

async function comprarOrden() {
	const idUsuario = sessionStorage.getItem('userId');
	const token = sessionStorage.getItem('user');
	const tokenId = jwtDecode(token).user_id;
	console.log(tokenId);
	console.log(tokenId === idUsuario);

	if (true) {
		const fecha = new Date();
		const fechaISO = fecha.toISOString(); // Formato 'yyyy-MM-ddTHH:mm:ss.SSSZ'
		const fechaSinMilisegundos = fechaISO.split('.')[0];
		console.log(fechaSinMilisegundos);

		const orden = {
			fecha_orden: "",
			estado: "pagada",
			id_cliente: idUsuario,
			total: precio.value,
			latitud: latitud,
			longitud: longitud
		};

		try {
			// Crear la orden
			const response = await ordenService.create(orden);

			const misProductos = sessionStorage.getItem('carrito');
			const productos = misProductos ? JSON.parse(misProductos) : [];
			for (const producto of productos) {
				const detalle = {
					id_orden: response.data.id_orden,
					id_producto: producto[0],
					cantidad: producto[2],
					precio_unitario: producto[3] / producto[2],
				};

				try {
					// Crear cada detalle de la orden
					const response2 = await detalleOrdenService.create(detalle);
					console.log(response2.data)
				} catch (error) {
					// Capturar errores del trigger por falta de stock
					console.error("Error al crear detalle de la orden:", error.response.data.message || error.message);
					alert(`Error: ${error.response.data.message || 'La orden no se pudo crear debido a falta de stock'}`);
					return; // Salir para evitar guardar la orden en caso de error
				}
			}
			console.log(response.data)

			const up = await ordenService.update(response.data);

			// Limpiar el carrito y recargar la página
			sessionStorage.setItem('carrito', []);
			alert("Orden creada correctamente");
			window.location.reload(); // Recargar la página

		} catch (error) {
			console.error("Error al crear la orden:", error.response?.data?.message || error.message);
			alert(`Error: ${error.response?.data?.message || 'No se pudo crear la orden'}`);
		}
	}
}
async function verCoordenadas() {
	console.log(latitud, longitud)
}
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 500px;
}

::v-deep(.leaflet-control-geocoder-form input) {
  color: black !important; 
  background-color: white !important; 
  border: 1px solid #ddd !important; 
  font-size: 16px !important; 
  padding: 10px !important; 
  width: auto !important; 
  visibility: visible !important; 
}

::v-deep(.leaflet-control-geocoder-form) {
  position: relative !important; 
  z-index: 10000 !important; 
}
</style>