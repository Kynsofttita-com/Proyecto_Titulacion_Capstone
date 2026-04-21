<template>
  <div class="min-h-screen flex items-stretch" style="background: white">
    <!-- Left Side - Navy with Content -->
    <div class="hidden lg:flex lg:w-1/2 flex-col justify-between p-16 relative overflow-hidden" style="background: #1a2735">
      <!-- Top Logo -->
      <div class="flex items-center space-x-2">
        <div class="w-3 h-3 rounded-full" style="background: #b87333"></div>
        <span class="text-sm font-light" style="color: #e8d8cb">Driving School · 06</span>
      </div>

      <!-- Main Content -->
      <div>
        <p class="text-sm font-light mb-6" style="color: #b87333">— ACCESO DE MIEMBROS</p>
        <h1 class="text-5xl font-light leading-tight mb-8" style="color: white">
          Bienvenido de<br />vuelta,
          <span style="color: #b87333">sigamos<br />donde lo<br />dejaste.</span>
        </h1>
        <p class="text-sm font-light leading-relaxed" style="color: #999">
          Entra para retomar tus proyectos,<br />colecciones guardadas y la mesa de<br />trabajo compartida con tu equipo.
        </p>
      </div>

      <!-- Bottom Info -->
      <div class="space-y-4">
        <div class="flex items-start space-x-2 text-xs font-light" style="color: #999">
          <div class="w-2 h-2 rounded-full mt-1" style="background: #b87333"></div>
          <div>
            <p>Sesión cifrada</p>
            <p>TLS 1.3 · Verificación en dos pasos</p>
            <p>disponible</p>
          </div>
        </div>
        <div class="flex justify-between text-xs" style="color: #666">
          <span>Servicio operativo</span>
          <span>09:42</span>
        </div>
      </div>

      <!-- Decorative circles -->
      <div class="absolute bottom-0 right-0 opacity-10">
        <svg width="300" height="300" viewBox="0 0 300 300">
          <circle cx="150" cy="150" r="120" fill="none" stroke="#b87333" stroke-width="1"/>
          <circle cx="150" cy="150" r="80" fill="none" stroke="#b87333" stroke-width="1"/>
          <circle cx="150" cy="150" r="40" fill="none" stroke="#b87333" stroke-width="1"/>
        </svg>
      </div>
    </div>

    <!-- Right Side - Cream Form -->
    <div class="w-full lg:w-1/2 flex flex-col justify-center p-8 lg:p-16" style="background: #e8d8cb">
      <!-- Header -->
      <div class="mb-12">
        <p class="text-xs font-semibold tracking-widest mb-6" style="color: #b87333">— INGRESAR</p>
        <h2 class="text-4xl font-light mb-4" style="color: #1a2735">Accede a tu cuenta</h2>
        <p class="text-sm" style="color: #666">
          ¿No tienes una?
          <a href="#" class="font-medium" style="color: #b87333">Crea una cuenta en dos minutos.</a>
        </p>
      </div>

      <!-- Social Buttons -->
      <div class="flex gap-4 mb-8">
        <button class="flex-1 py-3 px-4 rounded-lg font-medium transition-all hover:bg-white hover:shadow-md" style="background: white; color: #1a2735; border: 1px solid #ddd">
          <span class="flex items-center justify-center space-x-2">
            <span>G</span>
            <span>Google</span>
          </span>
        </button>
        <button class="flex-1 py-3 px-4 rounded-lg font-medium transition-all hover:bg-white hover:shadow-md" style="background: white; color: #1a2735; border: 1px solid #ddd">
          <span class="flex items-center justify-center space-x-2">
            <span>🍎</span>
            <span>Apple</span>
          </span>
        </button>
      </div>

      <!-- Divider -->
      <div class="flex items-center gap-4 mb-8">
        <div class="flex-1" style="border-top: 1px solid rgba(26, 39, 53, 0.1)"></div>
        <span class="text-xs font-semibold" style="color: #999">O CON CORREO</span>
        <div class="flex-1" style="border-top: 1px solid rgba(26, 39, 53, 0.1)"></div>
      </div>

      <!-- Form -->
      <form @submit.prevent="handleLogin" class="space-y-6">
        <!-- Email -->
        <div>
          <label class="text-xs font-semibold block mb-3" style="color: #1a2735">Correo electrónico</label>
          <div class="flex items-center px-4 py-3 rounded-lg" style="background: white">
            <svg class="w-5 h-5 mr-3" style="color: #999" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
            </svg>
            <input
              v-model="email"
              type="email"
              required
              class="flex-1 bg-transparent focus:outline-none text-sm"
              style="color: #1a2735"
              placeholder="tunombre@estudio.com"
            />
          </div>
        </div>

        <!-- Password -->
        <div>
          <div class="flex items-center justify-between mb-3">
            <label class="text-xs font-semibold" style="color: #1a2735">Contraseña</label>
            <a href="#" class="text-xs font-medium" style="color: #b87333">¿La olvidaste?</a>
          </div>
          <div class="flex items-center px-4 py-3 rounded-lg" style="background: white">
            <svg class="w-5 h-5 mr-3" style="color: #999" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
            </svg>
            <input
              v-model="password"
              type="password"
              required
              class="flex-1 bg-transparent focus:outline-none text-sm"
              style="color: #1a2735"
              placeholder="Mínimo 8 caracteres"
            />
          </div>
        </div>

        <!-- Remember -->
        <label class="flex items-center cursor-pointer">
          <input
            v-model="rememberMe"
            type="checkbox"
            class="w-4 h-4"
            style="accentColor: #b87333"
          />
          <span class="ml-3 text-sm font-medium" style="color: #1a2735">Recuérdame 30 días</span>
        </label>

        <!-- Button -->
        <button
          type="submit"
          :disabled="loading"
          class="w-full mt-8 py-3.5 px-4 rounded-lg font-semibold text-white transition-all duration-300 disabled:opacity-70 disabled:cursor-not-allowed flex items-center justify-center space-x-2"
          :style="{
            background: loading ? '#8b5a2b' : '#1a2735'
          }"
        >
          <span v-if="loading">
            <svg class="animate-spin h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
          </span>
          <span v-else>
            Entrar
            <span style="margin-left: 8px">→</span>
          </span>
        </button>
      </form>

      <!-- Error Message -->
      <transition name="fade">
        <div v-if="error" class="mt-6 p-4 rounded-lg text-xs font-medium" style="background: rgba(220, 38, 38, 0.1); color: #991b1b">
          {{ error }}
        </div>
      </transition>

      <!-- Footer -->
      <div class="mt-12 pt-8 border-t" style="border-color: rgba(26, 39, 53, 0.1)">
        <div class="flex items-center justify-between text-xs" style="color: #999">
          <span>© ATELIER 2026</span>
          <div class="flex space-x-4">
            <a href="#" class="hover:opacity-70" style="color: #b87333">Ayuda</a>
            <span>·</span>
            <a href="#" class="hover:opacity-70" style="color: #b87333">Privacidad</a>
          </div>
        </div>
      </div>
    </div>

    <!-- Mobile Logo (visible on small screens) -->
    <div class="absolute top-4 left-4 lg:hidden flex items-center space-x-2">
      <div class="w-2 h-2 rounded-full" style="background: #b87333"></div>
      <span class="text-xs font-light" style="color: #1a2735">Driving School</span>
    </div>

    <!-- CSS -->
    <style scoped>
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
