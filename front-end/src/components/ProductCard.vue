<template>
  <div class="product-card" @click="redirectToProduct">
    <h3 class="product-title">{{ props.name }}</h3>
    <div class="product-category">Categoría: {{ props.categoriaId }}</div>
    <div class="product-image">
      <img v-if="props.imgId" :src="'/' + props.imgId" alt="Imagen del producto" />
    </div>
    <div class="product-price">${{ props.precio }}</div>
    <div class="product-stock" :class="{ 'out-of-stock': props.stock === 0 }">
      <span v-if="props.stock > 0">{{ props.stock }} en stock</span>
      <span v-else>Sin stock</span>
    </div>
    <p class="product-description">{{ props.descripcion }}</p>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const props = defineProps({
  id: Number,
  name: String,
  imgId: String,
  descripcion: String,
  precio: Number,
  stock: Number,
  estado: String,
  categoriaId: Number,
});

const router = useRouter();
const productURL = `/product/${props.id}`;

function redirectToProduct() {
  router.push(productURL);
}
</script>

<style scoped>
.product-card {
  border: 1px solid #ddd;
  padding: 16px;
  text-align: center;
  cursor: pointer;
  transition: background-color 100ms ease-in;
  width: fit-content;
}

.product-card:hover {
  background-color: #f9f9f9;
}

.product-title {
  font-size: larger;
  font-weight: 600;
  margin: 8px 0;
}

.product-category {
  font-size: small;
  color: #666;
  margin-bottom: 8px;
}

.product-image img {
  max-width: 100%;
  height: auto;
}

.product-price {
  font-size: large;
  color: #333;
  font-weight: bold;
  margin: 8px 0;
}

.product-stock {
  font-size: small;
  color: #3b3b3b;
}

.out-of-stock {
  color: red;
  font-weight: bold;
}

.product-description {
  font-size: small;
  color: #555;
  margin-top: 8px;
}
</style>
