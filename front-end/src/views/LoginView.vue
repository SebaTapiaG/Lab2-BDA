<template>
	<div class="title">
		<h1>Inicio de sesión</h1>
	</div>
	<div class="input">
	 	<FloatLabel variant="in">
      <InputText id="email" v-model="state.email" variant="filled" size="large"/>
      <label for="in_label">Correo electronico</label>
    </FloatLabel>
	</div>
	<div class="input-pass">
	 	<FloatLabel variant="in">
      <Password id="contrasena" v-model="state.contrasena" variant="filled" :feedback="false" toggleMask/>
      <label for="in_label">Contraseña</label>
    </FloatLabel>
	</div>
	<div class="button">
		<Button @click="login">Iniciar sesión</Button>
	</div>
	<div class="text">
		<p>
    ¿No tienes cuenta? 
    <RouterLink to="/register" class="link">Crear cuenta</RouterLink>
  	</p>
	</div>
</template>

<script setup>
import { ref } from 'vue';
import { FloatLabel, Password, InputText, Button } from 'primevue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { reactive } from 'vue';
import { jwtDecode } from 'jwt-decode';
import { auth } from '@/services/authService';



const router = useRouter()

const state = reactive({
    email: undefined,
    contrasena: undefined,	
});



async function login(){
		const userObj = {
                nombre: "",
                email: state.email,
                contrasena: state.contrasena,
                telefono : "",
								direccion: "",
            };
						
		try {
        const response = await axios.post('http://localhost:8080/api/cliente/login', userObj);
        const token = response.data;
				console.log(jwtDecode(token))
				const userId = jwtDecode(token).user_id
				auth.login(userId, token)
				router.push("/")
    } catch (error) {
        console.log(error)
    }
};

</script>

<style>
.title{
	margin-top: 10%; 
	margin-left: 40%
}
.input{
	margin-top: 1%; 
	margin-left: 40%;
}
.input-pass{
	margin-top: 1%; 
	margin-left: 40%;
}
.button{
	margin-top: 2%;
	margin-left: 44%;
}
.text{
	margin-top: 1%;
	margin-left: 42%;
}
</style>