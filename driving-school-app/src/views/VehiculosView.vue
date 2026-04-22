<template>
  <div class="view-wrapper">
    <DataTable
      eyebrow="OPERATIVO"
      title="Gestión de Vehículos"
      :columns="columns"
      :data="vehiculos"
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
  { key: 'placa', label: 'Placa' },
  { key: 'modelo', label: 'Modelo' },
  { key: 'anio', label: 'Año' },
  { key: 'estado', label: 'Estado' },
  { key: 'proxima_revision', label: 'Próxima Revisión' }
])

const vehiculos = ref([
  { id: 1, placa: 'ABC-1234', modelo: 'Toyota Corolla', anio: 2022, estado: 'Operativo', proxima_revision: '2026-06-15' },
  { id: 2, placa: 'XYZ-5678', modelo: 'Honda Civic', anio: 2021, estado: 'Operativo', proxima_revision: '2026-05-20' },
  { id: 3, placa: 'DEF-9012', modelo: 'Hyundai Elantra', anio: 2023, estado: 'Mantenimiento', proxima_revision: '2026-07-10' },
  { id: 4, placa: 'GHI-3456', modelo: 'Mazda 3', anio: 2020, estado: 'Operativo', proxima_revision: '2026-04-30' },
  { id: 5, placa: 'JKL-7890', modelo: 'Kia Cerato', anio: 2022, estado: 'Operativo', proxima_revision: '2026-08-01' },
  { id: 6, placa: 'MNO-2345', modelo: 'Toyota Yaris', anio: 2021, estado: 'Fuera de Servicio', proxima_revision: '2026-12-15' },
  { id: 7, placa: 'PQR-6789', modelo: 'Chevrolet Aveo', anio: 2019, estado: 'Operativo', proxima_revision: '2026-05-05' },
  { id: 8, placa: 'STU-0123', modelo: 'Nissan Versa', anio: 2023, estado: 'Operativo', proxima_revision: '2026-09-20' },
  { id: 9, placa: 'VWX-4567', modelo: 'Hyundai I10', anio: 2022, estado: 'Operativo', proxima_revision: '2026-07-15' },
  { id: 10, placa: 'YZA-8901', modelo: 'Volkswagen Polo', anio: 2021, estado: 'Mantenimiento', proxima_revision: '2026-04-25' },
  { id: 11, placa: 'BCD-2345', modelo: 'Toyota Hilux', anio: 2022, estado: 'Operativo', proxima_revision: '2026-08-10' },
  { id: 12, placa: 'EFG-6789', modelo: 'Ford Ranger', anio: 2020, estado: 'Operativo', proxima_revision: '2026-06-30' },
])

const handleAdd = () => console.log('Agregar vehículo')
const handleEdit = (row) => console.log('Editar vehículo:', row)
const handleDelete = (row) => console.log('Eliminar vehículo:', row)
</script>

<style scoped>
.view-wrapper { padding: 2rem 1.5rem; max-width: 1400px; margin: 0 auto; }
@media (max-width: 768px) { .view-wrapper { padding: 1.5rem 1rem; } }
@media (max-width: 640px) { .view-wrapper { padding: 1rem; } }
</style>
