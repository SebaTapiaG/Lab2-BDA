<template>
    <div class="map-container">
      <div>
        <h2>Calcula el punto medio entre dos clientes</h2>
        <div class="inputs">
          <!-- Seleccionar cliente 1 -->
          <label for="client1">Cliente 1:</label>
          <select v-model="client1Id" id="client1">
            <option disabled value="">Selecciona un cliente</option>
            <option v-for="cliente in clientes" :key="cliente.id_cliente" :value="cliente.id_cliente">
              {{ cliente.nombre }} - {{ cliente.telefono }}
            </option>
          </select>
  
          <!-- Seleccionar cliente 2 -->
          <label for="client2">Cliente 2:</label>
          <select v-model="client2Id" id="client2">
            <option disabled value="">Selecciona un cliente</option>
            <option v-for="cliente in clientes" :key="cliente.id_cliente" :value="cliente.id_cliente">
              {{ cliente.nombre }} - {{ cliente.telefono }}
            </option>
          </select>
  
          <button @click="fetchMidpoint">Calcular Punto Medio</button>
        </div>
      </div>
  
      <!-- Mapa -->
      <div id="map" style="height: 500px;"></div>
    </div>
  </template>
  
  <script>
  import L from 'leaflet';
  import clientService from '../services/client.service';
  
  export default {
    name: 'MapView',
    data() {
      return {
        clientes: [], // Lista de clientes obtenida del backend
        client1Id: null,
        client2Id: null,
        map: null,
        midpointMarker: null,
      };
    },
    methods: {
      async fetchClientes() {
        try {
          const response = await clientService.getAll();
          this.clientes = response.data; // Asigna la lista de clientes
        } catch (error) {
          console.error('Error al obtener la lista de clientes:', error);
          alert('No se pudo obtener la lista de clientes.');
        }
      },
      async fetchMidpoint() {
        try {
          if (!this.client1Id || !this.client2Id) {
            alert('Por favor, selecciona ambos clientes.');
            return;
          }
  
          // Obtener punto medio
          const puntoMedioWKT = await clientService.puntoMedio(this.client1Id, this.client2Id);
          if (!puntoMedioWKT) {
            alert('No se encontró el punto medio.');
            return;
          }
  
          // Procesar coordenadas del punto medio
          const coordinates = puntoMedioWKT
            .replace('POINT(', '')
            .replace(')', '')
            .split(' ')
            .map(coord => parseFloat(coord));
          const [lng, lat] = coordinates;
  
          // Centrar el mapa y agregar marcador
          if (!this.midpointMarker) {
            this.midpointMarker = L.marker([lat, lng]).addTo(this.map);
          } else {
            this.midpointMarker.setLatLng([lat, lng]);
          }
          this.map.setView([lat, lng], 14);
        } catch (error) {
          console.error('Error al obtener el punto medio:', error);
          alert('Ocurrió un error al calcular el punto medio.');
        }
      },
    },
    mounted() {
      // Inicializar mapa
      this.map = L.map('map').setView([0, 0], 2);
  
      // Añadir capa base de OpenStreetMap
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      }).addTo(this.map);
  
      // Obtener la lista de clientes al cargar el componente
      this.fetchClientes();
    },
  };
  </script>
  
  <style>
  .map-container {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .inputs {
    margin-bottom: 1rem;
  }
  
  #map {
    width: 80%;
    border: 1px solid #ccc;
  }
  </style>
  