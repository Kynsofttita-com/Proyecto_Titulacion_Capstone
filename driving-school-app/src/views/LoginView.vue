<template>
  <div class="min-h-screen flex items-center justify-center overflow-hidden relative" style="background: linear-gradient(135deg, #1a2735 0%, #2d3f52 50%, #1a2735 100%)">
    <!-- Animated Background Elements -->
    <div class="absolute inset-0 overflow-hidden pointer-events-none">
      <!-- Floating circle 1 -->
      <div class="absolute w-96 h-96 rounded-full opacity-10" style="background: #b87333; filter: blur(60px); animation: float 8s ease-in-out infinite; top: -10%; left: -5%"></div>
      <!-- Floating circle 2 -->
      <div class="absolute w-80 h-80 rounded-full opacity-8" style="background: #e8d8cb; filter: blur(50px); animation: float 10s ease-in-out infinite 2s; bottom: -5%; right: -3%"></div>
    </div>

    <!-- Login Card -->
    <div class="w-full max-w-md mx-4 relative z-10">
      <div class="bg-white rounded-3xl shadow-2xl overflow-hidden" style="box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3)">

        <!-- Decorative Top Bar -->
        <div class="h-2" style="background: linear-gradient(90deg, #b87333 0%, #e8d8cb 50%, #b87333 100%)"></div>

        <!-- Content -->
        <div class="p-10">
          <!-- Header Section -->
          <div class="text-center mb-10">
            <!-- Icon -->
            <div class="w-20 h-20 rounded-2xl mx-auto mb-6 flex items-center justify-center text-4xl" style="background: linear-gradient(135deg, #b87333 0%, #8b5a2b 100%)">
              🔐
            </div>

            <!-- Title -->
            <h1 class="text-4xl font-bold mb-2" style="color: #1a2735">Driving School</h1>

            <!-- Subtitle -->
            <p class="text-sm font-medium" style="color: #b87333">Inicia sesión para continuar</p>
          </div>

          <!-- Form -->
          <form @submit.prevent="handleLogin" class="space-y-5">
            <!-- Email Input -->
            <div class="group">
              <label class="block text-xs font-semibold mb-2 uppercase tracking-wide" style="color: #1a2735">Correo Electrónico</label>
              <input
                v-model="email"
                type="email"
                required
                class="w-full px-4 py-3.5 rounded-xl transition-all duration-300 focus:outline-none"
                style="
                  background: #f9f7f5;
                  border: 2px solid #e8d8cb;
                  color: #1a2735;
                "
                @focus="$event.target.style.borderColor = '#b87333'; $event.target.style.background = '#fff'"
                @blur="$event.target.style.borderColor = '#e8d8cb'; $event.target.style.background = '#f9f7f5'"
                placeholder="admin@kynsoft.com"
              />
            </div>

            <!-- Password Input -->
            <div class="group">
              <label class="block text-xs font-semibold mb-2 uppercase tracking-wide" style="color: #1a2735">Contraseña</label>
              <input
                v-model="password"
                type="password"
                required
                class="w-full px-4 py-3.5 rounded-xl transition-all duration-300 focus:outline-none"
                style="
                  background: #f9f7f5;
                  border: 2px solid #e8d8cb;
                  color: #1a2735;
                "
                @focus="$event.target.style.borderColor = '#b87333'; $event.target.style.background = '#fff'"
                @blur="$event.target.style.borderColor = '#e8d8cb'; $event.target.style.background = '#f9f7f5'"
                placeholder="••••••••"
              />
            </div>

            <!-- Remember & Forgot -->
            <div class="flex items-center justify-between pt-2">
              <label class="flex items-center cursor-pointer">
                <input
                  v-model="rememberMe"
                  type="checkbox"
                  class="w-4 h-4 rounded cursor-pointer"
                  style="accentColor: #b87333"
                />
                <span class="ml-2 text-sm font-medium" style="color: #1a2735">Recuérdame</span>
              </label>
              <a href="#" class="text-sm font-medium transition-colors hover:opacity-80" style="color: #b87333">
                ¿Olvidaste tu contraseña?
              </a>
            </div>

            <!-- Submit Button -->
            <button
              type="submit"
              :disabled="loading"
              class="w-full py-3.5 px-4 rounded-xl font-bold text-white transition-all duration-300 transform hover:scale-105 disabled:opacity-70 disabled:cursor-not-allowed disabled:hover:scale-100 mt-8"
              :style="{
                background: loading ? 'linear-gradient(135deg, #8b5a2b 0%, #6b4423 100%)' : 'linear-gradient(135deg, #b87333 0%, #8b5a2b 100%)',
                boxShadow: loading ? 'none' : '0 8px 20px rgba(184, 115, 51, 0.3)'
              }"
            >
              <span v-if="loading" class="flex items-center justify-center">
                <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                Iniciando sesión...
              </span>
              <span v-else>INICIAR SESIÓN</span>
            </button>
          </form>

          <!-- Error Message -->
          <transition name="fade">
            <div v-if="error" class="mt-6 p-4 rounded-xl border-l-4 animate-pulse" style="background: #fef2f2; border-color: #dc2626; color: #991b1b">
              <div class="flex items-start">
                <span class="text-lg mr-2">⚠️</span>
                <p class="text-sm font-medium">{{ error }}</p>
              </div>
            </div>
          </transition>

          <!-- Divider -->
          <div class="mt-8 pt-6 border-t" style="border-color: #e8d8cb">
            <p class="text-center text-xs font-medium" style="color: #999">Driving School System v1.0</p>
          </div>
        </div>
      </div>
    </div>

    <!-- CSS Animations -->
    <style scoped>
      @keyframes float {
        0%, 100% { transform: translateY(0px) translateX(0px); }
        50% { transform: translateY(30px) translateX(20px); }
      }

      .fade-enter-active, .fade-leave-active {
        transition: opacity 0.3s ease;
      }

      .fade-enter-from, .fade-leave-to {
        opacity: 0;
      }
    </style>
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
