<template>
  <div class="map-container">
    <div>
      <h2>Calcula el punto medio entre dos clientes</h2>
      <div class="inputs">
        <!-- Seleccionar cliente 1 -->
        <label for="client1">Cliente 1:</label>
        <select v-model="client1Id" id="client1">
          <option disabled value="">Selecciona un cliente</option>
          <option v-for="cliente in clientes" :key="cliente.id_cliente" :value="cliente.id_cliente">
            {{ cliente.nombre }} - {{ cliente.telefono }}
          </option>
        </select>

        <!-- Seleccionar cliente 2 -->
        <label for="client2">Cliente 2:</label>
        <select v-model="client2Id" id="client2">
          <option disabled value="">Selecciona un cliente</option>
          <option v-for="cliente in clientes" :key="cliente.id_cliente" :value="cliente.id_cliente">
            {{ cliente.nombre }} - {{ cliente.telefono }}
          </option>
        </select>

        <Button @click="fetchMidpoint">Calcular Punto Medio</Button>
      </div>
    </div>
		<div>
			<h2>Busca los repartidores por zona:</h2>
			<label for="client1">Zonas:</label>
        <select v-model="zonaSeleccionada" id="client1">
          <option disabled value="">Selecciona una zona</option>
          <option v-for="(zona, index) in zonas" :key="index" :value="zona">
            - {{ zona.nombre }}
          </option>
        </select>
				
				<ButtonGroup>
					<Button @click="mostrarZona(zonaSeleccionada)">Ver zona</Button>
					<Button @click="buscarRepartidores">Ver repartidores</Button>
					<Button @click="limpiarPoligono">Limpiar zona</Button>
				</ButtonGroup>

				<br>
				<br>
				<h2>Crear zona:</h2>
				<label for="nombreZona">Nombre:</label>
				<input type="text" v-model="nombreZona" style="margin-right: 10px; height: 35px;"> </input>
				<ButtonGroup>
					<span v-if=" 3 <puntos.length ">
						<Button @click="crearPoligono">Mostrar poligono</Button>
						<Button @click="crearZona">Crear zona</Button>
						
					</span>
				</ButtonGroup>
		</div>
		<br>

    <!-- Mapa -->
    <div id="map" style="height: 500px;"></div>
		<br>
		<div v-if="repartidores.length" class="map-container">
			<h2>Repartidores {{ zonaSeleccionada.nombre }}</h2>
			<div v-for="repartidor in repartidores">
				{{ repartidor.nombre }}
			</div>
		</div>
  </div>
</template>

<script>
import L from 'leaflet';
import clientService from '../services/client.service';
import ubicacionIcon from '@/assets/ubicacion.png';
import ubicacionPuntoMedio from '@/assets/ubicacion_pm.png';
import zonaService from '@/services/zona.service';
import Button  from 'primevue/button';
import ButtonGroup from 'primevue/buttongroup';
import Select from 'primevue/select';

export default {
  name: 'MapView',
	components:{
		Button,
		ButtonGroup,
		Select
	},
  data() {
    return {
      clientes: [],
      client1Id: null,
      client2Id: null,
      map: null,
			puntos: [],
			poligono: [],
			nombreZona: null,
			zonas: [],
			repartidores: [],
			zonaSeleccionada: null,
      midpointMarker: null,
      client1Marker: null,
      client2Marker: null,
      client1Icon: L.icon({
        iconUrl: ubicacionIcon,
        iconSize: [48, 48],
        iconAnchor: [16, 32],
        popupAnchor: [0, -32],
      }),
      client2Icon: L.icon({
        iconUrl: ubicacionIcon,
        iconSize: [48, 48],
        iconAnchor: [16, 32],
        popupAnchor: [0, -32],
      }),
      puntoMedioIcon: L.icon({
        iconUrl: ubicacionPuntoMedio,
        iconSize: [48, 48],
        iconAnchor: [16, 32],
        popupAnchor: [0, -32],
      }),
    };
  },
  methods: {
    async fetchClientes() {
      try {
        const response = await clientService.getAll();
        this.clientes = response.data;
      } catch (error) {
        console.error('Error al obtener la lista de clientes:', error);
        alert('No se pudo obtener la lista de clientes.');
      }
    },
    async fetchMidpoint() {
      try {
        if (!this.client1Id || !this.client2Id) {
          alert('Por favor, selecciona ambos clientes.');
          return;
        }

        const client1Response = await clientService.get(this.client1Id);
        const client2Response = await clientService.get(this.client2Id);

        const client1 = client1Response.data;
        const client2 = client2Response.data;

        const client1Coords = [client1.latitud, client1.longitud];
        const client2Coords = [client2.latitud, client2.longitud];

        if (!this.client1Marker) {
          this.client1Marker = L.marker(client1Coords, { icon: this.client1Icon })
            .addTo(this.map)
            .bindPopup(`<b>${client1.nombre}</b>`)
            .openPopup();
        } else {
          this.client1Marker.setLatLng(client1Coords).setPopupContent(`<b>${client1.nombre}</b>`).openPopup();
        }

        if (!this.client2Marker) {
          this.client2Marker = L.marker(client2Coords, { icon: this.client2Icon })
            .addTo(this.map)
            .bindPopup(`<b>${client2.nombre}</b>`)
            .openPopup();
        } else {
          this.client2Marker.setLatLng(client2Coords).setPopupContent(`<b>${client2.nombre}</b>`).openPopup();
        }

        const puntoMedioWKT = await clientService.puntoMedio(this.client1Id, this.client2Id);
        if (!puntoMedioWKT) {
          alert('No se encontró el punto medio.');
          return;
        }

        const coordinates = puntoMedioWKT
          .replace('POINT(', '')
          .replace(')', '')
          .split(' ')
          .map(coord => parseFloat(coord));
        const [lng, lat] = coordinates;

        if (!this.midpointMarker) {
          this.midpointMarker = L.marker([lat, lng], { icon: this.puntoMedioIcon })
            .addTo(this.map)
            .bindPopup('<b>Punto Medio</b>')
            .openPopup();
        } else {
          this.midpointMarker.setLatLng([lat, lng]).setPopupContent('<b>Punto Medio</b>').openPopup();
        }

        const bounds = L.latLngBounds([client1Coords, client2Coords, [lat, lng]]);
        this.map.fitBounds(bounds);
      } catch (error) {
        console.error('Error al obtener el punto medio:', error);
        alert('Ocurrió un error al calcular el punto medio.');
      }
    },
		async cargarZonas() {
      try {
        const response = await zonaService.getByEstado("Disponible");
        this.zonas = response.data;
      } catch (error) {
        console.error("Error al cargar las zonas:", error);
      }
    },
		mostrarZona(zona){
			console.log(zona)
			function wktToLeafletCoordinates(wkt) {
			const coordinatesText = wkt.match(/\(\(([^)]+)\)\)/)[1]; // Extraer el texto entre (())
			const coordinatePairs = coordinatesText.split(','); // Separar las coordenadas
			const leafletCoordinates = coordinatePairs.map(pair => {
				const [lng, lat] = pair.trim().split(' ').map(Number);
				return [lat, lng]; // Leaflet usa [lat, lng]
			});
			return leafletCoordinates;
		}
			if (this.poligono) this.map.removeLayer(this.poligono);
			const polygonCoordinates = wktToLeafletCoordinates(zona.area); // Convertir WKT a coordenadas Leaflet
			const polygon = L.polygon(polygonCoordinates, {
				color: 'blue',               // Color del borde
				fillColor: 'lightblue',        // Color de fondo
				fillOpacity: 0.5            // Opacidad del relleno
			}).addTo(this.map);

			this.poligono = polygon

			this.map.fitBounds(this.poligono.getBounds());

			// 6️⃣ Agregar un popup al hacer clic en el polígono
			polygon.bindPopup(`<b>${zona.nombre}</b><br>Estado: ${zona.estado}`);

			},
		async buscarRepartidores(){
			try{
				const response = await zonaService.findRepartidores(this.zonaSeleccionada.id)
				this.repartidores = response.data
				console.log(response.data)
			}catch(error){
				console.log(error)
			}

		},
		crearPoligono() {
		if (this.puntos.length >= 3) {
			if (this.poligono) this.map.removeLayer(this.poligono);
			this.poligono = L.polygon(this.puntos, {
				color: 'blue',
				fillColor: 'cyan',
				fillOpacity: 0.5
			}).addTo(this.map);
			this.map.fitBounds(this.poligono.getBounds());
			this.map.eachLayer(layer => {
			if (layer instanceof L.Marker) {
				this.map.removeLayer(layer);
			}})
		} else {
			alert('Se necesitan al menos 3 puntos para crear un polígono.');
		}
	},
	limpiarPoligono(){
		if (this.poligono) this.map.removeLayer(this.poligono);
		this.poligono = []
		this.puntos = []

		this.map.eachLayer(layer => {
    if (layer instanceof L.Marker) {
      this.map.removeLayer(layer);
    }
  });
	},
	async crearZona(){
		function convertirACoordenadasWKT(coordenadas ) {
			if (!Array.isArray(coordenadas) || coordenadas.length === 0) {
				throw new Error('El arreglo de coordenadas es inválido o está vacío');
			}
				// Asegurarnos de que el polígono esté cerrado (el primer y último punto deben coincidir)
				const primerPunto = coordenadas[0];
				const ultimoPunto = coordenadas[coordenadas.length - 1];
				if (primerPunto[0] !== ultimoPunto[0] || primerPunto[1] !== ultimoPunto[1]) {
					coordenadas.push(primerPunto); // Cierra el polígono automáticamente
				}
				const coordenadasTexto = coordenadas.map(([lng, lat]) => `${lat} ${lng}`).join(', ');
				return `SRID=4326;POLYGON((${coordenadasTexto}))`;
			

			throw new Error('Tipo de WKT no soportado. Usa POINT, LINESTRING o POLYGON');
		}

		console.log(this.puntos.length)

		const areaZona = convertirACoordenadasWKT(this.puntos)

		const zona = {
			nombre: this.nombreZona,
			estado: "Disponible",
			area: convertirACoordenadasWKT(this.puntos)
		}

		console.log(areaZona)

		await zonaService.create(zona)

		this.cargarZonas()

	}
  },
    mounted() {
      // Inicializar mapa
      this.map = L.map('map').setView([0, 0], 2);
  
      // Añadir capa base de OpenStreetMap
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      }).addTo(this.map);

			this.map.on('click', (e) => {
				const { lat, lng } = e.latlng; // Obtener latitud y longitud del clic
				this.puntos.push([lat, lng]); // Agregar el punto a la lista
				L.marker([lat, lng]).addTo(this.map) // Mostrar un marcador en la posición clicada
					.bindPopup(`Punto ${this.puntos.length}: [${lat.toFixed(5)}, ${lng.toFixed(5)}]`)
					.openPopup();
			});
  
      // Obtener la lista de clientes al cargar el componente
      this.fetchClientes();
			this.cargarZonas();
    },
  };
</script>

<style>
.map-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

h2 {
  text-align: center;
  margin-bottom: 1rem;
}

.inputs {
  margin-bottom: 1rem;
}

#map {
  width: 80%;
  border: 1px solid #ccc;
}
</style>
