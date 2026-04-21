<template>
  <aside class="w-64 bg-gray-800 text-white shadow-lg">
    <div class="p-6 border-b border-gray-700">
      <h2 class="text-xl font-bold">Menu</h2>
    </div>
    <nav class="mt-6 space-y-2 px-4">
      <router-link
        v-for="item in menuItems"
        :key="item.path"
        :to="item.path"
        class="flex items-center space-x-3 px-4 py-3 rounded-lg hover:bg-gray-700 transition"
        :class="{ 'bg-blue-600': isActive(item.path) }"
      >
        <span class="text-xl">{{ item.icon }}</span>
        <span>{{ item.name }}</span>
      </router-link>
    </nav>
    <div class="absolute bottom-0 left-0 right-0 p-4 bg-gray-700 w-64">
      <button @click="toggleMenu" class="w-full text-left text-sm text-gray-300 hover:text-white">
        {{ isOpen ? '▼' : '▶' }} Menú Colapsable
      </button>
    </div>
  </aside>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const isOpen = ref(true)

const menuItems = [
  { name: 'Dashboard', path: '/dashboard', icon: '📊' },
  { name: 'Estudiantes', path: '/estudiantes', icon: '👨‍🎓' },
  { name: 'Instructores', path: '/instructores', icon: '👨‍🏫' },
  { name: 'Vehículos', path: '/vehiculos', icon: '🚗' },
  { name: 'Asignaciones', path: '/asignaciones', icon: '📋' },
  { name: 'Cobros', path: '/cobros', icon: '💰' },
  { name: 'Reportes', path: '/reportes', icon: '📈' },
  { name: 'Configuración', path: '/configuracion', icon: '⚙️' }
]

const isActive = (path) => route.path === path

const toggleMenu = () => {
  isOpen.value = !isOpen.value
}
</script>
