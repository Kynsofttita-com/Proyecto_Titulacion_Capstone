<template>
  <div class="p-6 max-w-7xl mx-auto">
    <h2 class="text-2xl font-bold text-gray-800 mb-6">Dashboard</h2>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      <div class="bg-white rounded-lg shadow p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-600 text-sm">Estudiantes Activos</p>
            <p class="text-3xl font-bold text-gray-800">247</p>
          </div>
          <div class="text-4xl">👨‍🎓</div>
        </div>
      </div>

      <div class="bg-white rounded-lg shadow p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-600 text-sm">Clases Hoy</p>
            <p class="text-3xl font-bold text-gray-800">34</p>
          </div>
          <div class="text-4xl">📚</div>
        </div>
      </div>

      <div class="bg-white rounded-lg shadow p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-600 text-sm">Vehículos</p>
            <p class="text-3xl font-bold text-gray-800">12/15</p>
          </div>
          <div class="text-4xl">🚗</div>
        </div>
      </div>

      <div class="bg-white rounded-lg shadow p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-600 text-sm">Ingresos</p>
            <p class="text-3xl font-bold text-green-600">$8,450</p>
          </div>
          <div class="text-4xl">💰</div>
        </div>
      </div>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <div class="lg:col-span-2 bg-white rounded-lg shadow p-6">
        <h3 class="text-lg font-semibold text-gray-800 mb-4">Gráfico de Actividades</h3>
        <div class="h-64 bg-gray-100 rounded flex items-center justify-center">
          <p class="text-gray-500">Gráfico placeholder de barras</p>
        </div>
      </div>

      <div class="bg-white rounded-lg shadow p-6">
        <h3 class="text-lg font-semibold text-gray-800 mb-4">Alertas/Notificaciones</h3>
        <div class="space-y-3">
          <div class="p-3 bg-yellow-50 border-l-4 border-yellow-400 text-sm text-yellow-700">
            📢 Clase programada en 30 minutos
          </div>
          <div class="p-3 bg-red-50 border-l-4 border-red-400 text-sm text-red-700">
            ⚠️ Vehículo requiere mantenimiento
          </div>
          <div class="p-3 bg-blue-50 border-l-4 border-blue-400 text-sm text-blue-700">
            ℹ️ Nuevo estudiante registrado
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// Dashboard data loaded here
</script>
