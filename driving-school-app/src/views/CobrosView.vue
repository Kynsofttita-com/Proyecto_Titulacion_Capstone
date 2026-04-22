<template>
  <div class="view-wrapper">
    <DataTable
      eyebrow="FINANZAS"
      title="Gestión de Cobros"
      :columns="columns"
      :data="cobros"
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
  { key: 'concepto', label: 'Concepto' },
  { key: 'monto', label: 'Monto' },
  { key: 'fecha', label: 'Fecha de Cobro' },
  { key: 'estado', label: 'Estado' }
])

const cobros = ref([
  { id: 1, estudiante: 'María García López', concepto: 'Matrícula Mes 1', monto: '$450.00', fecha: '2026-04-01', estado: 'Pagado' },
  { id: 2, estudiante: 'Juan Pérez', concepto: 'Clase Adicional', monto: '$75.00', fecha: '2026-04-05', estado: 'Pagado' },
  { id: 3, estudiante: 'Ana Martínez', concepto: 'Matrícula Mes 2', monto: '$450.00', fecha: '2026-04-15', estado: 'Pendiente' },
  { id: 4, estudiante: 'Carlos López', concepto: 'Matrícula Mes 1', monto: '$450.00', fecha: '2026-04-01', estado: 'Pagado' },
  { id: 5, estudiante: 'Sofia Díaz', concepto: 'Clase Especial', monto: '$100.00', fecha: '2026-04-10', estado: 'Pagado' },
  { id: 6, estudiante: 'Miguel Rodríguez', concepto: 'Matrícula Mes 2', monto: '$450.00', fecha: '2026-04-15', estado: 'Pagado' },
  { id: 7, estudiante: 'Laura Fernández', concepto: 'Clase Adicional', monto: '$75.00', fecha: '2026-04-20', estado: 'Rechazado' },
])

const handleAdd = () => console.log('Agregar cobro')
const handleEdit = (row) => console.log('Editar cobro:', row)
const handleDelete = (row) => console.log('Eliminar cobro:', row)
</script>

<style scoped>
.view-wrapper { padding: 2rem 1.5rem; max-width: 1400px; margin: 0 auto; }
@media (max-width: 768px) { .view-wrapper { padding: 1.5rem 1rem; } }
@media (max-width: 640px) { .view-wrapper { padding: 1rem; } }
</style>
