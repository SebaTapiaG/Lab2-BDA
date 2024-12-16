<template>
	<div v-for="(order, index) in orders" class="card" :key="order.id_orden">
		<Card>
			<template #title>N° de orden: {{ order.id_orden }}</template>
			<template #content>
				<p>Fecha: {{ order.fecha_orden }}</p>
				<p>Estado: {{ order.estado }}</p>
				<p>Costo total: {{ order.total }}$</p>
				<Button @click="verDetalles(order.id_orden)">Ver detalles</Button>

				<!-- Sección para mostrar los detalles de la orden -->
				<div v-if="selectedOrderId === order.id_orden" class="order-details">
					<h4>Detalles de la Orden:</h4>
					<ul>
						<li v-for="(detalle, idx) in orderDetails" :key="idx">
							{{ detalle.cantidad }} x {{ detalle.nombre }} - Precio total: {{ (detalle.cantidad *
								detalle.precio_unitario).toFixed(2) }}$

						</li>
					</ul>
				</div>

				<br><br>
				<span v-if="order.estado === 'pendiente'">
					<Button @click="pagarOrden(index)">Pagar</Button>
				</span>
			</template>
		</Card>
	</div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { Button, Card } from "primevue";
import axios from 'axios';
import detalleOrdenService from "../services/detalleOrden.service";
import ordenService from "../services/orden.service";

const orders = ref([]);
const orderDetails = ref([]); // Para almacenar los detalles de la orden
const selectedOrderId = ref(null); // Para identificar qué orden está seleccionada

onMounted(async () => {
	try {
		const id = sessionStorage.getItem('userId');
		const response = await ordenService.findByCliente(id);
		orders.value = response.data;
	} catch (error) {
		console.error('Error fetching products:', error);
	}
});

async function verDetalles(id_orden) {
	try {
		// Si ya está seleccionado, cerrarlo
		if (selectedOrderId.value === id_orden) {
			selectedOrderId.value = null; // Cerrar los detalles
			orderDetails.value = []; // Limpiar los detalles
			return;
		}

		const response = await detalleOrdenService.findProductosByOrdenId(id_orden);
		orderDetails.value = response.data; // Almacena los detalles obtenidos
		selectedOrderId.value = id_orden; // Establece el id de la orden seleccionada

	} catch (error) {
		console.error('Error fetching order details:', error);
		window.alert("Error al obtener los detalles de la orden.");
	}
}

async function pagarOrden(index) {
	const order = orders.value[index];
	const newOrder = {
		id_orden: order.id_orden,
		fecha_orden: order.fecha_orden,
		estado: "pagada",
		id_cliente: order.id_cliente,
		total: order.total
	};

	try {
		const response = await ordenService.update(newOrder);
		if (response.data.estado === "fallida") {
			window.alert("No se puede realizar esta compra, por lo tanto se eliminará la orden");
			window.location.reload();  // Recarga la página si la compra falla
		} else {
			// Si la orden fue pagada con éxito, mostrar la alerta
			window.alert("Pagado con éxito");
			// Recargar la página para mostrar la nueva información
			window.location.reload();  // Recarga la página después del pago
		}
	} catch (error) {
		console.log(error);
	}
}
</script>

<style scoped>
.order-details {
	margin-top: 10px;
}
</style>