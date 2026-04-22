<template>
  <div class="view-wrapper">
    <div class="reports-header">
      <div>
        <p class="eyebrow">— FINANZAS</p>
        <h1 class="title">Reportes y Análisis</h1>
      </div>
      <div class="header-actions">
        <select class="filter-select">
          <option>Este Mes</option>
          <option>Mes Anterior</option>
          <option>Últimos 3 Meses</option>
          <option>Últimos 12 Meses</option>
        </select>
        <button class="btn-export">📥 Exportar PDF</button>
      </div>
    </div>

    <div class="reports-grid">
      <article class="report-card">
        <p class="card-eyebrow">INGRESOS</p>
        <div class="card-value">$28,450</div>
        <p class="card-change">↑ 12% vs mes anterior</p>
      </article>

      <article class="report-card">
        <p class="card-eyebrow">GASTOS</p>
        <div class="card-value">$12,800</div>
        <p class="card-change">↑ 5% vs mes anterior</p>
      </article>

      <article class="report-card">
        <p class="card-eyebrow">CLASES IMPARTIDAS</p>
        <div class="card-value">287</div>
        <p class="card-change">↑ 18 clases vs mes anterior</p>
      </article>

      <article class="report-card">
        <p class="card-eyebrow">TASA DE APROBACIÓN</p>
        <div class="card-value">94%</div>
        <p class="card-change">↑ 3% vs mes anterior</p>
      </article>
    </div>

    <div class="reports-table">
      <h3 class="section-title">Detalle por Estudiante</h3>
      <DataTable
        eyebrow=""
        title=""
        :columns="columns"
        :data="reportes"
        @add="() => {}"
        @edit="() => {}"
        @delete="() => {}"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import DataTable from '../components/DataTable.vue'

const columns = ref([
  { key: 'estudiante', label: 'Estudiante' },
  { key: 'clases', label: 'Clases Completadas' },
  { key: 'ingresos', label: 'Total Pagado' },
  { key: 'promedio', label: 'Promedio' },
  { key: 'estado', label: 'Estado' }
])

const reportes = ref([
  { id: 1, estudiante: 'María García López', clases: 12, ingresos: '$1,350', promedio: '9.2', estado: 'En Curso' },
  { id: 2, estudiante: 'Juan Pérez', clases: 8, ingresos: '$975', promedio: '8.5', estado: 'En Curso' },
  { id: 3, estudiante: 'Ana Martínez', clases: 5, ingresos: '$600', promedio: '7.8', estado: 'Suspendido' },
  { id: 4, estudiante: 'Carlos López', clases: 15, ingresos: '$1,725', promedio: '9.5', estado: 'Completado' },
])
</script>

<style scoped>
.view-wrapper {
  padding: 2rem 1.5rem;
  max-width: 1400px;
  margin: 0 auto;
}

.reports-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 2rem;
  gap: 1rem;
}

.eyebrow {
  font-size: 0.68rem;
  font-weight: 600;
  letter-spacing: 0.12em;
  color: #b87333;
  margin: 0 0 0.25rem 0;
}

.title {
  font-size: 2.25rem;
  font-weight: 300;
  color: #1a2735;
  margin: 0;
  letter-spacing: -0.02em;
}

.header-actions {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.filter-select {
  padding: 0.75rem 1rem;
  border: 1px solid #e8d8cb;
  border-radius: 10px;
  background: #ffffff;
  font-size: 0.9rem;
  cursor: pointer;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
}

.btn-export {
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

.btn-export:hover {
  background: #a56a2b;
}

.reports-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 1rem;
  margin-bottom: 2rem;
}

.report-card {
  background: #ffffff;
  border-radius: 14px;
  padding: 1.5rem;
  box-shadow: 0 1px 3px rgba(26, 39, 53, 0.04);
}

.card-eyebrow {
  font-size: 0.68rem;
  font-weight: 600;
  letter-spacing: 0.12em;
  color: #9ca3af;
  margin: 0 0 0.75rem 0;
}

.card-value {
  font-size: 2rem;
  font-weight: 300;
  color: #1a2735;
  margin-bottom: 0.5rem;
}

.card-change {
  font-size: 0.85rem;
  color: #2d7a4f;
  margin: 0;
}

.reports-table {
  background: #ffffff;
  border-radius: 14px;
  padding: 1.5rem;
  box-shadow: 0 1px 3px rgba(26, 39, 53, 0.04);
}

.section-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #1a2735;
  margin: 0 0 1.5rem 0;
}

@media (max-width: 768px) {
  .view-wrapper { padding: 1.5rem 1rem; }
  .reports-header { flex-direction: column; }
  .header-actions { width: 100%; flex-direction: column; }
  .filter-select, .btn-export { width: 100%; }
  .reports-grid { grid-template-columns: repeat(2, 1fr); }
  .title { font-size: 1.75rem; }
}

@media (max-width: 640px) {
  .view-wrapper { padding: 1rem; }
  .reports-grid { grid-template-columns: 1fr; }
  .title { font-size: 1.5rem; }
}
</style>
