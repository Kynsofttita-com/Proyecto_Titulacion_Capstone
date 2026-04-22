<template>
  <div class="data-table-wrapper">
    <div class="table-header">
      <div>
        <p class="table-eyebrow">— {{ eyebrow }}</p>
        <h2 class="table-title">{{ title }}</h2>
      </div>
      <div class="table-controls">
        <div class="search-box">
          <input
            v-model="searchQuery"
            type="text"
            placeholder="🔍 Buscar..."
            class="search-input"
          />
        </div>
        <button class="btn-action" @click="$emit('add')">
          ➕ Agregar
        </button>
      </div>
    </div>

    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th v-for="col in columns" :key="col.key">
              {{ col.label }}
            </th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="row in filteredData" :key="row.id" class="table-row">
            <td v-for="col in columns" :key="col.key">
              {{ row[col.key] }}
            </td>
            <td class="actions-cell">
              <button class="btn-icon" title="Editar" @click="$emit('edit', row)">✏️</button>
              <button class="btn-icon btn-danger" title="Eliminar" @click="$emit('delete', row)">🗑️</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="table-footer">
      <p class="footer-text">
        Mostrando {{ filteredData.length }} de {{ data.length }} registros
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

defineProps({
  eyebrow: String,
  title: String,
  columns: Array,
  data: Array
})

defineEmits(['add', 'edit', 'delete'])

const searchQuery = ref('')

const filteredData = computed(() => {
  if (!searchQuery.value) return props.data
  const query = searchQuery.value.toLowerCase()
  return props.data.filter(row =>
    Object.values(row).some(val =>
      String(val).toLowerCase().includes(query)
    )
  )
})
</script>

<style scoped>
:root {
  --bg2: #111e2a;
  --bg3: #1a2e3f;
  --border: #1e3a50;
  --text: #e2eaf4;
  --text2: #94b3c8;
  --text3: #4a6a82;
  --accent: #f59e0b;
}

.data-table-wrapper {
  background: #ffffff;
  border-radius: 14px;
  padding: 1.5rem;
  box-shadow: 0 1px 3px rgba(26, 39, 53, 0.04);
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 1.5rem;
  gap: 1rem;
}

.table-eyebrow {
  font-size: 0.68rem;
  font-weight: 600;
  letter-spacing: 0.12em;
  color: #b87333;
  margin: 0 0 0.25rem 0;
}

.table-title {
  font-size: 1.3rem;
  font-weight: 600;
  color: #1a2735;
  margin: 0;
}

.table-controls {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.search-box {
  position: relative;
}

.search-input {
  padding: 0.75rem 1rem;
  border: 1px solid #e8d8cb;
  border-radius: 10px;
  background: #f5f1ec;
  font-size: 0.9rem;
  width: 250px;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  transition: border-color 0.2s;
}

.search-input:focus {
  outline: none;
  border-color: #b87333;
  background: #ffffff;
}

.btn-action {
  padding: 0.75rem 1.25rem;
  background: #b87333;
  color: #ffffff;
  border: none;
  border-radius: 10px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
}

.btn-action:hover {
  background: #a56a2b;
  transform: translateY(-2px);
}

.table-container {
  overflow-x: auto;
  border-radius: 10px;
  border: 1px solid #e8d8cb;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 0.9rem;
}

.data-table thead {
  background: #f5f1ec;
}

.data-table th {
  padding: 1rem;
  text-align: left;
  font-weight: 600;
  color: #1a2735;
  border-bottom: 2px solid #e8d8cb;
}

.data-table td {
  padding: 1rem;
  border-bottom: 1px solid #f5f1ec;
  color: #4b5563;
}

.table-row:hover {
  background: #f5f1ec;
}

.actions-cell {
  display: flex;
  gap: 0.5rem;
}

.btn-icon {
  background: none;
  border: none;
  font-size: 1.2rem;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 6px;
  transition: all 0.2s;
}

.btn-icon:hover {
  background: #e8d8cb;
}

.btn-icon.btn-danger:hover {
  background: rgba(184, 69, 69, 0.15);
}

.table-footer {
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid #e8d8cb;
}

.footer-text {
  font-size: 0.85rem;
  color: #9ca3af;
  margin: 0;
}

@media (max-width: 900px) {
  .table-header {
    flex-direction: column;
  }

  .table-controls {
    width: 100%;
    flex-direction: column;
  }

  .search-input {
    width: 100%;
  }

  .btn-action {
    width: 100%;
  }

  .data-table {
    font-size: 0.8rem;
  }

  .data-table th,
  .data-table td {
    padding: 0.75rem;
  }
}

@media (max-width: 640px) {
  .data-table-wrapper {
    padding: 1rem;
  }

  .table-title {
    font-size: 1.1rem;
  }

  .search-input {
    width: 100%;
  }

  .data-table {
    font-size: 0.75rem;
  }

  .data-table th,
  .data-table td {
    padding: 0.5rem;
  }

  .actions-cell {
    gap: 0.25rem;
  }

  .btn-icon {
    font-size: 1rem;
  }
}
</style>
