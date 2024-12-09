<template>
  <div class="home">
    <header class="hero">
      <h1>Bienvenido a nuestra tienda</h1>
      <p>Encuentra los mejores productos al mejor precio</p>
    </header>

    <div class="top-products">
      <h2>Productos más comprados</h2>
      <div v-if="products.length === 0" class="loading-message">
        Cargando productos...
      </div>
      <ul v-else>
        <li v-for="producto in products" :key="producto.id_producto">
          <p><strong>{{ producto.nombre }}</strong></p>
          <p>Cantidad comprada: {{ producto.total_comprado }}</p>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

// Definir una propiedad reactiva para los productos
const products = ref([]);

// Hacer la solicitud HTTP cuando el componente se monta
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/producto/top');
    if (response.status === 200) {
      products.value = response.data;
    }
  } catch (error) {
    console.error("Error al obtener los productos", error);
  }
});
</script>

<style scoped>
.home {
  padding: 20px;
  font-family: 'Arial', sans-serif;
  color: #333;
  background-color: #f9f9f9; 
}

.hero {
  text-align: center;
  margin-bottom: 40px;
  background-color: #4caf50; 
  color: white;
  padding: 40px 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); 
}

.hero h1 {
  font-size: 2.5rem;
  margin: 0;
}

.hero p {
  font-size: 1.2rem;
  margin: 10px 0 0;
}

.top-products {
  margin-top: 40px;
}

.top-products h2 {
  font-size: 1.8rem;
  margin-bottom: 20px;
}

.top-products ul {
  list-style: none;
  padding: 0;
}

.top-products li {
  padding: 10px;
  border: 1px solid #ddd;
  margin-bottom: 10px;
  background-color: #fff;
  border-radius: 5px;
}

.loading-message {
  font-size: 1.2rem;
  color: #888;
}

@media (min-width: 768px) {
  .hero h1 {
    font-size: 3rem;
  }

  .hero p {
    font-size: 1.5rem;
  }
}
</style>
