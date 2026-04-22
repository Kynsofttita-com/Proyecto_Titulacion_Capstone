<template>
  <div class="login-page">
    <!-- LEFT SIDE: FEATURES -->
    <div class="login-left">
      <div class="login-logo">
        <div class="logo-icon">🚗</div>
        <div>
          <div class="logo-name">DriveSchool</div>
          <div class="logo-sub">Sistema de Gestión · Kynsoft</div>
        </div>
      </div>

      <div class="login-headline">Control total<br><span>de tu escuela</span><br>de conducción</div>
      <div class="login-desc">Plataforma integral para la administración académica<br>y financiera de escuelas de conducción en Ecuador.</div>

      <div class="features">
        <div class="feature-item">
          <div class="feature-dot">👥</div>
          Gestión integral de estudiantes e instructores
        </div>
        <div class="feature-item">
          <div class="feature-dot">🚙</div>
          Control total de vehículos y mantenimientos
        </div>
        <div class="feature-item">
          <div class="feature-dot">📅</div>
          Programación automática de clases prácticas
        </div>
        <div class="feature-item">
          <div class="feature-dot">📊</div>
          Reportes financieros y operativos en tiempo real
        </div>
      </div>

      <div class="login-footer">© 2026 Kynsoft · Universidad de Las Américas · Ecuador</div>
    </div>

    <!-- RIGHT SIDE: FORM -->
    <div class="login-right">
      <div class="form-title">Iniciar sesión</div>
      <div class="form-subtitle">Ingresa tus credenciales para acceder al sistema</div>

      <div v-if="error" class="error-box">{{ error }}</div>

      <form @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <label class="form-label">Correo electrónico</label>
          <input
            v-model="email"
            type="email"
            class="form-input"
            placeholder="usuario@ejemplo.com"
            @keyup.enter="handleLogin"
          />
        </div>

        <div class="form-group">
          <label class="form-label">Contraseña</label>
          <input
            v-model="password"
            type="password"
            class="form-input"
            placeholder="••••••••"
            @keyup.enter="handleLogin"
          />
        </div>

        <div class="check-row">
          <label class="check-label">
            <input v-model="rememberMe" type="checkbox" />
            Recordar mis credenciales
          </label>
          <a href="#" class="link">¿Olvidaste tu contraseña?</a>
        </div>

        <button
          type="submit"
          class="btn-primary"
          :disabled="loading"
        >
          <span v-if="loading" class="spinner"></span>
          {{ loading ? 'Verificando...' : 'Iniciar Sesión' }}
        </button>

        <div class="divider">─── o ───</div>
        <button type="button" class="btn-alt" @click="fillDemo('estudiante')">
          👨‍🎓 Acceder como Estudiante
        </button>
      </form>

      <div class="demo-hint">
        <strong>Usuarios demo (haz clic para autocompletar):</strong><br>
        <span @click="fillDemo('admin')">admin@kynsoft.com</span> / Admin2026! – ADMIN<br>
        <span @click="fillDemo('instructor')">instructor@kynsoft.com</span> / Inst2026! – INSTRUCTOR<br>
        <span @click="fillDemo('estudiante')">estudiante@kynsoft.com</span> / Est2026! – ESTUDIANTE
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

const demoUsers = {
  admin: { email: 'admin@kynsoft.com', password: 'Admin2026!' },
  instructor: { email: 'instructor@kynsoft.com', password: 'Inst2026!' },
  estudiante: { email: 'estudiante@kynsoft.com', password: 'Est2026!' }
}

const fillDemo = (role) => {
  const user = demoUsers[role]
  if (user) {
    email.value = user.email
    password.value = user.password
  }
}

const handleLogin = async () => {
  error.value = ''

  if (!email.value || !password.value) {
    error.value = 'Por favor completa todos los campos.'
    return
  }

  loading.value = true
  const success = await authStore.login(email.value, password.value)
  loading.value = false

  if (success) {
    router.push('/dashboard')
  } else {
    error.value = 'Credenciales inválidas. Intenta de nuevo.'
  }
}
</script>

<style scoped>
:root {
  --bg: #0d1b26;
  --bg2: #111e2a;
  --bg3: #1a2e3f;
  --border: #1e3a50;
  --text: #e2eaf4;
  --text2: #94b3c8;
  --text3: #4a6a82;
  --accent: #f59e0b;
  --accent2: #ea580c;
}

/* ===== ANIMATIONS ===== */
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(16px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes glow {
  0%, 100% { box-shadow: 0 0 20px rgba(245, 158, 11, 0.1); }
  50% { box-shadow: 0 0 40px rgba(245, 158, 11, 0.25); }
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* ===== LOGIN PAGE ===== */
.login-page {
  display: flex;
  min-height: 100vh;
  background: var(--bg);
  color: var(--text);
  font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
}

.login-left {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 60px;
  position: relative;
  overflow: hidden;
  background: linear-gradient(145deg, #1a2735 0%, #0f1e2a 60%, #0d1b26 100%);
}

.login-left::before {
  content: '';
  position: absolute;
  top: -20%;
  left: -20%;
  width: 60%;
  height: 60%;
  background: radial-gradient(circle, rgba(245, 158, 11, 0.08), transparent 70%);
  pointer-events: none;
}

.login-left::after {
  content: '';
  position: absolute;
  bottom: -10%;
  right: -10%;
  width: 50%;
  height: 50%;
  background: radial-gradient(circle, rgba(6, 182, 212, 0.06), transparent 70%);
  pointer-events: none;
}

.login-logo {
  display: flex;
  align-items: center;
  gap: 14px;
  margin-bottom: 52px;
  animation: fadeIn 0.5s ease;
}

.logo-icon {
  width: 52px;
  height: 52px;
  border-radius: 14px;
  flex-shrink: 0;
  background: linear-gradient(135deg, var(--accent), var(--accent2));
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  animation: glow 3s ease infinite;
}

.logo-name {
  font-family: 'Syne', sans-serif;
  font-size: 22px;
  font-weight: 800;
  letter-spacing: -0.5px;
}

.logo-sub {
  font-size: 11px;
  color: var(--text3);
  margin-top: 2px;
}

.login-headline {
  font-family: 'Syne', sans-serif;
  font-size: 42px;
  font-weight: 800;
  line-height: 1.1;
  letter-spacing: -1px;
  margin-bottom: 20px;
  animation: fadeIn 0.5s 0.1s ease both;
}

.login-headline span {
  color: var(--accent);
}

.login-desc {
  font-size: 15px;
  color: var(--text2);
  line-height: 1.7;
  margin-bottom: 44px;
  animation: fadeIn 0.5s 0.2s ease both;
}

.features {
  display: flex;
  flex-direction: column;
  gap: 18px;
  animation: fadeIn 0.5s 0.3s ease both;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 14px;
  font-size: 14px;
  color: var(--text2);
}

.feature-dot {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  flex-shrink: 0;
  background: rgba(245, 158, 11, 0.12);
  border: 1px solid rgba(245, 158, 11, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
}

.login-footer {
  margin-top: auto;
  padding-top: 48px;
  font-size: 12px;
  color: var(--text3);
  animation: fadeIn 0.5s 0.4s ease both;
}

/* ===== RIGHT SIDE ===== */
.login-right {
  width: 480px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 60px 50px;
  background: var(--bg2);
  border-left: 1px solid var(--border);
}

.form-title {
  font-family: 'Syne', sans-serif;
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 6px;
  letter-spacing: -0.3px;
}

.form-subtitle {
  font-size: 14px;
  color: var(--text2);
  margin-bottom: 32px;
}

.form-group {
  margin-bottom: 20px;
}

.form-label {
  display: block;
  font-size: 12px;
  font-weight: 600;
  color: var(--text2);
  margin-bottom: 8px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.form-input {
  width: 100%;
  padding: 13px 16px;
  background: var(--bg3);
  border: 1px solid var(--border);
  border-radius: 10px;
  color: var(--text);
  font-size: 14px;
  outline: none;
  font-family: inherit;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-input:focus {
  border-color: var(--accent);
  box-shadow: 0 0 0 3px rgba(245, 158, 11, 0.12);
}

.form-input::placeholder {
  color: var(--text3);
}

.check-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.check-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  color: var(--text2);
  cursor: pointer;
}

.check-label input {
  accent-color: var(--accent);
  cursor: pointer;
}

.link {
  color: var(--accent);
  font-size: 13px;
  text-decoration: none;
  cursor: pointer;
  transition: opacity 0.2s;
}

.link:hover {
  opacity: 0.7;
}

.btn-primary {
  width: 100%;
  padding: 14px;
  border: none;
  border-radius: 10px;
  background: linear-gradient(135deg, var(--accent), var(--accent2));
  color: #fff;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  font-family: inherit;
  transition: opacity 0.2s, transform 0.1s;
  margin-bottom: 16px;
}

.btn-primary:hover:not(:disabled) {
  opacity: 0.9;
}

.btn-primary:active:not(:disabled) {
  transform: scale(0.99);
}

.btn-primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-alt {
  width: 100%;
  padding: 13px;
  background: transparent;
  border: 1px solid var(--border);
  border-radius: 10px;
  color: var(--text2);
  font-size: 14px;
  cursor: pointer;
  font-family: inherit;
  transition: border-color 0.2s, color 0.2s;
}

.btn-alt:hover {
  border-color: var(--accent);
  color: var(--text);
}

.divider {
  text-align: center;
  color: var(--text3);
  font-size: 12px;
  margin: 16px 0;
}

.error-box {
  padding: 12px 16px;
  background: rgba(239, 68, 68, 0.1);
  border: 1px solid rgba(239, 68, 68, 0.3);
  border-radius: 10px;
  color: #f87171;
  font-size: 13px;
  margin-bottom: 16px;
  animation: fadeIn 0.3s ease;
}

.demo-hint {
  margin-top: 24px;
  padding: 16px;
  background: var(--bg3);
  border-radius: 10px;
  border: 1px solid var(--border);
  font-size: 12px;
  color: var(--text3);
  line-height: 1.9;
}

.demo-hint strong {
  color: var(--text2);
}

.demo-hint span {
  display: inline-block;
  cursor: pointer;
  color: var(--accent);
  padding: 1px 6px;
  border-radius: 4px;
  transition: background 0.15s;
  font-weight: 500;
}

.demo-hint span:hover {
  background: rgba(245, 158, 11, 0.1);
}

.spinner {
  display: inline-block;
  width: 16px;
  height: 16px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: #fff;
  border-radius: 50%;
  animation: spin 0.7s linear infinite;
  vertical-align: middle;
  margin-right: 8px;
}

/* ===== RESPONSIVE ===== */
@media (max-width: 1024px) {
  .login-page {
    flex-direction: column;
  }

  .login-left {
    padding: 40px;
    justify-content: flex-start;
    padding-top: 40px;
  }

  .login-right {
    width: 100%;
    padding: 40px;
  }

  .login-headline {
    font-size: 32px;
  }
}

@media (max-width: 640px) {
  .login-left {
    padding: 30px 20px;
  }

  .login-right {
    padding: 30px 20px;
  }

  .login-headline {
    font-size: 24px;
    margin-bottom: 16px;
  }

  .logo-icon {
    width: 40px;
    height: 40px;
    font-size: 18px;
  }

  .demo-hint {
    font-size: 11px;
  }
}
</style>
