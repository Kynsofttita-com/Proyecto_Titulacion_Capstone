<template>
  <div class="view-wrapper">
    <div class="config-header">
      <div>
        <p class="eyebrow">— SISTEMA</p>
        <h1 class="title">Configuración del Sistema</h1>
      </div>
    </div>

    <div class="config-grid">
      <section class="config-card">
        <h3 class="card-title">👤 Perfil de Usuario</h3>
        <div class="config-item">
          <label>Nombre Completo</label>
          <input type="text" :value="userProfile.name" class="config-input" />
        </div>
        <div class="config-item">
          <label>Email</label>
          <input type="email" :value="userProfile.email" class="config-input" />
        </div>
        <div class="config-item">
          <label>Teléfono</label>
          <input type="tel" :value="userProfile.phone" class="config-input" />
        </div>
        <button class="btn-save">💾 Guardar Cambios</button>
      </section>

      <section class="config-card">
        <h3 class="card-title">🔐 Seguridad</h3>
        <div class="config-item">
          <label>Contraseña Actual</label>
          <input type="password" placeholder="••••••••" class="config-input" />
        </div>
        <div class="config-item">
          <label>Nueva Contraseña</label>
          <input type="password" placeholder="••••••••" class="config-input" />
        </div>
        <div class="config-item">
          <label>Confirmar Contraseña</label>
          <input type="password" placeholder="••••••••" class="config-input" />
        </div>
        <button class="btn-save">🔄 Cambiar Contraseña</button>
      </section>

      <section class="config-card">
        <h3 class="card-title">🎨 Apariencia</h3>
        <div class="config-item">
          <label>Tema</label>
          <select class="config-input">
            <option>Oscuro (por defecto)</option>
            <option>Claro</option>
            <option>Automático</option>
          </select>
        </div>
        <div class="config-item">
          <label>Idioma</label>
          <select class="config-input">
            <option>Español</option>
            <option>English</option>
            <option>Português</option>
          </select>
        </div>
      </section>

      <section class="config-card">
        <h3 class="card-title">📧 Notificaciones</h3>
        <div class="config-toggle">
          <label>
            <input type="checkbox" checked />
            Notificaciones por Email
          </label>
        </div>
        <div class="config-toggle">
          <label>
            <input type="checkbox" checked />
            Alertas de Asignaciones
          </label>
        </div>
        <div class="config-toggle">
          <label>
            <input type="checkbox" />
            Reportes Semanales
          </label>
        </div>
        <button class="btn-save">✅ Aplicar Preferencias</button>
      </section>

      <section class="config-card config-danger">
        <h3 class="card-title">⚠️ Zona de Peligro</h3>
        <p class="danger-text">Estas acciones no se pueden deshacer.</p>
        <button class="btn-danger">🚪 Cerrar Sesión en Todos los Dispositivos</button>
        <button class="btn-danger btn-destructive">🗑️ Eliminar Cuenta</button>
      </section>

      <section class="config-card">
        <h3 class="card-title">ℹ️ Información del Sistema</h3>
        <div class="info-item">
          <span>Versión:</span>
          <strong>2.1.0</strong>
        </div>
        <div class="info-item">
          <span>Última Actualización:</span>
          <strong>2026-04-15</strong>
        </div>
        <div class="info-item">
          <span>Almacenamiento:</span>
          <strong>4.2 GB de 10 GB</strong>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const userProfile = ref({
  name: 'Admin User',
  email: 'admin@kynsoft.com',
  phone: '+593 98765432'
})
</script>

<style scoped>
.view-wrapper {
  padding: 2rem 1.5rem;
  max-width: 1400px;
  margin: 0 auto;
}

.config-header {
  margin-bottom: 2rem;
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

.config-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 1.5rem;
}

.config-card {
  background: #ffffff;
  border-radius: 14px;
  padding: 1.5rem;
  box-shadow: 0 1px 3px rgba(26, 39, 53, 0.04);
}

.config-card.config-danger {
  border: 1px solid rgba(184, 69, 69, 0.2);
}

.card-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #1a2735;
  margin: 0 0 1.5rem 0;
  border-bottom: 2px solid #f5f1ec;
  padding-bottom: 1rem;
}

.config-item {
  margin-bottom: 1rem;
}

.config-item label {
  display: block;
  font-size: 0.85rem;
  font-weight: 600;
  color: #4b5563;
  margin-bottom: 0.5rem;
}

.config-input {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid #e8d8cb;
  border-radius: 8px;
  font-size: 0.9rem;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  transition: border-color 0.2s;
}

.config-input:focus {
  outline: none;
  border-color: #b87333;
  background: #f5f1ec;
}

.config-toggle {
  margin-bottom: 1rem;
}

.config-toggle label {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  font-size: 0.9rem;
  cursor: pointer;
  color: #4b5563;
}

.config-toggle input[type="checkbox"] {
  width: 18px;
  height: 18px;
  cursor: pointer;
}

.btn-save {
  width: 100%;
  padding: 0.75rem 1.25rem;
  background: #2d7a4f;
  color: #ffffff;
  border: none;
  border-radius: 8px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
}

.btn-save:hover {
  background: #245a3a;
}

.btn-danger {
  width: 100%;
  padding: 0.75rem 1.25rem;
  background: #b84545;
  color: #ffffff;
  border: none;
  border-radius: 8px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  margin-bottom: 0.75rem;
  transition: all 0.2s;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
}

.btn-danger:hover {
  background: #9d3939;
}

.btn-danger.btn-destructive {
  background: #dc3545;
}

.btn-danger.btn-destructive:hover {
  background: #c82333;
}

.danger-text {
  font-size: 0.85rem;
  color: #b84545;
  margin-bottom: 1rem;
}

.info-item {
  display: flex;
  justify-content: space-between;
  padding: 0.75rem 0;
  border-bottom: 1px solid #f5f1ec;
  font-size: 0.9rem;
}

.info-item:last-child {
  border-bottom: none;
}

.info-item span {
  color: #9ca3af;
}

.info-item strong {
  color: #1a2735;
}

@media (max-width: 900px) {
  .config-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 640px) {
  .view-wrapper { padding: 1rem; }
  .title { font-size: 1.5rem; }
  .config-card { padding: 1rem; }
}
</style>
