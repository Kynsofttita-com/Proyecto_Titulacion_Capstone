import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import api from '../services/api'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)
  const token = ref(localStorage.getItem('token') || null)
  const role = ref(localStorage.getItem('role') || null)
  const isAuthenticated = computed(() => !!token.value)

  const login = async (email, password) => {
    try {
      const response = await api.post('/api/auth/login', { email, password })
      const { data } = response

      token.value = data.token
      user.value = { id: data.user.id, email: data.user.email }
      role.value = data.user.roles?.[0] || null

      localStorage.setItem('token', data.token)
      localStorage.setItem('role', role.value)

      return true
    } catch (error) {
      console.error('Login error:', error.response?.data?.message || error.message)
      return false
    }
  }

  const logout = () => {
    user.value = null
    token.value = null
    role.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('role')
  }

  const checkAuth = () => {
    const storedToken = localStorage.getItem('token')
    if (storedToken) {
      token.value = storedToken
      role.value = localStorage.getItem('role')
    }
  }

  return {
    user,
    token,
    role,
    isAuthenticated,
    login,
    logout,
    checkAuth
  }
})
