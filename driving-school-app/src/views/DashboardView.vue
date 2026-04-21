<template>
  <div class="dashboard-wrapper">
    <div class="dashboard-inner">

      <!-- Header -->
      <header class="dashboard-header">
        <div>
          <div class="eyebrow-row">
            <span class="eyebrow-dot"></span>
            <span class="eyebrow-text">— PANEL DE CONTROL</span>
          </div>
          <h1 class="greeting">Bienvenido de vuelta, {{ userName }}</h1>
          <p class="date-line">{{ today }} · Resumen del día</p>
        </div>

        <div class="header-right">
          <div class="status-pill">
            <span class="status-dot"></span>
            Operativo
          </div>
          <button @click="logout" class="logout-btn" title="Cerrar sesión">
            🚪
          </button>
          <div class="avatar">{{ userInitials }}</div>
        </div>
      </header>

      <!-- Stat Cards -->
      <section class="stats-grid">
        <article
          v-for="stat in stats"
          :key="stat.label"
          class="stat-card"
        >
          <div class="stat-accent"></div>
          <p class="stat-label">{{ stat.label }}</p>
          <div class="stat-value-row">
            <span class="stat-value">{{ stat.value }}</span>
            <span v-if="stat.suffix" class="stat-suffix" :class="stat.suffixClass">{{ stat.suffix }}</span>
          </div>
          <div class="stat-progress-track">
            <div class="stat-progress-fill" :style="{ width: stat.progress + '%' }"></div>
          </div>
        </article>
      </section>

      <!-- Main grid: chart + notifications -->
      <section class="main-grid">

        <!-- Chart card -->
        <article class="chart-card">
          <div class="card-header">
            <div>
              <p class="card-eyebrow">— ACTIVIDAD</p>
              <h3 class="card-title">Clases por hora</h3>
            </div>
            <span class="card-meta">Últimas 12 horas</span>
          </div>

          <div class="chart-bars">
            <div
              v-for="(bar, i) in chartData"
              :key="i"
              class="bar-col"
            >
              <div
                class="bar"
                :class="'bar--' + bar.level"
                :style="{ height: bar.value + '%' }"
              ></div>
              <span
                class="bar-label"
                :class="{ 'bar-label--active': bar.level !== 'normal' }"
              >{{ bar.hour }}h</span>
            </div>
          </div>

          <div class="chart-legend">
            <span class="legend-item"><span class="legend-swatch legend-peak"></span>Pico</span>
            <span class="legend-item"><span class="legend-swatch legend-high"></span>Alto</span>
            <span class="legend-item"><span class="legend-swatch legend-normal"></span>Normal</span>
          </div>
        </article>

        <!-- Notifications card -->
        <article class="notif-card">
          <div class="card-header">
            <div>
              <p class="card-eyebrow">— NOTIFICACIONES</p>
              <h3 class="card-title">Alertas</h3>
            </div>
          </div>

          <div class="notif-list">
            <div
              v-for="notif in notifications"
              :key="notif.title"
              class="notif-item"
              :class="'notif-item--' + notif.type"
            >
              <p class="notif-type">{{ notif.title }}</p>
              <p class="notif-msg">{{ notif.message }}</p>
            </div>
          </div>
        </article>

      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const today = computed(() => {
  const d = new Date()
  return d.toLocaleDateString('es-ES', { weekday: 'long', day: 'numeric', month: 'long' })
    .replace(/^\w/, c => c.toUpperCase())
})

const userName = computed(() => {
  return authStore.user?.email?.split('@')[0] || 'Usuario'
})

const userInitials = computed(() => {
  if (authStore.user?.email) {
    const parts = authStore.user.email.split('@')[0].split('.')
    return parts.map(p => p[0].toUpperCase()).join('')
  }
  return 'U'
})

const stats = ref([
  { label: 'ESTUDIANTES ACTIVOS', value: '247', suffix: '↑ 12', suffixClass: 'suffix-up', progress: 72 },
  { label: 'CLASES HOY', value: '34', suffix: 'de 40', suffixClass: 'suffix-muted', progress: 85 },
  { label: 'VEHÍCULOS', value: '12', suffix: '/15', suffixClass: 'suffix-light', progress: 80 },
  { label: 'INGRESOS', value: '$8,450', suffix: '', suffixClass: '', progress: 64 }
])

const chartData = ref([
  { hour: 8,  value: 32, level: 'normal' },
  { hour: 9,  value: 55, level: 'normal' },
  { hour: 10, value: 78, level: 'high' },
  { hour: 11, value: 92, level: 'high' },
  { hour: 12, value: 70, level: 'normal' },
  { hour: 13, value: 45, level: 'normal' },
  { hour: 14, value: 60, level: 'normal' },
  { hour: 15, value: 82, level: 'peak' },
  { hour: 16, value: 88, level: 'peak' },
  { hour: 17, value: 65, level: 'normal' },
  { hour: 18, value: 40, level: 'normal' },
  { hour: 19, value: 22, level: 'normal' }
])

const notifications = ref([
  { type: 'info',    title: 'PRÓXIMA CLASE',   message: 'Clase programada en 30 minutos' },
  { type: 'danger',  title: 'MANTENIMIENTO',   message: 'Vehículo #07 requiere revisión' },
  { type: 'success', title: 'NUEVO REGISTRO',  message: 'María García se registró hoy' }
])

const logout = async () => {
  authStore.logout()
  router.push('/login')
}

onMounted(() => {
  if (!authStore.isAuthenticated) {
    router.push('/login')
  }
})
</script>

<style scoped>
/* ===== Layout base ===== */
.dashboard-wrapper {
  min-height: 100vh;
  background: #f5f1ec;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  padding: 2rem 1.5rem;
}

.dashboard-inner {
  max-width: 1280px;
  margin: 0 auto;
}

/* ===== Header ===== */
.dashboard-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 1rem;
  margin-bottom: 2rem;
}

.eyebrow-row {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
}

.eyebrow-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: #b87333;
  box-shadow: 0 0 10px rgba(184, 115, 51, 0.5);
}

.eyebrow-text {
  font-size: 0.7rem;
  font-weight: 600;
  letter-spacing: 0.18em;
  color: #b87333;
}

.greeting {
  font-size: 2.25rem;
  font-weight: 300;
  color: #1a2735;
  margin: 0;
  letter-spacing: -0.02em;
}

.date-line {
  font-size: 0.85rem;
  color: #6b7280;
  margin: 0.25rem 0 0 0;
  font-weight: 400;
}

.header-right {
  display: flex;
  gap: 0.75rem;
  align-items: center;
}

.status-pill {
  padding: 0.5rem 0.875rem;
  background: #e8d8cb;
  border-radius: 8px;
  font-size: 0.72rem;
  color: #1a2735;
  font-weight: 600;
  letter-spacing: 0.05em;
  display: flex;
  align-items: center;
  gap: 0.4rem;
}

.status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #2d7a4f;
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}

.logout-btn {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  background: #f5f1ec;
  border: 1px solid #e8d8cb;
  font-size: 1.2rem;
  cursor: pointer;
  transition: all 0.25s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logout-btn:hover {
  background: #e8d8cb;
  transform: scale(1.05);
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #1a2735;
  color: #e8d8cb;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.8rem;
  font-weight: 600;
  letter-spacing: 0.05em;
}

/* ===== Stats grid ===== */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 1rem;
  margin-bottom: 1.25rem;
}

.stat-card {
  background: #ffffff;
  border-radius: 14px;
  padding: 1.25rem;
  position: relative;
  overflow: hidden;
  transition: transform 0.25s ease, box-shadow 0.25s ease;
  box-shadow: 0 1px 3px rgba(26, 39, 53, 0.04);
}

.stat-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 28px rgba(26, 39, 53, 0.1);
}

.stat-accent {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: #b87333;
}

.stat-label {
  font-size: 0.68rem;
  font-weight: 600;
  letter-spacing: 0.12em;
  color: #9ca3af;
  margin: 0.35rem 0 0.6rem 0;
}

.stat-value-row {
  display: flex;
  align-items: baseline;
  gap: 0.5rem;
}

.stat-value {
  font-size: 2rem;
  font-weight: 300;
  color: #1a2735;
  letter-spacing: -0.02em;
  line-height: 1;
}

.stat-suffix {
  font-size: 0.78rem;
  font-weight: 500;
}

.suffix-up { color: #2d7a4f; }
.suffix-muted { color: #6b7280; }
.suffix-light { color: #9ca3af; font-weight: 300; }

.stat-progress-track {
  margin-top: 0.75rem;
  height: 3px;
  background: #f5f1ec;
  border-radius: 2px;
  overflow: hidden;
}

.stat-progress-fill {
  height: 100%;
  background: #b87333;
  border-radius: 2px;
  transition: width 0.6s ease;
}

/* ===== Main grid ===== */
.main-grid {
  display: grid;
  grid-template-columns: minmax(0, 2fr) minmax(0, 1fr);
  gap: 1rem;
}

@media (max-width: 900px) {
  .main-grid {
    grid-template-columns: 1fr;
  }
}

/* ===== Card shared ===== */
.chart-card,
.notif-card {
  background: #ffffff;
  border-radius: 14px;
  padding: 1.5rem;
  box-shadow: 0 1px 3px rgba(26, 39, 53, 0.04);
}

.card-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  margin-bottom: 1.25rem;
  gap: 1rem;
}

.card-eyebrow {
  font-size: 0.68rem;
  font-weight: 600;
  letter-spacing: 0.12em;
  color: #b87333;
  margin: 0 0 0.25rem 0;
}

.card-title {
  font-size: 1.1rem;
  font-weight: 500;
  color: #1a2735;
  margin: 0;
  letter-spacing: -0.01em;
}

.card-meta {
  font-size: 0.75rem;
  color: #9ca3af;
  font-weight: 400;
}

/* ===== Chart ===== */
.chart-bars {
  display: flex;
  align-items: flex-end;
  gap: 0.5rem;
  height: 180px;
  padding: 0 0.25rem;
}

.bar-col {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.4rem;
  height: 100%;
  justify-content: flex-end;
}

.bar {
  width: 100%;
  border-radius: 5px 5px 0 0;
  min-height: 4px;
  transition: transform 0.25s ease, opacity 0.25s ease;
  cursor: pointer;
}

.bar:hover {
  transform: scaleY(1.05);
  transform-origin: bottom;
  opacity: 0.88;
}

.bar--normal { background: #e8d8cb; }
.bar--high   { background: #b87333; }
.bar--peak   { background: #1a2735; }

.bar-label {
  font-size: 0.68rem;
  color: #9ca3af;
  font-weight: 400;
}

.bar-label--active {
  color: #1a2735;
  font-weight: 600;
}

.chart-legend {
  display: flex;
  gap: 1.25rem;
  margin-top: 1.25rem;
  padding-top: 1rem;
  border-top: 1px solid rgba(26, 39, 53, 0.08);
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 0.4rem;
  font-size: 0.75rem;
  color: #6b7280;
}

.legend-swatch {
  width: 10px;
  height: 10px;
  border-radius: 2px;
}

.legend-peak   { background: #1a2735; }
.legend-high   { background: #b87333; }
.legend-normal { background: #e8d8cb; }

/* ===== Notifications ===== */
.notif-list {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.notif-item {
  padding: 0.85rem 1rem;
  border-radius: 8px;
  border-left: 3px solid transparent;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
  transition: transform 0.2s ease;
  cursor: pointer;
}

.notif-item:hover {
  transform: translateX(3px);
}

.notif-item--info {
  background: #faf5ef;
  border-left-color: #b87333;
}

.notif-item--danger {
  background: #fdecec;
  border-left-color: #b84545;
}

.notif-item--success {
  background: #ecf5ef;
  border-left-color: #2d7a4f;
}

.notif-type {
  font-size: 0.68rem;
  font-weight: 600;
  letter-spacing: 0.08em;
  margin: 0 0 0.25rem 0;
}

.notif-item--info    .notif-type { color: #b87333; }
.notif-item--danger  .notif-type { color: #b84545; }
.notif-item--success .notif-type { color: #2d7a4f; }

.notif-msg {
  font-size: 0.82rem;
  color: #1a2735;
  margin: 0;
  line-height: 1.4;
  font-weight: 400;
}

/* ===== Responsive ===== */
@media (max-width: 640px) {
  .dashboard-wrapper { padding: 1.25rem 1rem; }
  .greeting { font-size: 1.75rem; }
  .stat-value { font-size: 1.65rem; }
  .chart-bars { height: 140px; }
}
</style>
