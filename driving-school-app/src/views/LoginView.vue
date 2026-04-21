<template>
  <div class="min-h-screen flex items-center justify-center overflow-hidden" style="background: linear-gradient(135deg, #1a2735 0%, #2d5a4f 50%, #1a2735 100%)">
    <!-- Decorative wavy background -->
    <svg class="absolute inset-0 w-full h-full" preserveAspectRatio="none" viewBox="0 0 1200 800" style="opacity: 0.1">
      <path d="M0,400 Q300,300 600,400 T1200,400 L1200,0 L0,0 Z" fill="#b87333"/>
      <path d="M0,500 Q300,400 600,500 T1200,500 L1200,800 L0,800 Z" fill="#e8d8cb"/>
    </svg>

    <!-- Login Card -->
    <div class="w-full max-w-md mx-4 relative z-10">
      <div class="bg-white rounded-3xl shadow-2xl p-10">
        <!-- Header -->
        <div class="text-center mb-8">
          <h1 class="text-4xl font-bold mb-2" style="color: #1a2735">Login</h1>
          <p class="text-sm font-medium" style="color: #b87333">Enter your credentials</p>
        </div>

        <!-- Form -->
        <form @submit.prevent="handleLogin" class="space-y-5">
          <!-- Email Input -->
          <div>
            <input
              v-model="email"
              type="email"
              required
              class="w-full px-4 py-3 rounded-lg border-2 transition-all focus:outline-none"
              style="border-color: #e8d8cb; color: #1a2735"
              @focus="$event.target.style.borderColor = '#b87333'"
              @blur="$event.target.style.borderColor = '#e8d8cb'"
              placeholder="Email address"
            />
          </div>

          <!-- Password Input -->
          <div>
            <input
              v-model="password"
              type="password"
              required
              class="w-full px-4 py-3 rounded-lg border-2 transition-all focus:outline-none"
              style="border-color: #e8d8cb; color: #1a2735"
              @focus="$event.target.style.borderColor = '#b87333'"
              @blur="$event.target.style.borderColor = '#e8d8cb'"
              placeholder="Password"
            />
          </div>

          <!-- Forgot Password Link -->
          <div class="text-right">
            <a href="#" class="text-sm font-medium transition-colors" style="color: #b87333">
              Forgotten password?
            </a>
          </div>

          <!-- Submit Button -->
          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3 px-4 rounded-lg font-bold text-white transition-all duration-200 disabled:opacity-70 disabled:cursor-not-allowed"
            :style="{
              background: loading ? '#8b5a2b' : '#b87333'
            }"
          >
            <span v-if="loading" class="flex items-center justify-center">
              <svg class="animate-spin -ml-1 mr-2 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
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
          <div v-if="error" class="mt-6 p-4 rounded-lg border-l-4" style="background: #fef2f2; border-color: #dc2626; color: #991b1b">
            <p class="text-sm font-medium">{{ error }}</p>
          </div>
        </transition>
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
