# 🚗 Driving School System

Sistema integral de control administrativo y financiero para escuelas de conducción en Ecuador.

Desarrollado por:
- Raúl Sebastián Cruz Baño
- Hernán Mateo Jurado Moran
- Víctor Javier Gómez Regalado

Universidad de Las Américas - 2026

---

## 📋 Prerrequisitos

Antes de comenzar asegúrate de tener instalado:

| Herramienta | Versión | Descarga |
|-------------|---------|----------|
| Docker Desktop | 4.x+ | https://www.docker.com/products/docker-desktop |
| Java (Eclipse Temurin) | 21+ | https://adoptium.net/temurin/releases/?version=21 |
| Maven | 3.9+ | https://maven.apache.org/download.cgi |
| Node.js | 18+ | https://nodejs.org/en/download |
| Git | 2.x+ | https://git-scm.com/downloads |

### Verificar instalaciones
```bash
docker --version
java --version
mvn --version
node --version
git --version
```

---

## 🚀 Instalación y Setup

### Paso 1: Clonar el repositorio
```bash
git clone https://github.com/Kynsofttita-com/Proyecto_Titulacion_Capstone.git
cd Proyecto_Titulacion_Capstone
```

### Paso 2: Configurar variables de entorno
```bash
# Copiar archivo de ejemplo
cp infrastructure/docker/.env.example infrastructure/docker/.env

# Windows
copy infrastructure\docker\.env.example infrastructure\docker\.env
```

### Paso 3: Editar el archivo .env
Abre `infrastructure/docker/.env` y configura los valores:
```env
DB_HOST=ds-postgres
DB_PORT=5432
DB_USER=dsadmin
DB_PASS=dspass2026
JWT_SECRET=driving-school-jwt-secret-key-min-256-bits-2026
RABBITMQ_USER=dsrabbit
RABBITMQ_PASS=dsrabbit2026
```

### Paso 4: Levantar los servicios con Docker
```bash
cd infrastructure/docker

# Windows
cd infrastructure\docker

docker-compose up -d
```

### Paso 5: Verificar que todos los servicios están corriendo
```bash
docker ps
```

Debes ver estos contenedores con status `Up`:
ds-postgres   → PostgreSQL
ds-rabbitmq   → RabbitMQ
ds-eureka     → Eureka Server
ds-config     → Config Server
ds-gateway    → API Gateway

### Paso 6: Verificar en el navegador

| Servicio | URL | Resultado esperado |
|---------|-----|--------------------|
| Eureka Dashboard | http://localhost:8761 | Dashboard de Eureka |
| Config Server | http://localhost:8888/ms-auth/default | JSON con config |
| API Gateway | http://localhost:8080/actuator/health | {"status":"UP"} |
| RabbitMQ | http://localhost:15672 | Panel de RabbitMQ |

---

## 🗂️ Estructura del proyecto

Proyecto_Titulacion_Capstone/
├── backend/
│   ├── eureka-server/       # Service Discovery (Puerto: 8761)
│   ├── config-server/       # Configuración centralizada (Puerto: 8888)
│   ├── api-gateway/         # Punto de entrada único (Puerto: 8080)
│   ├── ms-auth/             # Autenticación y autorización (Puerto: 8081)
│   ├── ms-estudiantes/      # Administración de estudiantes (Puerto: 8082)
│   ├── ms-instructores/     # Administración de instructores (Puerto: 8083)
│   ├── ms-vehiculos/        # Control de vehículos (Puerto: 8084)
│   ├── ms-asignaciones/     # Asignaciones y programación (Puerto: 8085)
│   ├── ms-cobros/           # Cobros y finanzas (Puerto: 8086)
│   ├── ms-reportes/         # Reportes y analítica (Puerto: 8087)
│   └── ms-notificaciones/   # Notificaciones por email (Puerto: 8088)
├── frontend/
│   └── driving-school-app/  # Vue.js 3 + Vite (Puerto: 5173)
├── infrastructure/
│   ├── docker/              # Docker Compose y configuraciones
│   └── config-repo/         # Configuraciones por microservicio
├── docs/                    # Documentación del proyecto
└── scripts/                 # Scripts de automatización

---

## 🌐 Puertos del sistema

| Servicio | Puerto | URL |
|---------|--------|-----|
| API Gateway | 8080 | http://localhost:8080 |
| MS-Auth | 8081 | http://localhost:8081 |
| MS-Estudiantes | 8082 | http://localhost:8082 |
| MS-Instructores | 8083 | http://localhost:8083 |
| MS-Vehículos | 8084 | http://localhost:8084 |
| MS-Asignaciones | 8085 | http://localhost:8085 |
| MS-Cobros | 8086 | http://localhost:8086 |
| MS-Reportes | 8087 | http://localhost:8087 |
| MS-Notificaciones | 8088 | http://localhost:8088 |
| Eureka Server | 8761 | http://localhost:8761 |
| Config Server | 8888 | http://localhost:8888 |
| PostgreSQL | 5432 | localhost:5432 |
| RabbitMQ AMQP | 5672 | - |
| RabbitMQ Dashboard | 15672 | http://localhost:15672 |
| Frontend Vue.js | 5173 | http://localhost:5173 |

---

## 🛠️ Tecnologías

| Capa | Tecnología |
|------|-----------|
| Backend | Java 21 + Spring Boot 3.5 |
| Frontend | Vue.js 3 + Vite |
| Base de datos | PostgreSQL 16 |
| Mensajería | RabbitMQ 3.13 |
| Contenedores | Docker + Docker Compose |
| Service Discovery | Spring Cloud Eureka |
| Config Server | Spring Cloud Config |
| API Gateway | Spring Cloud Gateway |
| Seguridad | JWT + Spring Security |
| CI/CD | GitHub Actions |

---

## 📋 Comandos útiles

### Ver logs de un servicio
```bash
# Logs en tiempo real
docker-compose logs -f eureka-server
docker-compose logs -f config-server
docker-compose logs -f api-gateway

# Todos los logs
docker-compose logs -f
```

### Reiniciar servicios
```bash
# Reiniciar un servicio específico
docker-compose restart eureka-server

# Reiniciar todos
docker-compose restart
```

### Parar servicios
```bash
# Parar sin eliminar datos
docker-compose down

# Parar y eliminar todos los datos (¡cuidado!)
docker-compose down -v
```

### Rebuildar un servicio
```bash
docker-compose up -d --build nombre-servicio
```

### Acceder a PostgreSQL
```bash
docker exec -it ds-postgres psql -U dsadmin

# Listar bases de datos
\l

# Conectar a una base de datos
\c db_auth

# Salir
\q
```

### Acceder a RabbitMQ

URL: http://localhost:15672
Usuario: dsrabbit
Password: dsrabbit2026

### Ver estado de todos los contenedores
```bash
docker ps
```

### Ver uso de recursos
```bash
docker stats
```

---

## 🔧 Solución de problemas comunes

### Docker no inicia
```bash
# Verificar que Docker Desktop está corriendo
docker ps
# Si falla, abrir Docker Desktop manualmente
```

### Puerto ocupado
```bash
# Windows - ver qué usa el puerto
netstat -ano | findstr :8080
```

### Eureka no registra servicios
```bash
# Verificar logs de eureka
docker logs ds-eureka
# Reiniciar eureka
docker-compose restart eureka-server
```

### Config Server no responde
```bash
docker logs ds-config
docker-compose restart config-server
```

### Base de datos no conecta
```bash
# Verificar que postgres está corriendo
docker ps | grep postgres
# Ver logs
docker logs ds-postgres
```

---

## 🔄 Flujo de trabajo Git
main          ← Código en producción
↑
develop       ← Integración del equipo
↑
feature/xxx   ← Desarrollo individual

### Crear nueva rama de feature
```bash
git checkout develop
git pull origin develop
git checkout -b feature/nombre-funcionalidad
```

### Subir cambios
```bash
git add .
git commit -m "feat: descripción del cambio"
git push origin feature/nombre-funcionalidad
```

### Crear Pull Request
1. Ir al repositorio en GitHub
2. Clic en **Compare & pull request**
3. Base: `develop` ← Compare: `feature/xxx`
4. Mínimo 1 aprobación requerida

---

## 👥 Equipo de desarrollo

| Nombre | Rol | GitHub |
|--------|-----|--------|
| Raúl Sebastián Cruz Baño | Developer | @raul-cruz |
| Hernán Mateo Jurado Moran | Developer | @Hmateo205 |