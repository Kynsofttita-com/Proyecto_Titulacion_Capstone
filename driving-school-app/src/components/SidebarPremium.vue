<template>
  <aside class="sidebar" :class="{ collapsed: isCollapsed }">
    <!-- Logo -->
    <div class="sb-logo">
      <div class="sb-logo-icon">🚗</div>
      <div v-if="!isCollapsed">
        <div class="sb-logo-text">DriveSchool</div>
        <div class="sb-logo-sub">Sistema de Gestión</div>
      </div>
    </div>

    <!-- Navigation Sections -->
    <nav class="nav-section">
      <div class="nav-section-label">Principal</div>
      <router-link
        to="/dashboard"
        class="nav-item"
        :class="{ active: isActive('/dashboard') }"
      >
        <span class="nav-icon">📊</span>
        <span class="nav-label">Dashboard</span>
      </router-link>

      <div class="nav-section-label">Académico</div>
      <router-link
        to="/estudiantes"
        class="nav-item"
        :class="{ active: isActive('/estudiantes') }"
      >
        <span class="nav-icon">👨‍🎓</span>
        <span class="nav-label">Estudiantes</span>
        <span class="nav-badge">247</span>
      </router-link>

      <router-link
        to="/instructores"
        class="nav-item"
        :class="{ active: isActive('/instructores') }"
      >
        <span class="nav-icon">👨‍🏫</span>
        <span class="nav-label">Instructores</span>
      </router-link>

      <div class="nav-section-label">Operativo</div>
      <router-link
        to="/vehiculos"
        class="nav-item"
        :class="{ active: isActive('/vehiculos') }"
      >
        <span class="nav-icon">🚙</span>
        <span class="nav-label">Vehículos</span>
      </router-link>

      <router-link
        to="/asignaciones"
        class="nav-item"
        :class="{ active: isActive('/asignaciones') }"
      >
        <span class="nav-icon">📋</span>
        <span class="nav-label">Asignaciones</span>
      </router-link>

      <div class="nav-section-label">Finanzas</div>
      <router-link
        to="/cobros"
        class="nav-item"
        :class="{ active: isActive('/cobros') }"
      >
        <span class="nav-icon">💳</span>
        <span class="nav-label">Cobros</span>
      </router-link>

      <router-link
        to="/reportes"
        class="nav-item"
        :class="{ active: isActive('/reportes') }"
      >
        <span class="nav-icon">📈</span>
        <span class="nav-label">Reportes</span>
      </router-link>

      <div class="nav-section-label">Sistema</div>
      <router-link
        to="/configuracion"
        class="nav-item"
        :class="{ active: isActive('/configuracion') }"
      >
        <span class="nav-icon">⚙️</span>
        <span class="nav-label">Configuración</span>
      </router-link>
    </nav>

    <!-- User Card -->
    <div class="sb-user">
      <div class="sb-user-card">
        <div class="avatar" :style="{ background: userColor }">
          {{ userInitials }}
        </div>
        <div v-if="!isCollapsed">
          <div class="sb-user-name">{{ userName }}</div>
          <span class="sb-user-role" :style="{ background: userColor + '33', color: userColor }">
            {{ userRole }}
          </span>
        </div>
      </div>
    </div>

    <!-- Toggle Button -->
    <button class="sb-toggle" @click="toggleSidebar" :title="isCollapsed ? 'Expandir' : 'Contraer'">
      {{ isCollapsed ? '▶' : '◀' }}
    </button>
  </aside>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const route = useRoute()
const authStore = useAuthStore()
const isCollapsed = ref(false)

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

const userRole = computed(() => {
  return authStore.role || 'USUARIO'
})

const userColor = computed(() => {
  const roleColors = {
    ADMIN: '#f59e0b',
    ADMINISTRATIVO: '#06b6d4',
    INSTRUCTOR: '#10b981',
    ESTUDIANTE: '#8b5cf6'
  }
  return roleColors[userRole.value] || '#f59e0b'
})

const toggleSidebar = () => {
  isCollapsed.value = !isCollapsed.value
}

const isActive = (path) => {
  return route.path === path
}
</script>

<style scoped>
:root {
  --bg2: #111e2a;
  --bg3: #1a2e3f;
  --border: #1e3a50;
  --text: #e2eaf4;
  --text2: #94b3c8;
  --text3: #4a6a82;
  --accent: #f59e0b;
}

.sidebar {
  width: 240px;
  background: var(--bg2);
  border-right: 1px solid var(--border);
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  transition: width 0.25s ease;
  position: relative;
  overflow: hidden;
  height: 100vh;
  font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
}

.sidebar.collapsed {
  width: 68px;
}

/* ===== LOGO ===== */
.sb-logo {
  padding: 22px 18px 18px;
  display: flex;
  align-items: center;
  gap: 12px;
  border-bottom: 1px solid var(--border);
  font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
}

.sb-logo-icon {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  flex-shrink: 0;
  background: linear-gradient(135deg, var(--accent), #ea580c);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
}

.sb-logo-text {
  font-size: 15px;
  font-weight: 700;
  white-space: nowrap;
  overflow: hidden;
  letter-spacing: -0.3px;
}

.sb-logo-sub {
  font-size: 10px;
  color: var(--text3);
  white-space: nowrap;
  overflow: hidden;
  font-weight: 400;
}

/* ===== NAVIGATION ===== */
.nav-section {
  padding: 10px 8px;
  flex: 1;
  overflow-y: auto;
}

.nav-section-label {
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 1px;
  text-transform: uppercase;
  color: var(--text3);
  padding: 8px 10px 4px;
  white-space: nowrap;
  overflow: hidden;
  transition: opacity 0.2s;
}

.sidebar.collapsed .nav-section-label {
  opacity: 0;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 12px;
  margin: 2px 0;
  border-radius: 10px;
  cursor: pointer;
  border-left: 2px solid transparent;
  transition: all 0.15s;
  color: var(--text2);
  font-size: 13px;
  position: relative;
  text-decoration: none;
}

.sidebar.collapsed .nav-item {
  justify-content: center;
  padding: 12px 0;
}

.nav-item:hover {
  background: rgba(255, 255, 255, 0.04);
  color: var(--text);
}

.nav-item.active {
  background: rgba(245, 158, 11, 0.1);
  border-left-color: var(--accent);
  color: var(--accent);
  font-weight: 600;
}

.sidebar.collapsed .nav-item.active {
  border-left-color: transparent;
  border-radius: 10px;
}

.nav-icon {
  font-size: 18px;
  flex-shrink: 0;
}

.nav-label {
  white-space: nowrap;
  overflow: hidden;
  transition: opacity 0.2s;
}

.sidebar.collapsed .nav-label {
  opacity: 0;
  width: 0;
}

.nav-badge {
  margin-left: auto;
  min-width: 18px;
  height: 18px;
  border-radius: 9px;
  background: var(--accent);
  color: #0d1b26;
  font-size: 10px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 5px;
}

.sidebar.collapsed .nav-badge {
  display: none;
}

/* ===== USER CARD ===== */
.sb-user {
  padding: 12px;
  border-top: 1px solid var(--border);
}

.sb-user-card {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 12px;
  background: var(--bg3);
  border-radius: 10px;
  border: 1px solid var(--border);
}

.sidebar.collapsed .sb-user-card {
  justify-content: center;
  padding: 10px 0;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: 700;
  color: #0d1b26;
}

.sb-user-name {
  font-size: 12px;
  font-weight: 600;
  white-space: nowrap;
  overflow: hidden;
}

.sb-user-role {
  font-size: 10px;
  padding: 2px 6px;
  border-radius: 4px;
  display: inline-block;
  margin-top: 2px;
}

.sidebar.collapsed .sb-user-name,
.sidebar.collapsed .sb-user-role {
  display: none;
}

/* ===== TOGGLE ===== */
.sb-toggle {
  position: absolute;
  top: 50%;
  right: -13px;
  transform: translateY(-50%);
  width: 26px;
  height: 26px;
  border-radius: 50%;
  background: var(--bg3);
  border: 1px solid var(--border);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 11px;
  color: var(--text2);
  transition: all 0.2s;
  z-index: 10;
}

.sb-toggle:hover {
  background: #0d1b26;
  color: var(--text);
}

/* ===== SCROLLBAR ===== */
.nav-section::-webkit-scrollbar {
  width: 4px;
}

.nav-section::-webkit-scrollbar-track {
  background: transparent;
}

.nav-section::-webkit-scrollbar-thumb {
  background: var(--border);
  border-radius: 2px;
}

.nav-section::-webkit-scrollbar-thumb:hover {
  background: var(--text3);
}

/* ===== RESPONSIVE ===== */
@media (max-width: 768px) {
  .sidebar {
    width: 68px;
  }

  .sidebar.collapsed {
    width: 240px;
  }
}
</style>
