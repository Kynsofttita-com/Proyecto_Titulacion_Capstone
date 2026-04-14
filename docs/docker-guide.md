# 🐳 Docker Guide - Driving School System

## Estructura de contenedores
infrastructure/docker/
├── docker-compose.yml      # Orquestación de todos los servicios
├── Dockerfile.template     # Template reutilizable para microservicios
├── .dockerignore           # Archivos a ignorar en el build
├── init-db.sql             # Script de inicialización de bases de datos
└── .env                    # Variables de entorno (NO commitear)

## 🚀 Cómo usar el Dockerfile.template

### Paso 1: Copiar el template al microservicio
```bash
copy infrastructure\docker\Dockerfile.template backend\ms-nombre\Dockerfile
```

### Paso 2: Editar el puerto en el Dockerfile
Cambia `EXPOSE ${PORT}` por el puerto del microservicio:

| Microservicio | Puerto |
|--------------|--------|
| ms-auth | 8081 |
| ms-estudiantes | 8082 |
| ms-instructores | 8083 |
| ms-vehiculos | 8084 |
| ms-asignaciones | 8085 |
| ms-cobros | 8086 |
| ms-reportes | 8087 |
| ms-notificaciones | 8088 |

### Paso 3: Agregar al docker-compose.yml
```yaml
ms-nombre:
  build:
    context: ../../backend/ms-nombre
    dockerfile: Dockerfile
  container_name: ds-ms-nombre
  restart: always
  ports:
    - "808X:808X"
  networks:
    - driving-school-net
  depends_on:
    - eureka-server
    - config-server
    - postgres
  environment:
    - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://ds-eureka:8761/eureka/
    - SPRING_CONFIG_IMPORT=optional:configserver:http://ds-config:8888
    - DB_HOST=ds-postgres
    - DB_USER=dsadmin
    - DB_PASS=dspass2026
```

### Paso 4: Build y levantar
```bash
cd infrastructure/docker
docker-compose up -d --build ms-nombre
```

---

## 📋 Comandos útiles

### Ver todos los contenedores
```bash
docker ps
```

### Ver logs de un contenedor
```bash
docker logs ds-nombre-contenedor
docker logs ds-nombre-contenedor --follow
```

### Reiniciar un contenedor
```bash
docker-compose restart ms-nombre
```

### Parar todos los contenedores
```bash
docker-compose down
```

### Parar y eliminar volúmenes
```bash
docker-compose down -v
```

### Rebuildar un servicio específico
```bash
docker-compose up -d --build ms-nombre
```

### Ver uso de recursos
```bash
docker stats
```

---

## 🔧 Troubleshooting común

### Error: Dockerfile not found
failed to read dockerfile: open Dockerfile: no such file or directory
**Solución:** El archivo se guardó como `Dockerfile.txt`
```bash
rename Dockerfile.txt Dockerfile
```

### Error: Port already in use
Bind for 0.0.0.0:8080 failed: port is already allocated
**Solución:** Otro proceso usa ese puerto
```bash
# Ver qué usa el puerto
netstat -ano | findstr :8080
# Cambiar el puerto en docker-compose.yml
```

### Error: No spring.config.import
No spring.config.import property has been defined
**Solución:** Agregar en application.yml del microservicio:
```yaml
spring:
  config:
    import: "optional:configserver:http://ds-config:8888"
```

### Error: Connection refused a Eureka
Cannot execute request on any known server
**Solución:** Verificar que eureka-server está corriendo
```bash
docker ps | grep eureka
docker logs ds-eureka
```

### Error: BUILD FAILURE - main class not found
Unable to find a single main class
**Solución:** Hay clase duplicada, eliminar carpeta extra
```bash
# Verificar carpetas en src/main/java
dir backend\ms-nombre\src\main\java\
# Eliminar carpeta duplicada
rmdir /s /q backend\ms-nombre\src\main\java\nombre_duplicado
```

---

## 🌐 URLs de servicios

| Servicio | URL | Credenciales |
|---------|-----|-------------|
| Eureka Dashboard | http://localhost:8761 | - |
| Config Server | http://localhost:8888 | - |
| API Gateway | http://localhost:8080 | - |
| RabbitMQ Dashboard | http://localhost:15672 | dsrabbit/dsrabbit2026 |
| PostgreSQL | localhost:5432 | dsadmin/dspass2026 |

---

## 📦 Puertos del sistema