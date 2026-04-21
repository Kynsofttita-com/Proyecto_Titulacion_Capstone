<template>
  <aside class="w-64 text-white shadow-2xl flex flex-col" style="background: linear-gradient(180deg, #1a2735 0%, #2d3f52 100%)">
    <!-- Logo Section -->
    <div class="p-6 border-b" style="border-color: #b87333">
      <div class="flex items-center space-x-2">
        <div class="w-10 h-10 rounded-lg flex items-center justify-center text-white font-bold text-lg" style="background: #b87333">
          DS
        </div>
        <h2 class="text-lg font-bold">Driving</h2>
      </div>
    </div>

    <!-- Navigation -->
    <nav class="flex-1 mt-6 space-y-1 px-3 overflow-y-auto">
      <router-link
        v-for="item in menuItems"
        :key="item.path"
        :to="item.path"
        class="flex items-center space-x-3 px-4 py-3 rounded-lg transition-all duration-200"
        :style="{
          background: isActive(item.path) ? '#b87333' : 'transparent',
          color: isActive(item.path) ? 'white' : '#e8d8cb',
        }"
      >
        <span class="text-lg flex-shrink-0">{{ item.icon }}</span>
        <span class="text-sm font-medium">{{ item.name }}</span>
      </router-link>
    </nav>

    <!-- Footer -->
    <div class="p-4 border-t" style="border-color: #b87333">
      <button
        @click="toggleMenu"
        class="w-full text-left text-sm px-2 py-2 rounded transition-colors hover:opacity-80"
        :style="{ color: '#e8d8cb' }"
      >
        <span class="text-xs">{{ isOpen ? '▼' : '▶' }}</span>
        <span class="ml-2">Opciones</span>
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
