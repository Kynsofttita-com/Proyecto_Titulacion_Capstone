<script setup>
import { useRoute } from 'vue-router'
import { useAuthStore } from './stores/auth'
import MainLayout from './layouts/MainLayout.vue'

const route = useRoute()
const authStore = useAuthStore()

authStore.checkAuth()

const isAuthPage = route.path === '/login'
</script>

<template>
  <MainLayout v-if="!isAuthPage && authStore.isAuthenticated" />
  <router-view v-else />
</template>

<style>
html, body {
  height: 100%;
  width: 100%;
  margin: 0;
  padding: 0;
}
</style>
