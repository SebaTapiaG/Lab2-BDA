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
	<div class="button">
		<Button @click="guardarOrden">Guardar orden</Button>
	</div>
	<div class="button">
		<Button @click="comprarOrden">Comprar orden</Button>
	</div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { Button, InputNumber, Card } from "primevue";
import { jwtDecode } from "jwt-decode";
import axios from 'axios';

const products = ref([]);
const precio = ref([]);
const values = new Array(products.length).fill(null);


onMounted(async () => {
	try {
		const misProductos = sessionStorage.getItem('carrito')
		products.value = misProductos ? JSON.parse(misProductos) : [];
		var price = 0;
		for (const product of products.value) {
			price += product[3]
		}
		precio.value = price;
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
		};

		try {
			// Crear la orden
			const response = await axios.post('http://localhost:8080/api/orden/create', orden);

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
					const response2 = await axios.post('http://localhost:8080/api/detalle_orden/create', detalle);
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
		};

		try {
			// Crear la orden
			const response = await axios.post('http://localhost:8080/api/orden/create', orden);

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
					const response2 = await axios.post('http://localhost:8080/api/detalle_orden/create', detalle);
				} catch (error) {
					// Capturar errores del trigger por falta de stock
					console.error("Error al crear detalle de la orden:", error.response.data.message || error.message);
					alert(`Error: ${error.response.data.message || 'La orden no se pudo crear debido a falta de stock'}`);
					return; // Salir para evitar guardar la orden en caso de error
				}
			}
			const update = await axios.put('http://localhost:8080/api/orden/update', response.data)

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


</script>