import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ProductoView from '../views/Producto.vue'

const routes = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path : '/',
            name : 'Home',
            component : HomeView
        },
        {
            path : '/producto/:id',
            name : 'Producto',
            component : ProductoView
        }
    ]
})

