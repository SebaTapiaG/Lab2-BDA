<template>
  <div class="home">
    <header class="hero">
      <h1>Bienvenido a nuestra tienda</h1>
      <p>Encuentra los mejores productos al mejor precio</p>
    </header>

    <section class="featured-products">
      <h2>Productos Destacados</h2>
      <div class="product-grid">
        <ProductCard
            v-for="product in featuredProducts"
            :key="product.id"
            :id="product.id"
            :name="product.name"
            :price="product.price"
            :imgId="product.imgId"
        />
      </div>
    </section>

    <section class="categories">
      <h2>Categorías</h2>
      <div class="category-grid">
        <CategoryCard
            v-for="category in categories"
            :key="category.id"
            :name="category.name"
            :imgId="category.imgId"
        />
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import ProductCard from '@/components/ProductCard.vue';
import CategoryCard from '@/components/CategoryCard.vue';

const featuredProducts = ref([]);
const categories = ref([]);

async function fetchFeaturedProducts() {
  // Aquí realizarías la llamada a tu API para obtener productos destacados
  featuredProducts.value = await fetch('/api/featured-products').then(res => res.json());
}

async function fetchCategories() {
  // Aquí realizarías la llamada a tu API para obtener categorías
  categories.value = await fetch('/api/categories').then(res => res.json());
}

onMounted(() => {
  fetchFeaturedProducts();
  fetchCategories();
});
</script>

<style scoped>
.home {
  padding: 20px;
}

.hero {
  text-align: center;
  margin-bottom: 40px;
}

.featured-products, .categories {
  margin-bottom: 40px;
}

.product-grid, .category-grid {
  display: grid;
  gap: 20px;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
}
</style>
