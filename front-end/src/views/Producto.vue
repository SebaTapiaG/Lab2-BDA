<template>
  <div>
    <h1>Gestión de Productos</h1>
    
    <!-- Listado de productos -->
    <div v-if="productos.length">
      <h2>Lista de Productos</h2>
      <ul>
        <li v-for="producto in productos" :key="producto.id_producto">
          {{ producto.nombre }} - {{ producto.precio }}
          <button @click="eliminarProducto(producto.id_producto)">Eliminar</button>
          <button @click="seleccionarProducto(producto)">Editar</button>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>No hay productos disponibles.</p>
    </div>

    <!-- Formulario para crear/editar productos -->
    <h2>{{ productoSeleccionado.id_producto ? "Editar Producto" : "Crear Producto" }}</h2>
    <form @submit.prevent="guardarProducto">
      <div>
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" v-model="productoSeleccionado.nombre" required />
      </div>
      <div>
        <label for="precio">Precio:</label>
        <input type="number" id="precio" v-model="productoSeleccionado.precio" required />
      </div>
      <div>
        <label for="categoria">Categoría (ID):</label>
        <input type="number" id="categoria" v-model="productoSeleccionado.id_categoria" required />
      </div>
      <button type="submit">{{ productoSeleccionado.id_producto ? "Actualizar" : "Crear" }}</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      productos: [], // Lista de productos
      productoSeleccionado: {
        id_producto: null,
        nombre: "",
        precio: "",
        id_categoria: "",
      }, // Producto en edición/creación
      apiUrl: "http://localhost:8080/api/producto", // URL del backend
    };
  },
  methods: {
    // Obtener todos los productos
    async cargarProductos() {
      try {
        const response = await axios.get(this.apiUrl);
        this.productos = response.data;
      } catch (error) {
        console.error("Error al cargar los productos:", error);
      }
    },
    // Guardar producto (crear o actualizar)
    async guardarProducto() {
      try {
        if (this.productoSeleccionado.id_producto) {
          // Actualizar producto
          await axios.put(`${this.apiUrl}/producto/update`, this.productoSeleccionado);
          alert("Producto actualizado correctamente.");
        } else {
          // Crear producto
          await axios.post(`${this.apiUrl}/producto/create`, this.productoSeleccionado);
          alert("Producto creado correctamente.");
        }
        this.cargarProductos();
        this.limpiarFormulario();
      } catch (error) {
        console.error("Error al guardar el producto:", error);
      }
    },
    // Eliminar producto
    async eliminarProducto(id_producto) {
      try {
        await axios.delete(`${this.apiUrl}/producto/delete/${id_producto}`);
        alert("Producto eliminado correctamente.");
        this.cargarProductos();
      } catch (error) {
        console.error("Error al eliminar el producto:", error);
      }
    },
    // Seleccionar producto para edición
    seleccionarProducto(producto) {
      this.productoSeleccionado = { ...producto };
    },
    // Limpiar el formulario
    limpiarFormulario() {
      this.productoSeleccionado = {
        id_producto: null,
        nombre: "",
        precio: "",
        id_categoria: "",
      };
    },
  },
  created() {
    // Cargar productos al iniciar
    this.cargarProductos();
  },
};
</script>

<style>
h1 {
  text-align: center;
}
form {
  margin-top: 20px;
}
form div {
  margin-bottom: 10px;
}
button {
  margin-right: 5px;
}
</style>
