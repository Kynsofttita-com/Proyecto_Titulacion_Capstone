<template>
  <div class="min-h-screen" style="background: linear-gradient(135deg, #f5f3f0 0%, #f9f7f5 100%)">
    <div class="p-8 max-w-7xl mx-auto">
      <!-- Header -->
      <div class="mb-8">
        <h2 class="text-4xl font-bold mb-2" style="color: #1a2735">Dashboard</h2>
        <div class="h-1 w-24 rounded" style="background: #b87333"></div>
      </div>

      <!-- Stat Cards -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <!-- Estudiantes Activos -->
        <div class="bg-white rounded-xl shadow-lg p-6 border-t-4 transition-all hover:shadow-xl" style="border-color: #b87333">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium mb-2" style="color: #999">Estudiantes Activos</p>
              <p class="text-4xl font-bold" style="color: #1a2735">247</p>
            </div>
            <div class="w-14 h-14 rounded-full flex items-center justify-center text-2xl" style="background: #b87333; color: white">👨‍🎓</div>
          </div>
        </div>

        <!-- Clases Hoy -->
        <div class="bg-white rounded-xl shadow-lg p-6 border-t-4 transition-all hover:shadow-xl" style="border-color: #b87333">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium mb-2" style="color: #999">Clases Hoy</p>
              <p class="text-4xl font-bold" style="color: #1a2735">34</p>
            </div>
            <div class="w-14 h-14 rounded-full flex items-center justify-center text-2xl" style="background: #b87333; color: white">📚</div>
          </div>
        </div>

        <!-- Vehículos -->
        <div class="bg-white rounded-xl shadow-lg p-6 border-t-4 transition-all hover:shadow-xl" style="border-color: #b87333">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium mb-2" style="color: #999">Vehículos</p>
              <p class="text-4xl font-bold" style="color: #1a2735">12/15</p>
            </div>
            <div class="w-14 h-14 rounded-full flex items-center justify-center text-2xl" style="background: #b87333; color: white">🚗</div>
          </div>
        </div>

        <!-- Ingresos -->
        <div class="bg-white rounded-xl shadow-lg p-6 border-t-4 transition-all hover:shadow-xl" style="border-color: #b87333">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium mb-2" style="color: #999">Ingresos</p>
              <p class="text-4xl font-bold" style="color: #1a2735">$8,450</p>
            </div>
            <div class="w-14 h-14 rounded-full flex items-center justify-center text-2xl" style="background: #b87333; color: white">💰</div>
          </div>
        </div>
      </div>

      <!-- Charts and Alerts Section -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Activity Chart -->
        <div class="lg:col-span-2 bg-white rounded-xl shadow-lg p-6">
          <div class="mb-4 pb-4 border-b-2" style="border-color: #e8d8cb">
            <h3 class="text-xl font-bold" style="color: #1a2735">Gráfico de Actividades</h3>
          </div>
          <div class="h-64 rounded-lg flex items-center justify-center" style="background: linear-gradient(135deg, #f5f3f0 0%, #faf8f6 100%)">
            <p class="text-sm font-medium" style="color: #b87333">📊 Gráfico de barras - Actividades por hora</p>
          </div>
        </div>

        <!-- Notifications -->
        <div class="bg-white rounded-xl shadow-lg p-6">
          <div class="mb-4 pb-4 border-b-2" style="border-color: #e8d8cb">
            <h3 class="text-xl font-bold" style="color: #1a2735">Alertas/Notificaciones</h3>
          </div>
          <div class="space-y-3">
            <!-- Info Alert -->
            <div class="p-4 rounded-lg border-l-4 transition-all hover:shadow-md" style="background: #faf8f6; border-color: #b87333; color: #1a2735">
              <p class="text-sm font-medium">📢 Clase programada en 30 minutos</p>
            </div>

            <!-- Warning Alert -->
            <div class="p-4 rounded-lg border-l-4 transition-all hover:shadow-md" style="background: #fee8e0; border-color: #d9834d; color: #8b4513">
              <p class="text-sm font-medium">⚠️ Vehículo requiere mantenimiento</p>
            </div>

            <!-- Success Alert -->
            <div class="p-4 rounded-lg border-l-4 transition-all hover:shadow-md" style="background: #f0f8f5; border-color: #2d5a4f; color: #1a3a33">
              <p class="text-sm font-medium">ℹ️ Nuevo estudiante registrado</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// Dashboard data loaded here
</script>
