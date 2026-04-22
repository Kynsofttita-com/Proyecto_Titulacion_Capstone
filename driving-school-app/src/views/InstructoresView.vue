<template>
  <div class="view-wrapper">
    <DataTable
      eyebrow="ACADÉMICO"
      title="Gestión de Instructores"
      :columns="columns"
      :data="instructores"
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
  { key: 'nombre', label: 'Nombre Completo' },
  { key: 'email', label: 'Email' },
  { key: 'telefono', label: 'Teléfono' },
  { key: 'especializacion', label: 'Especialización' },
  { key: 'estudiantes', label: 'Estudiantes Asignados' }
])

const instructores = ref([
  { id: 1, nombre: 'David Flores Ramírez', email: 'david.flores@kynsoft.com', telefono: '+593 98765450', especializacion: 'Conducción Defensiva', estudiantes: 8 },
  { id: 2, nombre: 'Andrés Castillo Gómez', email: 'andres.castillo@kynsoft.com', telefono: '+593 98765451', especializacion: 'Conducción de Carga', estudiantes: 6 },
  { id: 3, nombre: 'Patricia Núñez Oviedo', email: 'patricia.nunez@kynsoft.com', telefono: '+593 98765452', especializacion: 'Conducción General', estudiantes: 10 },
  { id: 4, nombre: 'Javier Morales Rivera', email: 'javier.morales@kynsoft.com', telefono: '+593 98765453', especializacion: 'Seguridad Vial', estudiantes: 7 },
  { id: 5, nombre: 'Mónica Salinas Sandoval', email: 'monica.salinas@kynsoft.com', telefono: '+593 98765454', especializacion: 'Conducción General', estudiantes: 9 },
])

const handleAdd = () => console.log('Agregar instructor')
const handleEdit = (row) => console.log('Editar instructor:', row)
const handleDelete = (row) => console.log('Eliminar instructor:', row)
</script>

<style scoped>
.view-wrapper {
  padding: 2rem 1.5rem;
  max-width: 1400px;
  margin: 0 auto;
}

@media (max-width: 768px) {
  .view-wrapper { padding: 1.5rem 1rem; }
}

@media (max-width: 640px) {
  .view-wrapper { padding: 1rem; }
}
</style>
