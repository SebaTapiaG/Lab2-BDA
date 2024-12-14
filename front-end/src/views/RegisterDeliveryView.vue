<template>
	<div class="title">
		<h1>Registro de Repartidor</h1>
	</div>
	<div class="input">
		<FloatLabel variant="in">
			<InputText id="nombre" v-model="state.nombre" variant="filled" size="large" />
			<label for="in_label">Nombre</label>
		</FloatLabel>
	</div>
	<div class="input">
		<FloatLabel variant="in">
			<InputText id="email" v-model="state.email" variant="filled" size="large" />
			<label for="in_label">Correo Electrónico</label>
		</FloatLabel>
	</div>
	<div class="input">
		<FloatLabel variant="in">
			<InputText id="telefono" v-model="state.telefono" variant="filled" size="large" />
			<label for="in_label">Número Telefónico</label>
		</FloatLabel>
	</div>
	<div class="input">
		<FloatLabel variant="in">
			<InputText id="almacen" v-model="state.almacen" variant="filled" size="large" />
			<label for="in_label">Nombre del Almacén</label>
		</FloatLabel>
	</div>
	<div class="input-pass">
		<FloatLabel variant="in">
			<Password id="contrasena" v-model="state.contrasena" variant="filled" :feedback="false" toggleMask />
			<label for="in_label">Contraseña</label>
		</FloatLabel>
	</div>
	<div class="input-pass">
		<FloatLabel variant="in">
			<Password id="recontrasena" v-model="state.recontrasena" variant="filled" :feedback="false" toggleMask />
			<label for="in_label">Confirme su Contraseña</label>
		</FloatLabel>
	</div>
	<div class="button">
		<Button @click="register">Crear cuenta</Button>
	</div>
	<div class="text">
		<p>
			¿Ya tienes cuenta?
			<RouterLink to="/" class="link">Inicia sesión</RouterLink>
		</p>
	</div>
</template>

<script>
import { FloatLabel, InputText, Button, Password } from 'primevue';

export default {
	name: "RegisterDeliveryView",
}
</script>

<script setup>
import { z } from 'zod';
import { reactive } from 'vue';
import deliveryService from '../services/repartidor.service';
import { useRouter } from 'vue-router';

const router = useRouter()

const schema = z.object({
	contrasena: z.string().min(8, 'La contraseña debe tener al menos 8 caracteres'),
	recontrasena: z.string().min(8, 'La contraseña debe tener al menos 8 caracteres'),
	telefono: z.string().min(9, 'Número de teléfono inválido'),
}).refine(data => data.contrasena === data.recontrasena, {
	message: 'Las contraseñas no coinciden',
	path: ['recontrasena'],
});

const state = reactive({
	nombre: undefined,
	email: undefined,
	telefono: undefined,
	almacen: undefined,
	contrasena: undefined,
	recontrasena: undefined,
});

async function register() {
	const result = schema.safeParse(state);
	if (result.success) {
		console.log(result.data);
		return;
	}
	const deliveryObj = {
		nombre: state.nombre,
		email: state.email,
		telefono: state.telefono,
		almacen: state.almacen,
		contrasena: state.contrasena,
	};

	try {
		const response = await deliveryService.register(deliveryObj);
		console.log(response.data);
		console.log("Repartidor registrado", deliveryObj);
		router.push("/");
	} catch (error) {
		console.log(error);
	}
};
</script>

<style>
.title {
	margin-top: 10%;
	margin-left: 40%;
}

.input {
	margin-top: 1%;
	margin-left: 40%;
}

.input-pass {
	margin-top: 1%;
	margin-left: 40%;
}

.button {
	margin-top: 2%;
	margin-left: 44%;
}

.text {
	margin-top: 1%;
	margin-left: 42%;
}
</style>
