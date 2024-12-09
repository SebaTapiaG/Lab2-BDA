<script>
import { auth } from "@/services/authService";
import { Menubar } from "primevue";

export default {
	name: "App",
  setup() {
    return {
      auth
    }
  },

  methods: {
    getUser() {
      this.user = sessionStorage.getItem('user');  // Recupera el usuario de sessionStorage
    },
    
  }
};
</script>

<script setup>
  import { ref } from "vue";
	import { useRouter } from 'vue-router'; // Importa el router
	const router = useRouter()


	const itemsNotLogged = ref([
  	{ 
			label: 'Home', 
			icon: 'pi pi-home', 
			command: () => { router.push("/") }
		},
  	{ label: 'Registrarme', 
			command: () => { router.push("/register") } 
		},
		{ label: 'Iniciar sesión', 
			command: () => { router.push("/login") } 
		},
]);
const itemsLogged= ref([
  	{ 
			label: 'Home', 
			icon: 'pi pi-home', 
			command: () => { router.push("/") }
		},
		{ 
			label: 'Productos',  
			command: () => { router.push("/products") }
		},
		{ 
			label: 'Mis ordenes',  
			command: () => { router.push("/myOrders") }
		},
		{ 
			label: 'Mi carrito',  
			command: () => { router.push("/cart") }
		},
		{ 
			label: 'Logout',  
			command: () => { logout() }
		},
]);

function logout() {
      auth.logout();  // Llama al método logout del servicio
      router.push('/');  // Redirige al home después de hacer logout
    }
</script>

<template>
  <nav>
		<span v-if="!auth.isLogged()">
		<Menubar :model="itemsNotLogged"/>
		</span>
		<span v-if="auth.isLogged()">
		<Menubar :model="itemsLogged"/>
		</span>
  </nav>
  <router-view/>
</template>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
