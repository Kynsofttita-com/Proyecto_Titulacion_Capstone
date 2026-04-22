<template>
  <div class="flex h-screen" style="background: #f5f1ec">
    <SidebarPremium />
    <main class="flex-1 overflow-y-auto">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import SidebarPremium from '../components/SidebarPremium.vue'
</script>
