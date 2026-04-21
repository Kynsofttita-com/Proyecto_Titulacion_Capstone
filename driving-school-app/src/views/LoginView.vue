<template>
  <div class="login-container">
    <!-- Fondo con ondas decorativas -->
    <svg class="waves-bg" viewBox="0 0 1440 900" preserveAspectRatio="xMidYMid slice" xmlns="http://www.w3.org/2000/svg">
      <!-- Fondo base navy -->
      <rect width="1440" height="900" fill="#1a2735" />

      <!-- Onda superior (más clara) -->
      <path d="M0,120 C280,40 520,180 780,120 C1020,70 1240,160 1440,100 L1440,0 L0,0 Z"
            fill="#2a3a4d" opacity="0.85" />

      <!-- Onda intermedia (cobre suave) -->
      <path d="M0,260 C240,180 500,320 760,260 C1000,210 1220,300 1440,240 L1440,0 L0,0 Z"
            fill="#b87333" opacity="0.18" />

      <!-- Onda media-baja -->
      <path d="M0,640 C240,560 480,720 760,660 C1020,605 1240,700 1440,640 L1440,900 L0,900 Z"
            fill="#0f1a24" opacity="0.7" />

      <!-- Onda inferior cobre -->
      <path d="M0,780 C280,720 520,840 780,780 C1020,730 1240,810 1440,760 L1440,900 L0,900 Z"
            fill="#b87333" opacity="0.25" />

      <!-- Onda final navy oscuro -->
      <path d="M0,840 C240,800 500,870 760,830 C1020,795 1240,860 1440,820 L1440,900 L0,900 Z"
            fill="#0a1420" opacity="0.9" />
    </svg>

    <!-- Logo en esquina superior -->
    <div class="top-logo">
      <div class="logo-dot"></div>
      <span>Driving School · 06</span>
    </div>

    <!-- Card central -->
    <div class="login-card">
      <!-- Encabezado -->
      <div class="card-header">
        <p class="eyebrow">— ACCESO DE MIEMBROS</p>
        <h1 class="title">Bienvenido de vuelta</h1>
        <p class="subtitle">Ingresa tus credenciales para continuar</p>
      </div>

      <!-- Formulario -->
      <form @submit.prevent="handleLogin" class="login-form">
        <!-- Email -->
        <div class="input-group">
          <input
            v-model="email"
            type="email"
            required
            placeholder="Correo electrónico"
            class="form-input"
          />
        </div>

        <!-- Contraseña -->
        <div class="input-group">
          <input
            v-model="password"
            type="password"
            required
            placeholder="Contraseña"
            class="form-input"
          />
        </div>

        <!-- Olvidó contraseña -->
        <a href="#" class="forgot-link">¿Olvidaste tu contraseña?</a>

        <!-- Botón -->
        <button
          type="submit"
          :disabled="loading"
          class="submit-btn"
        >
          <span v-if="loading" class="loader-wrap">
            <svg class="spinner" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <circle class="spinner-track" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="spinner-head" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z"></path>
            </svg>
          </span>
          <span v-else>INGRESAR</span>
        </button>

        <!-- Mensaje de error -->
        <transition name="fade">
          <div v-if="error" class="error-msg">
            {{ error }}
          </div>
        </transition>
      </form>

      <!-- Footer del card -->
      <div class="card-footer">
        <span>¿No tienes cuenta?</span>
        <a href="#" class="signup-link">Regístrate</a>
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

<style scoped>
/* ===== Contenedor principal ===== */
.login-container {
  min-height: 100vh;
  width: 100%;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 1rem;
  overflow: hidden;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
}

/* ===== Fondo con ondas ===== */
.waves-bg {
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}

/* ===== Logo superior ===== */
.top-logo {
  position: absolute;
  top: 1.5rem;
  left: 1.5rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  z-index: 2;
  color: #e8d8cb;
  font-size: 0.75rem;
  font-weight: 300;
  letter-spacing: 0.05em;
}

.logo-dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: #b87333;
  box-shadow: 0 0 12px rgba(184, 115, 51, 0.6);
}

/* ===== Card central ===== */
.login-card {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 420px;
  background: #e8d8cb;
  border-radius: 16px;
  padding: 3rem 2.5rem 2rem;
  box-shadow:
    0 30px 80px rgba(10, 20, 32, 0.5),
    0 10px 30px rgba(10, 20, 32, 0.3);
}

/* ===== Encabezado ===== */
.card-header {
  text-align: center;
  margin-bottom: 2rem;
}

.eyebrow {
  font-size: 0.7rem;
  font-weight: 600;
  letter-spacing: 0.15em;
  color: #b87333;
  margin: 0 0 1rem 0;
}

.title {
  font-size: 2rem;
  font-weight: 300;
  color: #1a2735;
  margin: 0 0 0.5rem 0;
  letter-spacing: -0.02em;
}

.subtitle {
  font-size: 0.875rem;
  color: #6b7280;
  margin: 0;
  font-weight: 400;
}

/* ===== Formulario ===== */
.login-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.input-group {
  position: relative;
}

.form-input {
  width: 100%;
  padding: 0.95rem 1rem;
  background: #ffffff;
  border: 1px solid rgba(26, 39, 53, 0.1);
  border-radius: 10px;
  font-size: 0.9rem;
  color: #1a2735;
  outline: none;
  transition: all 0.2s ease;
  box-sizing: border-box;
  font-family: inherit;
}

.form-input::placeholder {
  color: #9ca3af;
  font-weight: 400;
}

.form-input:focus {
  border-color: #b87333;
  box-shadow: 0 0 0 3px rgba(184, 115, 51, 0.15);
}

/* ===== Link "olvidé contraseña" ===== */
.forgot-link {
  align-self: flex-start;
  font-size: 0.8rem;
  color: #b87333;
  text-decoration: none;
  font-weight: 500;
  margin-top: 0.25rem;
  transition: opacity 0.2s ease;
}

.forgot-link:hover {
  opacity: 0.75;
}

/* ===== Botón submit ===== */
.submit-btn {
  width: 100%;
  margin-top: 1rem;
  padding: 0.95rem 1rem;
  background: #1a2735;
  color: #ffffff;
  border: none;
  border-radius: 10px;
  font-size: 0.85rem;
  font-weight: 600;
  letter-spacing: 0.1em;
  cursor: pointer;
  transition: all 0.25s ease;
  font-family: inherit;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 50px;
}

.submit-btn:hover:not(:disabled) {
  background: #b87333;
  transform: translateY(-1px);
  box-shadow: 0 10px 25px rgba(184, 115, 51, 0.3);
}

.submit-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.loader-wrap {
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.spinner {
  width: 20px;
  height: 20px;
  animation: spin 0.8s linear infinite;
}

.spinner-track {
  opacity: 0.25;
}

.spinner-head {
  opacity: 0.9;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* ===== Mensaje de error ===== */
.error-msg {
  margin-top: 0.5rem;
  padding: 0.75rem 1rem;
  background: rgba(220, 38, 38, 0.08);
  border: 1px solid rgba(220, 38, 38, 0.2);
  border-radius: 8px;
  color: #991b1b;
  font-size: 0.8rem;
  font-weight: 500;
  text-align: center;
}

/* ===== Footer del card ===== */
.card-footer {
  margin-top: 2rem;
  padding-top: 1.5rem;
  border-top: 1px solid rgba(26, 39, 53, 0.1);
  text-align: center;
  font-size: 0.8rem;
  color: #6b7280;
}

.signup-link {
  color: #b87333;
  font-weight: 600;
  text-decoration: none;
  margin-left: 0.35rem;
  transition: opacity 0.2s ease;
}

.signup-link:hover {
  opacity: 0.75;
}

/* ===== Transición de error ===== */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease, transform 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(-5px);
}

/* ===== Responsive ===== */
@media (max-width: 480px) {
  .login-card {
    padding: 2.5rem 1.75rem 1.75rem;
  }

  .title {
    font-size: 1.65rem;
  }

  .top-logo {
    top: 1rem;
    left: 1rem;
  }
}
</style>
