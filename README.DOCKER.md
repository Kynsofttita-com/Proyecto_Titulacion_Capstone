# 🚀 Guía de Ejecución con Docker

## 📋 Requisitos Previos

- Docker instalado (versión 20.10+)
- Docker Compose instalado (versión 1.29+)
- 8GB RAM mínimo disponible
- Puertos 80, 3000, 5173, 8080, 8081, 8888, 8761, 5432, 5672 libres

## 🏗️ Estructura del Sistema

```
┌─────────────────────────────────────────┐
│         NGINX (Reverse Proxy)           │
│  Puerto 80 (prod) y 3000 (dev proxy)    │
└──────────────────┬──────────────────────┘
                   │
        ┌──────────┴──────────┐
        │                     │
    ┌───▼────────┐    ┌──────▼───────┐
    │  Frontend  │    │ API Gateway   │
    │  Vite Dev  │    │  (8080)       │
    │  (5173)    │    └──────┬────────┘
    └────────────┘           │
                    ┌────────┴────────┐
                    │                 │
              ┌─────▼──────┐   ┌──────▼────┐
              │  MS-Auth   │   │ Config    │
              │  (8081)    │   │ Server    │
              │            │   │ (8888)    │
              └─────┬──────┘   └──────┬────┘
                    │                 │
                    └────────┬────────┘
                             │
                    ┌────────┴────────┐
                    │                 │
              ┌─────▼──────┐   ┌──────▼────┐
              │ PostgreSQL │   │ RabbitMQ  │
              │  (5432)    │   │  (5672)   │
              └────────────┘   └───────────┘
```

## ⚙️ Configuración Previa

### 1. Compilar todos los servicios backend

```bash
# MS-Auth
cd backend/ms-auth
mvn clean package -DskipTests
cd ../..

# API Gateway
cd backend/api-gateway
mvn clean package -DskipTests
cd ../..

# Config Server
cd backend/config-server
mvn clean package -DskipTests
cd ../..

# Eureka Server
cd backend/eureka-server
mvn clean package -DskipTests
cd ../..
```

### 2. Compilar el frontend

```bash
cd driving-school-app
npm install
npm run build
cd ..
```

## 🚀 OPCIÓN 1: Ejecución en Desarrollo (Con Hot Reload)

### Paso 1: Levanta los servicios

```bash
# Desde la raíz del proyecto
docker-compose up -d
```

Esto levanta:
- ✅ PostgreSQL (5432)
- ✅ RabbitMQ (5672)
- ✅ Eureka Server (8761)
- ✅ Config Server (8888)
- ✅ MS-Auth (8081)
- ✅ API Gateway (8080)
- ✅ Frontend Vite Dev (5173)
- ✅ Nginx (80, 3000)

### Paso 2: Espera a que todo inicie

```bash
# Ver logs
docker-compose logs -f

# Cuando veas esto, todo está listo:
# frontend_1  | ➜  Local:   http://localhost:5173/
# api-gateway | Started ApiGatewayApplication
# ms-auth     | Started MsAuthApplication
```

### Paso 3: Accede a la aplicación

**Frontend en desarrollo (Con hot reload):**
```
http://localhost:5173
```

**O a través del proxy Nginx:**
```
http://localhost:3000
```

### Paso 4: Haz login

```
Email:    admin@kynsoft.com
Password: Admin2026!
```

### Paso 5: Edita el código y ¡Se actualiza automáticamente!

Los cambios en estos archivos se reflejan al instante:

```
driving-school-app/src/views/LoginView.vue     ← Cambia y se ve al instante
driving-school-app/src/stores/auth.js          ← Cambios en estado
driving-school-app/src/layouts/MainLayout.vue  ← Cambios en layout
driving-school-app/src/components/**/*.vue     ← Todos los componentes
```

## 🔧 OPCIÓN 2: Ejecución en Producción (sin Hot Reload)

### Paso 1: Levanta los servicios (sin frontend dev)

```bash
# Solo los servicios backend + nginx sirviendo dist compilado
docker-compose up -d eureka-server config-server ms-auth api-gateway postgres rabbitmq nginx
```

### Paso 2: Accede a la aplicación

```
http://localhost
```

La app sirve desde `driving-school-app/dist/` compilado.

## 📊 Monitoreo y Debugging

### Ver logs de todos los servicios

```bash
docker-compose logs -f
```

### Ver logs de un servicio específico

```bash
docker-compose logs -f ms-auth
docker-compose logs -f api-gateway
docker-compose logs -f frontend
```

### Acceder a bases de datos

**PostgreSQL:**
```bash
docker-compose exec postgres psql -U dsadmin -d db_auth
```

**RabbitMQ Dashboard:**
```
http://localhost:15672
Usuario: dsrabbit
Password: dsrabbit2026
```

**Eureka Dashboard:**
```
http://localhost:8761
```

## 🧪 Pruebas con Docker

### Test de Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@kynsoft.com","password":"Admin2026!"}'
```

### Test de API Gateway

```bash
# Ver si el gateway está corriendo
curl http://localhost:8080/actuator/health

# Ver servicios disponibles en Eureka
curl http://localhost:8761/actuator/service-registry
```

## 🛑 Detener los servicios

```bash
# Detener todos
docker-compose down

# Detener y eliminar volúmenes (resetea DB)
docker-compose down -v

# Detener un servicio específico
docker-compose stop ms-auth
docker-compose start ms-auth
```

## 🔄 Actualizar cambios en código

### Si cambias el frontend (Vue.js)

```bash
# Hot reload automático - ¡No necesitas hacer nada!
# Los cambios aparecen al guardar el archivo
```

### Si cambias el backend (Java)

```bash
# Necesitas recompilar
cd backend/ms-auth
mvn clean package -DskipTests
cd ../..

# Luego rebuild la imagen
docker-compose up -d --build ms-auth
```

### Si cambias las dependencias (package.json)

```bash
# Necesitas reconstruir la imagen
docker-compose up -d --build frontend
```

## 🐛 Troubleshooting

### Error: "Port already in use"

```bash
# Ver qué servicio está usando el puerto
docker ps -a

# Usar puertos diferentes en docker-compose.yml
# O liberar el puerto
```

### Error: "Cannot connect to localhost:5173"

```bash
# El frontend aún está compilando, espera 30 segundos
# O verifica los logs
docker-compose logs frontend
```

### Error: "JWT Token Invalid"

```bash
# Asegúrate que JWT_SECRET en .env sea idéntico en todos lados
# Reinicia los servicios
docker-compose restart ms-auth api-gateway
```

### Error: "Service not found (Eureka)"

```bash
# Los servicios tardan 30-60 segundos en registrarse en Eureka
# Espera y recarga
# Verifica en http://localhost:8761
```

### Base de datos vacía

```bash
# Flyway debería ejecutar las migraciones automáticamente
# Si no, verifica los logs
docker-compose logs ms-auth | grep -i flyway

# O resetea la BD
docker-compose down -v
docker-compose up -d
```

## 📝 Variables de Entorno

Ver el archivo `.env` para cambiar puertos, credenciales, etc.

### Cambios comunes:

```env
# Cambiar puerto del frontend
FRONTEND_PORT=5174  # en lugar de 5173

# Cambiar credenciales DB
POSTGRES_PASSWORD=nueva_password

# Cambiar JWT secret
JWT_SECRET=tu-nuevo-secret-mas-largo-que-256-bits
```

## ✅ Checklist Final

- [ ] Docker instalado y corriendo
- [ ] Backend compilado (`mvn clean package -DskipTests`)
- [ ] Frontend compilado (`npm run build`)
- [ ] docker-compose.yml en la raíz del proyecto
- [ ] nginx.conf en la raíz del proyecto
- [ ] Dockerfile.dev en driving-school-app/
- [ ] .env configurado
- [ ] Puertos 80, 3000, 5173, 8080, 8081, 8888, 8761, 5432, 5672 disponibles
- [ ] `docker-compose up -d` ejecutado
- [ ] Acceso a http://localhost:5173
- [ ] Login exitoso

---

**¡Listo! 🎉 Ahora tienes todo corriendo en Docker con hot reload automático.**
