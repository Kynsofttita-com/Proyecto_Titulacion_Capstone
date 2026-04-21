<template>
  <div class="flex h-screen" style="background: #f5f3f0">
    <Sidebar />
    <div class="flex-1 flex flex-col overflow-hidden">
      <!-- Header -->
      <header class="shadow" style="background: linear-gradient(135deg, #1a2735 0%, #2d3f52 100%)">
        <div class="max-w-7xl mx-auto px-6 py-4 flex justify-between items-center">
          <div class="flex items-center space-x-3">
            <div class="p-2 rounded-lg" style="background: #b87333">
              <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
              </svg>
            </div>
            <h1 class="text-2xl font-bold text-white">Driving School</h1>
          </div>
          <div class="flex items-center space-x-6">
            <div class="text-right">
              <p class="text-sm font-medium text-white">{{ authStore.user?.email }}</p>
              <p class="text-xs" style="color: #e8d8cb">{{ userRole }}</p>
            </div>
            <button
              @click="handleLogout"
              class="px-4 py-2 rounded-lg text-sm font-medium text-white transition-all hover:scale-105"
              style="background: #b87333"
            >
              Cerrar Sesión
            </button>
          </div>
        </div>
      </header>
      <!-- Main Content -->
      <main class="flex-1 overflow-y-auto">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { computed } from 'vue'
import Sidebar from '../components/layout/Sidebar.vue'

const router = useRouter()
const authStore = useAuthStore()

const userRole = computed(() => {
  return authStore.role ? authStore.role.charAt(0) + authStore.role.slice(1).toLowerCase() : 'Usuario'
})

const handleLogout = () => {
  authStore.logout()
  router.push('/login')
}
</script>
