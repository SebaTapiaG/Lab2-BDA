<template>
<div>
    <h1>Ordenes disponibles</h1>
    
    <!-- Listado de Ordenes -->
    <div v-if="ordenes.length" class="map-container">
      <label for="orden">Ordenes por repartir:</label>
			<br>
			<select v-model="ordenSeleccionada" id="orden">
				<option disabled value="">Selecciona una orden</option>
				<option v-for="(orden, index) in ordenes" :key="index" :value="orden">
					{{ orden.id_orden }} <!-- Muestra correctamente el id de la orden -->
				</option>
			</select>
		<br>
		<button @click="seleccionarOrden(ordenSeleccionada)">Seleccionar</button>
    </div>
    <div v-else>
      <p>No hay ordenes disponibles.</p>
    </div>
		<br>

		<div class="map-container"><div id="map" style="height: 500px;"></div></div>

    <!-- Confirmar Entrega -->
    <div v-if="ordenSeleccionada" class="confirmacion-container">
        <h2>Confirmar Entrega</h2>
        <p>Estás entregando la orden {{ ordenSeleccionada.id_orden }}.</p>
        <button @click="marcarComoCompletada">Marcar como Completada</button>
        <button @click="cancelarSeleccion">Cancelar</button>
      </div>
    </div>
</template>

<script>
import ordenesService from '@/services/orden.service';

export default {
  data() {
    return {
      ordenes: [], // Lista de órdenes cargadas desde el backend
      ordenSeleccionada: null, // Orden seleccionada por el repartidor		
			map: null,
    };
  },
  methods: {
    // Obtener todas las órdenes
    async cargarOrdenes() {
      try {
        const response = await ordenesService.getPagadas();
        this.ordenes = response.data;
      } catch (error) {
        console.error("Error al cargar las órdenes:", error);
      }
    },
    // Seleccionar una orden
    seleccionarOrden(orden) {
			console.log(orden)
			this.ordenSeleccionada = orden;
			this.mostrarPunto(this.ordenSeleccionada.latitud, this.ordenSeleccionada.longitud)
    },
    // Marcar una orden como completada
    async marcarComoCompletada() {
			console.log(this.ordenSeleccionada)
      try {
        if (this.ordenSeleccionada.id_orden) {
					const id_repartidor = sessionStorage.getItem("userId")
          await ordenesService.updateEstado(this.ordenSeleccionada.id_orden, "enviada", id_repartidor);
          alert("Orden marcada como enviada.");
          this.cargarOrdenes(); // Actualiza la lista de órdenes
          this.cancelarSeleccion(); // Limpia la selección
        }
      } catch (error) {
        console.error("Error al completar la orden:", error);
        alert("Hubo un error al completar la orden.");
      }
    },
    // Cancelar la selección de una orden
    cancelarSeleccion() {
      this.ordenSeleccionada = null; // Limpia la orden seleccionada
    },
		 mostrarPunto(latitud, longitud) {

		// 4️⃣ Crear el marcador (punto) en la ubicación especificada
		const marker = L.marker([latitud, longitud]).addTo(this.map);

		// 5️⃣ Agregar un popup al marcador (opcional)
		marker.bindPopup(`<b>Punto mostrado</b><br>Latitud: ${latitud}, Longitud: ${longitud}`).openPopup();

		// 6️⃣ Centrar el mapa en el punto
		this.map.setView([latitud, longitud], 16);
}
  },
  created() {
    // Cargar las órdenes al iniciar
    this.cargarOrdenes();
  },
	mounted() {
      // Inicializar mapa
      this.map = L.map('map').setView([0, 0], 2);
  
      // Añadir capa base de OpenStreetMap
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      }).addTo(this.map);
    },
};
</script>

<style scoped>
h1 {
  text-align: center;
}
.orden-item {
  margin: 0.5rem 0;
  padding: 0.5rem;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 5px;
}
.confirmacion-container {
  margin-top: 1rem;
  padding: 1rem;
  background-color: #eaf4fc;
  border: 1px solid #cce0f1;
  border-radius: 5px;
}
button {
  margin-right: 5px;
}
</style>