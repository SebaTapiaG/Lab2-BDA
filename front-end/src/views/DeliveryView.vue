<template>
<div>
    <h1>Ordenes disponibles</h1>
    
    <!-- Listado de Ordenes -->
    <div v-if="ordenes.length">
      <h2>Lista de Ordenes</h2>
      <ul>
        <li v-for="orden in ordenes" :key="orden.id_orden">
          {{ orden.id_orden }} - {{ orden.fecha }} - {{ orden.id_cliente }} - {{ orden.id_producto }} - {{ orden.cantidad }}
          <button @click="seleccionarOrden(orden)">Seleccionar</button>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>No hay ordenes disponibles.</p>
    </div>

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
    };
  },
  methods: {
    // Obtener todas las órdenes
    async cargarOrdenes() {
      try {
        const response = await ordenesService.getAll();
        this.ordenes = response.data;
      } catch (error) {
        console.error("Error al cargar las órdenes:", error);
      }
    },
    // Seleccionar una orden
    seleccionarOrden(orden) {
      this.ordenSeleccionada = { ...orden }; // Clonar la orden seleccionada
    },
    // Marcar una orden como completada
    async marcarComoCompletada() {
      try {
        if (this.ordenSeleccionada.id_orden) {
          await ordenesService.update(this.ordenSeleccionada.id_orden);
          alert("Orden marcada como completada.");
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
  },
  created() {
    // Cargar las órdenes al iniciar
    this.cargarOrdenes();
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