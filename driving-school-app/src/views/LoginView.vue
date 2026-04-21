<template>
  <div class="min-h-screen flex items-center justify-center" style="background: linear-gradient(135deg, #1a2735 0%, #2d3f52 100%)">
    <!-- Decorative elements -->
    <div class="absolute top-0 left-0 w-96 h-96 rounded-full opacity-10" style="background: #b87333; filter: blur(100px)"></div>
    <div class="absolute bottom-0 right-0 w-96 h-96 rounded-full opacity-10" style="background: #e8d8cb; filter: blur(100px)"></div>

    <!-- Login Card -->
    <div class="w-full max-w-md mx-4 relative z-10">
      <!-- Header Decoration -->
      <div class="text-center mb-8">
        <div class="inline-block mb-6 p-4 rounded-xl" style="background: #b87333">
          <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
          </svg>
        </div>
        <h1 class="text-4xl font-bold text-white mb-2">Driving School</h1>
        <p class="text-sm font-medium" style="color: #e8d8cb">Sistema de Gestión de Escuela de Manejo</p>
      </div>

      <!-- Main Card -->
      <div class="bg-white rounded-2xl shadow-2xl p-8">
        <form @submit.prevent="handleLogin" class="space-y-6">
          <!-- Email Input -->
          <div>
            <label class="block text-sm font-semibold mb-3" style="color: #1a2735">Correo Electrónico</label>
            <div class="relative">
              <svg class="absolute left-4 top-3.5 w-5 h-5" style="color: #b87333" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
              </svg>
              <input
                v-model="email"
                type="email"
                required
                class="w-full pl-12 pr-4 py-3 border-2 rounded-lg transition-colors focus:outline-none"
                style="border-color: #e8d8cb; color: #1a2735"
                @focus="$event.target.style.borderColor = '#b87333'"
                @blur="$event.target.style.borderColor = '#e8d8cb'"
                placeholder="admin@kynsoft.com"
              />
            </div>
          </div>

          <!-- Password Input -->
          <div>
            <label class="block text-sm font-semibold mb-3" style="color: #1a2735">Contraseña</label>
            <div class="relative">
              <svg class="absolute left-4 top-3.5 w-5 h-5" style="color: #b87333" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
              </svg>
              <input
                v-model="password"
                type="password"
                required
                class="w-full pl-12 pr-4 py-3 border-2 rounded-lg transition-colors focus:outline-none"
                style="border-color: #e8d8cb; color: #1a2735"
                @focus="$event.target.style.borderColor = '#b87333'"
                @blur="$event.target.style.borderColor = '#e8d8cb'"
                placeholder="••••••••"
              />
            </div>
          </div>

          <!-- Remember Me -->
          <div class="flex items-center justify-between pt-2">
            <label class="flex items-center cursor-pointer group">
              <input
                v-model="rememberMe"
                type="checkbox"
                class="w-5 h-5 rounded accent-current cursor-pointer"
                style="accentColor: #b87333"
              />
              <span class="ml-3 text-sm font-medium" style="color: #1a2735">Recuérdame</span>
            </label>
            <a href="#" class="text-sm font-medium transition-colors" style="color: #b87333">
              ¿Olvidaste tu contraseña?
            </a>
          </div>

          <!-- Submit Button -->
          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3 px-4 rounded-lg font-bold text-white transition-all duration-200 transform hover:scale-105 disabled:opacity-70 disabled:cursor-not-allowed disabled:hover:scale-100"
            :style="{
              background: loading ? '#8b5a2b' : '#b87333'
            }"
          >
            <span v-if="loading" class="flex items-center justify-center">
              <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              Iniciando sesión...
            </span>
            <span v-else>Iniciar Sesión</span>
          </button>
        </form>

        <!-- Error Message -->
        <transition name="fade">
          <div v-if="error" class="mt-6 p-4 rounded-lg border-l-4 animate-pulse" style="background: #fee; border-color: #c33; color: #933">
            <div class="flex items-start">
              <svg class="w-5 h-5 mr-3 mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd" />
              </svg>
              <span>{{ error }}</span>
            </div>
          </div>
        </transition>

        <!-- Divider -->
        <div class="mt-6 flex items-center">
          <div class="flex-1 h-px" style="background: #e8d8cb"></div>
          <span class="px-3 text-xs font-medium" style="color: #999">Driving School System v1.0</span>
          <div class="flex-1 h-px" style="background: #e8d8cb"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const email = ref('')
const password = ref('')
const rememberMe = ref(false)
const loading = ref(false)
const error = ref('')

const handleLogin = async () => {
  error.value = ''
  loading.value = true

  try {
    const success = await authStore.login(email.value, password.value)
    if (success) {
      router.push('/dashboard')
    } else {
      error.value = 'Email o contraseña inválidos'
    }
  } catch (err) {
    error.value = err.message || 'Error en el login'
  } finally {
    loading.value = false
  }
}
</script>
