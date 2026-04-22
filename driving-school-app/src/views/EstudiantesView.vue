<template>
  <div class="view-wrapper">
    <DataTable
      eyebrow="ACADÉMICO"
      title="Gestión de Estudiantes"
      :columns="columns"
      :data="estudiantes"
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
  { key: 'estado', label: 'Estado' },
  { key: 'clases', label: 'Clases Completadas' }
])

const estudiantes = ref([
  { id: 1, nombre: 'María García López', email: 'maria.garcia@email.com', telefono: '+593 98765432', estado: 'Activo', clases: 12 },
  { id: 2, nombre: 'Juan Pérez Rodríguez', email: 'juan.perez@email.com', telefono: '+593 98765433', estado: 'Activo', clases: 8 },
  { id: 3, nombre: 'Ana Martínez Silva', email: 'ana.martinez@email.com', telefono: '+593 98765434', estado: 'Suspendido', clases: 5 },
  { id: 4, nombre: 'Carlos López Fernández', email: 'carlos.lopez@email.com', telefono: '+593 98765435', estado: 'Activo', clases: 15 },
  { id: 5, nombre: 'Sofia Díaz González', email: 'sofia.diaz@email.com', telefono: '+593 98765436', estado: 'Activo', clases: 10 },
  { id: 6, nombre: 'Miguel Rodríguez Martín', email: 'miguel.rodriguez@email.com', telefono: '+593 98765437', estado: 'Activo', clases: 20 },
  { id: 7, nombre: 'Laura Fernández García', email: 'laura.fernandez@email.com', telefono: '+593 98765438', estado: 'Inactivo', clases: 3 },
  { id: 8, nombre: 'Pedro Sánchez López', email: 'pedro.sanchez@email.com', telefono: '+593 98765439', estado: 'Activo', clases: 18 },
  { id: 9, nombre: 'Isabella Ruiz Torres', email: 'isabella.ruiz@email.com', telefono: '+593 98765440', estado: 'Activo', clases: 7 },
  { id: 10, nombre: 'Roberto Moreno Jiménez', email: 'roberto.moreno@email.com', telefono: '+593 98765441', estado: 'Activo', clases: 14 },
])

const handleAdd = () => {
  console.log('Agregar nuevo estudiante')
}

const handleEdit = (row) => {
  console.log('Editar estudiante:', row)
}

const handleDelete = (row) => {
  console.log('Eliminar estudiante:', row)
}
</script>

<style scoped>
.view-wrapper {
  padding: 2rem 1.5rem;
  max-width: 1400px;
  margin: 0 auto;
}

@media (max-width: 768px) {
  .view-wrapper {
    padding: 1.5rem 1rem;
  }
}

@media (max-width: 640px) {
  .view-wrapper {
    padding: 1rem;
  }
}
</style>
