<template>
  <div class="view-wrapper">
    <DataTable
      eyebrow="OPERATIVO"
      title="Gestión de Asignaciones"
      :columns="columns"
      :data="asignaciones"
      @add="handleAdd"
      @edit="handleEdit"
      @delete="handleDelete"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import DataTable from '../components/DataTable.vue'

const columns = ref([
  { key: 'estudiante', label: 'Estudiante' },
  { key: 'instructor', label: 'Instructor' },
  { key: 'vehiculo', label: 'Vehículo' },
  { key: 'fecha', label: 'Fecha' },
  { key: 'hora', label: 'Hora' }
])

const asignaciones = ref([
  { id: 1, estudiante: 'María García López', instructor: 'David Flores', vehiculo: 'ABC-1234', fecha: '2026-04-22', hora: '09:00' },
  { id: 2, estudiante: 'Juan Pérez', instructor: 'Patricia Núñez', vehiculo: 'XYZ-5678', fecha: '2026-04-22', hora: '10:30' },
  { id: 3, estudiante: 'Ana Martínez', instructor: 'Javier Morales', vehiculo: 'DEF-9012', fecha: '2026-04-22', hora: '14:00' },
  { id: 4, estudiante: 'Carlos López', instructor: 'David Flores', vehiculo: 'GHI-3456', fecha: '2026-04-23', hora: '09:00' },
  { id: 5, estudiante: 'Sofia Díaz', instructor: 'Mónica Salinas', vehiculo: 'JKL-7890', fecha: '2026-04-23', hora: '11:00' },
])

const handleAdd = () => console.log('Agregar asignación')
const handleEdit = (row) => console.log('Editar asignación:', row)
const handleDelete = (row) => console.log('Eliminar asignación:', row)
</script>

<style scoped>
.view-wrapper { padding: 2rem 1.5rem; max-width: 1400px; margin: 0 auto; }
@media (max-width: 768px) { .view-wrapper { padding: 1.5rem 1rem; } }
@media (max-width: 640px) { .view-wrapper { padding: 1rem; } }
</style>
