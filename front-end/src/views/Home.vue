<template>
  <div class="home-container">
    <h1>Bienvenido a la tienda</h1>
    <p>
      E-commerce
    </p>
    <div class="features">
    </div>
    <footer>
      <p>© 2024 Hola. Todos los derechos reservados.</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: "HomeView",
};
</script>

<style scoped>
.home-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
h1 {
  color: #333;
  font-size: 2.5rem;
  margin-bottom: 10px;
}
p {
  font-size: 1.2rem;
  color: #555;
}
.features {
  margin-top: 20px;
  text-align: left;
}
.features h2 {
  color: #007bff;
  font-size: 1.5rem;
}
.features ul {
  list-style: none;
  padding: 0;
}
.features li {
  margin-bottom: 10px;
  font-size: 1.1rem;
  color: #333;
}
.actions {
  margin-top: 30px;
}
.action-button {
  display: inline-block;
  margin: 10px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border-radius: 5px;
  text-decoration: none;
  font-weight: bold;
  transition: background-color 0.3s ease;
}
.action-button:hover {
  background-color: #0056b3;
}
footer {
  margin-top: 30px;
  font-size: 0.9rem;
  color: #777;
}
</style>
