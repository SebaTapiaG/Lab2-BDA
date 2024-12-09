<template>
	<div v-for="(order, index) in orders" class="card" :key="order.id_orden">
		<Card>
        <template #title>N° de orden: {{order.id_orden}}</template>
        <template #content>
						<p>Fecha: {{ order.fecha_orden }}</p>
						<p>Estado: {{ order.estado }}</p>
						<p>Costo total: {{ order.total }}$</p>
						<Button @click="">Ver detalles</Button>
						<br>
						<br>
						<span v-if="order.estado === 'pendiente'">
							<Button @click="pagarOrden(index)">Pagar</Button>
						</span>	
        </template>
    </Card>
	</div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { Button, InputNumber, Card } from "primevue";
import { jwtDecode } from "jwt-decode";
import axios from 'axios';

const orders = ref([]);

onMounted(async () => {
  try {
		const id = sessionStorage.getItem('userId')
    const response = await axios.get(`http://localhost:8080/api/orden/findByCliente/${id}`)
		orders.value = response.data
  } catch (error) {
    console.error('Error fetching products:', error);
  }
});

async function pagarOrden(index){
	const order = orders.value[index]
	console.log(order)
	const newOrder = {
										id_orden: order.id_orden,
										fecha_orden: order.fecha_orden,
										estado: "pagada",
										id_cliente: order.id_cliente,
										total: order.total
										}

	try{
		console.log(newOrder)
		const response = await axios.put('http://localhost:8080/api/orden/update', newOrder)
		if(response.data.estado === "fallida"){
			window.alert("No se puede realizar esta compra, por lo tanto se eliminara la orden")
			window.location.reload()
		}
		
	}catch(error){
		console.log(error)
	}


}

</script>