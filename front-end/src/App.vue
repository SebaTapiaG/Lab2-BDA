<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { auth } from "@/services/authService";
import { Menubar } from "primevue";
import 'leaflet/dist/leaflet.css';

const router = useRouter();

// Menú para usuarios no logueados
const itemsNotLogged = ref([
	{ label: "Home", icon: "pi pi-home", command: () => router.push("/") },
	{ label: "Registrarme", command: () => router.push("/register") },
	{ label: "Iniciar sesión", command: () => router.push("/login") },
	{ label: "Mapa", command: () => router.push("/mapa") },
]);

// Menú para usuarios logueados
const itemsLogged = ref([
	{ label: "Home", icon: "pi pi-home", command: () => router.push("/") },
	{ label: "Productos", command: () => router.push("/products") },
	{ label: "Mis órdenes", command: () => router.push("/myOrders") },
	{ label: "Mi carrito", command: () => router.push("/cart") },
	{
		label: "Logout",
		command: () => logout(),
	},
]);

// Computed para determinar el modelo dinámico del menú
const menuItems = computed(() => (auth.isLogged() ? itemsLogged.value : itemsNotLogged.value));

// Función de logout
function logout() {
	auth.logout(); // Llama al método logout del servicio
	router.push("/"); // Redirige al home después de hacer logout
	alert("Sesión cerrada exitosamente");
}
</script>

<template>
	<nav>
		<!-- Barra de navegación dinámica -->
		<Menubar :model="menuItems" />
	</nav>
	<router-view />
</template>

<style scoped>
nav {
	line-height: 1.5;
}

@media (min-width: 1024px) {
	nav {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}
}
</style>
