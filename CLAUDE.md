# DriveSchool System - Guía Maestra para Claude Code
## Kynsoft | Universidad de Las Américas | Titulación 2026

---

## 🎯 MISIÓN DEL PROYECTO
Sistema integral de gestión para escuelas de conducción en Ecuador.
Arquitectura de microservicios con Java 21 + Spring Boot 3.5 + Vue.js 3.

## 👥 EQUIPO
- **Hernán Mateo Jurado Moran** → Full Stack + DevOps + Arquitectura
- **Raúl Sebastián Cruz Baño** → Backend + Base de Datos

## 📁 REPOSITORIO
```
https://github.com/Kynsofttita-com/Proyecto_Titulacion_Capstone.git
Rama principal: main (protegida, requiere PR)
Rama desarrollo: develop
Rama actual: feature/ms-auth
Ramas futuras: feature/(Microservicio)
```

---

## 🏗️ ARQUITECTURA GENERAL

```
Frontend (Vue.js 3)          → Puerto 5173 / 3000
API Gateway (Spring Cloud)   → Puerto 8080
MS-Auth                      → Puerto 8081 → db_auth
MS-Estudiantes               → Puerto 8082 → db_estudiantes
MS-Instructores              → Puerto 8083 → db_instructores
MS-Vehículos                 → Puerto 8084 → db_vehiculos
MS-Asignaciones              → Puerto 8085 → db_asignaciones
MS-Cobros                    → Puerto 8086 → db_cobros
MS-Reportes                  → Puerto 8087 → db_reportes
MS-Notificaciones            → Puerto 8088 → db_notificaciones
Eureka Server                → Puerto 8761
Config Server                → Puerto 8888
PostgreSQL 16                → Puerto 5432
RabbitMQ 3.13                → Puerto 5672 / Panel: 15672
```

---

## 🛠️ STACK TECNOLÓGICO

### Backend
- Java 21 + Spring Boot 3.5.13
- Spring Cloud (Eureka, Gateway, Config)
- Spring Security + JWT (jjwt 0.12.5)
- Spring Data JPA + Hibernate
- Flyway (migraciones DB)
- RabbitMQ (Spring AMQP)
- Swagger/OpenAPI (springdoc 2.3.0)
- Lombok + MapStruct
- JUnit 5 + Mockito (tests)

### Frontend
- Vue.js 3 + Vite
- Pinia (estado global)
- Vue Router 4
- Axios (HTTP client)
- Tailwind CSS 3

### Infraestructura
- Docker + Docker Compose
- GitHub Actions (CI/CD)
- PostgreSQL 16
- RabbitMQ 3.13

---

## 📋 ESTADO DE SPRINTS

| Sprint | Módulo | Estado | Fechas |
|--------|--------|--------|--------|
| Sprint 1 | Infraestructura Base | ✅ COMPLETADO | 08/04 - 14/04/2026 |
| Sprint 2 | MS-Auth + Login UI | 🔄 EN CURSO | 15/04 - 21/04/2026 |
| Sprint 3 | MS-Estudiantes Backend | ⏳ PENDIENTE | 22/04 - 28/04/2026 |
| Sprint 4 | MS-Estudiantes FE + Instructores BE | ⏳ PENDIENTE | 29/04 - 05/05/2026 |
| Sprint 5 | Instructores FE + Vehículos BE | ⏳ PENDIENTE | 06/05 - 12/05/2026 |
| Sprint 6 | Vehículos FE + Asignaciones BE | ⏳ PENDIENTE | 13/05 - 19/05/2026 |
| Sprint 7 | Asignaciones FE + Cobros BE | ⏳ PENDIENTE | 20/05 - 26/05/2026 |
| Sprint 8 | Cobros FE + Notificaciones | ⏳ PENDIENTE | 27/05 - 02/06/2026 |
| Sprint 9 | MS-Reportes | ⏳ PENDIENTE | 03/06 - 09/06/2026 |
| Sprint 10 | Integración General | ⏳ PENDIENTE | 10/06 - 16/06/2026 |
| Sprint 11 | Pruebas y QA | ⏳ PENDIENTE | 17/06 - 23/06/2026 |
| Sprint 12 | Documentación y Cierre | ⏳ PENDIENTE | 24/06 - 30/06/2026 |

---

## 🔄 SPRINT 2 - PENDIENTES EXACTOS

### Backend (completado):
- ✅ Flyway V1 migración
- ✅ Entidades JPA (User, Role, AuditLog, PasswordResetToken)
- ✅ Repositorios JPA
- ✅ JwtTokenProvider, JwtAuthenticationFilter, SecurityConfig
- ✅ DTOs, Excepciones, GlobalExceptionHandler
- ✅ AuthService + AuthServiceImpl
- ✅ AuthController

### Backend (pendiente):
- ❌ UserService + UserController (CRUD, solo ADMIN)
- ❌ AuditController
- ❌ Pruebas unitarias (mínimo 15 tests)
- ❌ Swagger/OpenAPI configurado
- ❌ Filtro JWT en API Gateway

### Frontend (pendiente):
- ❌ Proyecto Vue.js 3 con Vite
- ❌ Estructura de carpetas src/
- ❌ Servicio API (Axios)
- ❌ Auth store (Pinia)
- ❌ Vue Router con guards
- ❌ LoginView
- ❌ MainLayout + Sidebar
- ❌ DashboardView
- ❌ Dockerfile + nginx.conf
- ❌ Agregar al docker-compose

---

## 🎨 DISEÑO FRONTEND

### Paleta de colores (estilo Lovable/premium):
```css
--bg-primary: #0d1b26
--bg-secondary: #111e2a
--bg-tertiary: #1a2e3f
--border: #1e3a50
--text-primary: #e2eaf4
--text-secondary: #94b3c8
--text-muted: #4a6a82
--accent: #f59e0b
--accent-2: #ea580c
--cyan: #06b6d4
--green: #10b981
--purple: #8b5cf6
--red: #ef4444
```

### Tipografías:
```
Display/Títulos: Syne (700, 800)
Cuerpo/UI: DM Sans (400, 500, 600)
```

### Componentes base:
- Cards con gradiente sutil y border-radius 14px
- Sidebar colapsable con íconos
- Inputs con focus glow en accent color
- Botones con gradiente accent → accent-2
- Badges de estado con colores semánticos
- Tablas con hover suave
- Toasts de notificación animados

---

## 📏 CONVENCIONES DE CÓDIGO

### Git commits:
```
feat(ms-auth): descripción
fix(ms-estudiantes): descripción
chore: descripción
docs: descripción
ci: descripción
infra: descripción
test(ms-auth): descripción
```

### Estructura de paquetes Java:
```
com.kynsoft.{servicio}/
  config/          → Spring configs
  controller/      → REST controllers
  dto/
    request/       → DTOs entrada
    response/      → DTOs salida
  entity/          → JPA entities
  enums/           → Enumeraciones
  exception/       → Custom exceptions
  repository/      → JPA repositories
  security/jwt/    → JWT classes
  service/         → Interfaces
  service/impl/    → Implementaciones
  event/           → RabbitMQ events
```

### Reglas importantes:
1. Cada MS tiene su propia BD, NUNCA compartir BD entre MS
2. Comunicación síncrona: REST a través del API Gateway
3. Comunicación asíncrona: RabbitMQ para eventos
4. JWT obligatorio en todos los endpoints excepto /api/auth/**
5. BCrypt strength=12 para passwords
6. Flyway para TODAS las migraciones de BD
7. Tests obligatorios: mínimo 80% cobertura por MS
8. Swagger documentado en cada controller

---

## 🔐 CREDENCIALES DE DESARROLLO

```env
DB_HOST=localhost
DB_PORT=5432
DB_USER=dsadmin
DB_PASS=dspass2026
RABBITMQ_USER=dsrabbit
RABBITMQ_PASS=dsrabbit2026
JWT_SECRET=kynsoft-driving-school-super-secret-key-2026-udla-titulacion
JWT_EXPIRATION=86400000
ADMIN_EMAIL=admin@kynsoft.com
ADMIN_PASS=Admin2026!
FRONTEND_URL=http://localhost:5173
```

---

## 🚀 COMANDOS ÚTILES

### Levantar infraestructura:
```bash
cd infrastructure/docker
docker-compose up -d postgres rabbitmq eureka-server config-server api-gateway
```

### Levantar MS-Auth:
```bash
cd backend/ms-auth
mvn spring-boot:run
```

### Frontend dev:
```bash
cd frontend/driving-school-app
npm run dev
```

### Verificar servicios:
```bash
# Eureka Dashboard
http://localhost:8761

# RabbitMQ Panel
http://localhost:15672 (dsrabbit/dsrabbit2026)

# Swagger MS-Auth
http://localhost:8081/swagger-ui.html

# API Gateway
http://localhost:8080
```

### Git workflow:
```bash
git checkout develop
git pull origin develop
git checkout feature/nombre-feature
# ... desarrollar ...
git add .
git commit -m "feat(ms): descripción"
git push origin feature/nombre-feature
# Crear PR en GitHub
```

---

## 🤖 INSTRUCCIONES PARA CLAUDE CODE

Cuando trabajes en este proyecto:

1. **SIEMPRE** verifica en qué sprint/tarea estamos antes de codificar
2. **SIEMPRE** usa las convenciones de paquetes definidas
3. **SIEMPRE** agrega Swagger @Operation en cada endpoint nuevo
4. **SIEMPRE** escribe tests para cada servicio nuevo
5. **SIEMPRE** usa los colores y tipografía definida en el frontend
6. **NUNCA** modifiques la BD de otro microservicio directamente
7. **NUNCA** hagas commit directo a main o develop
8. Cuando completes una tarea, actualiza el estado en este archivo
9. Prioriza calidad sobre velocidad
10. El diseño del frontend debe verse como Lovable/Linear/Vercel