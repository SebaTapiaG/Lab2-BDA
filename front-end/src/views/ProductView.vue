<template>
  <div class="product-view">
    <h2 class="view-title">Detalles del Producto</h2>
    <ProductoComp
        v-if="product"
        :id="product.id"
        :name="product.name"
        :imgId="product.imgId"
        :descripcion="product.descripcion"
        :precio="product.precio"
        :stock="product.stock"
        :estado="product.estado"
        :categoriaId="product.categoriaId"
    />
    <div v-else class="loading">Cargando...</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import ProductoComp from '@/components/ProductCard.vue'; 

const route = useRoute();
const productId = route.params.id; // Obtiene el ID del producto de la URL

const product = ref(null);

async function fetchProductData() {
  try {
    const response = await fetch(`/api/products/${productId}`);
    if (!response.ok) {
      throw new Error('Error al obtener el producto');
    }
    product.value = await response.json();
  } catch (error) {
    console.error(error);
  }
}

onMounted(() => {
  fetchProductData();
});
</script>

<style scoped>
.product-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.view-title {
  margin-bottom: 20px;
  font-size: 24px;
  font-weight: bold;
}

.loading {
  font-size: 18px;
  color: #888;
}
</style>
