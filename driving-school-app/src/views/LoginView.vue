<template>
  <div class="min-h-screen flex items-center justify-center" style="background: #5a7a8a">
    <!-- Login Container -->
    <div class="flex h-screen sm:h-auto sm:rounded-lg overflow-hidden shadow-2xl" style="width: 100%; max-width: 900px; background: white">

      <!-- Left Side - Form -->
      <div class="flex-1 p-12 flex flex-col justify-center" style="background: white">
        <div class="mb-12">
          <h2 class="text-3xl font-light mb-2" style="color: #1a2735">Login</h2>
          <p class="text-sm" style="color: #999">Enter your credentials to continue</p>
        </div>

        <!-- Form -->
        <form @submit.prevent="handleLogin" class="space-y-6">
          <!-- Email Input -->
          <div>
            <div class="flex items-center" style="border-bottom: 1px solid #e0e0e0">
              <svg class="w-5 h-5 mr-3" style="color: #999" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 0 1-5 0V12m0 0V8.5a2.5 2.5 0 0 0-5 0v3.5M12 12V8m0 0H8m4 0h4" />
              </svg>
              <input
                v-model="email"
                type="email"
                required
                class="flex-1 py-3 px-0 bg-transparent focus:outline-none text-sm"
                style="color: #1a2735"
                placeholder="Username"
              />
            </div>
          </div>

          <!-- Password Input -->
          <div>
            <div class="flex items-center" style="border-bottom: 1px solid #e0e0e0">
              <svg class="w-5 h-5 mr-3" style="color: #999" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
              </svg>
              <input
                v-model="password"
                type="password"
                required
                class="flex-1 py-3 px-0 bg-transparent focus:outline-none text-sm"
                style="color: #1a2735"
                placeholder="Password"
              />
            </div>
          </div>

          <!-- Remember & Forgot -->
          <div class="flex items-center justify-between pt-2 text-xs">
            <label class="flex items-center cursor-pointer">
              <input
                v-model="rememberMe"
                type="checkbox"
                class="w-4 h-4"
                style="accentColor: #b87333"
              />
              <span class="ml-2" style="color: #1a2735">Remember me</span>
            </label>
            <a href="#" class="hover:opacity-70 transition" style="color: #b87333">
              Forgot password?
            </a>
          </div>

          <!-- Submit Button -->
          <button
            type="submit"
            :disabled="loading"
            class="w-full mt-8 py-3 px-4 font-semibold text-white rounded transition-all duration-300 disabled:opacity-70 disabled:cursor-not-allowed"
            :style="{
              background: loading ? '#8b5a2b' : '#b87333'
            }"
          >
            <span v-if="loading" class="flex items-center justify-center">
              <svg class="animate-spin -ml-1 mr-2 h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              Logging in...
            </span>
            <span v-else>LOGIN</span>
          </button>
        </form>

        <!-- Error Message -->
        <transition name="fade">
          <div v-if="error" class="mt-6 p-3 rounded text-xs" style="background: #fef2f2; color: #991b1b">
            {{ error }}
          </div>
        </transition>
      </div>

      <!-- Right Side - Branding -->
      <div class="w-32 sm:w-40 flex flex-col items-center justify-center text-white" style="background: #1a2735">
        <div class="transform -rotate-90">
          <h1 class="text-xl sm:text-2xl font-light tracking-widest" style="color: #b87333">LOGIN</h1>
        </div>
      </div>
    </div>

    <!-- CSS Animations -->
    <style scoped>
      .fade-enter-active, .fade-leave-active {
        transition: opacity 0.3s ease;
      }

      .fade-enter-from, .fade-leave-to {
        opacity: 0;
      }

      @media (max-width: 640px) {
        :deep(form) {
          margin-bottom: 2rem;
        }
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
