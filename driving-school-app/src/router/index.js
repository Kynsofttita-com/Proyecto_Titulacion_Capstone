import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import LoginViewPremium from '../views/LoginViewPremium.vue'
import DashboardView from '../views/DashboardView.vue'
import EstudiantesView from '../views/EstudiantesView.vue'
import InstructoresView from '../views/InstructoresView.vue'
import VehiculosView from '../views/VehiculosView.vue'
import AsignacionesView from '../views/AsignacionesView.vue'
import CobrosView from '../views/CobrosView.vue'
import ReportesView from '../views/ReportesView.vue'
import ConfiguracionView from '../views/ConfiguracionView.vue'
import MainLayout from '../layouts/MainLayout.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: LoginViewPremium,
    meta: { requiresAuth: false }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: DashboardView,
    meta: { requiresAuth: true },
    layout: MainLayout
  },
  {
    path: '/estudiantes',
    name: 'Estudiantes',
    component: EstudiantesView,
    meta: { requiresAuth: true },
    layout: MainLayout
  },
  {
    path: '/instructores',
    name: 'Instructores',
    component: InstructoresView,
    meta: { requiresAuth: true },
    layout: MainLayout
  },
  {
    path: '/vehiculos',
    name: 'Vehículos',
    component: VehiculosView,
    meta: { requiresAuth: true },
    layout: MainLayout
  },
  {
    path: '/asignaciones',
    name: 'Asignaciones',
    component: AsignacionesView,
    meta: { requiresAuth: true },
    layout: MainLayout
  },
  {
    path: '/cobros',
    name: 'Cobros',
    component: CobrosView,
    meta: { requiresAuth: true },
    layout: MainLayout
  },
  {
    path: '/reportes',
    name: 'Reportes',
    component: ReportesView,
    meta: { requiresAuth: true },
    layout: MainLayout
  },
  {
    path: '/configuracion',
    name: 'Configuración',
    component: ConfiguracionView,
    meta: { requiresAuth: true },
    layout: MainLayout
  },
  {
    path: '/',
    redirect: '/dashboard'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  authStore.checkAuth()

  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/login')
  } else if (to.path === '/login' && authStore.isAuthenticated) {
    next('/dashboard')
  } else {
    next()
  }
})

export default router
