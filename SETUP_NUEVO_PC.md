# 🚀 Guía Completa: Setup del Proyecto en Nueva Máquina

## 📋 PASO 0: Requisitos Previos

Instala estos programas **antes** de empezar:

### Windows:
1. **Git** → https://git-scm.com/download/win
2. **Docker Desktop** → https://www.docker.com/products/docker-desktop
3. **Node.js 20+** → https://nodejs.org/
4. **Maven 3.8+** → https://maven.apache.org/download.cgi (o usa `choco install maven` si tienes Chocolatey)
5. **Java 21** → https://www.oracle.com/java/technologies/downloads/#java21 (o viene con Maven)

**Verifica las instalaciones:**
```bash
git --version
docker --version
node --version
npm --version
mvn --version
java -version
```

---

## 📥 PASO 1: Clonar el Repositorio

```bash
# Copia la URL de tu repositorio Git
git clone https://github.com/tu-usuario/Proyecto_Titulacion_Capstone.git

# Entra al directorio
cd Proyecto_Titulacion_Capstone

# Verifica que estés en la rama correcta
git branch -a
git checkout main  # o la rama que uses
```

---

## 🏗️ PASO 2: Compilar Todos los Servicios Backend

**Esto puede tomar 5-10 minutos la primera vez (descarga de dependencias)**

### MS-Auth
```bash
cd backend/ms-auth
mvn clean package -DskipTests
cd ../..
```

### MS-Estudiantes (opcional, si existe)
```bash
cd backend/ms-estudiantes
mvn clean package -DskipTests
cd ../..
```

### MS-Instructores (opcional, si existe)
```bash
cd backend/ms-instructores
mvn clean package -DskipTests
cd ../..
```

### MS-Vehículos (opcional, si existe)
```bash
cd backend/ms-vehiculos
mvn clean package -DskipTests
cd ../..
```

### MS-Asignaciones (opcional, si existe)
```bash
cd backend/ms-asignaciones
mvn clean package -DskipTests
cd ../..
```

### Config Server
```bash
cd backend/config-server
mvn clean package -DskipTests
cd ../..
```

### Eureka Server
```bash
cd backend/eureka-server
mvn clean package -DskipTests
cd ../..
```

### API Gateway
```bash
cd backend/api-gateway
mvn clean package -DskipTests
cd ../..
```

**⚡ Atajo (ejecutar todo junto):**
```bash
for dir in backend/*/; do
  echo "Compilando $dir..."
  (cd "$dir" && mvn clean package -DskipTests -q)
done
```

---

## 🎨 PASO 3: Compilar el Frontend

```bash
cd driving-school-app
npm install
npm run build
cd ..
```

**Verifica que se creó la carpeta `dist`:**
```bash
ls -la driving-school-app/dist/
```

---

## 🐳 PASO 4: Levantar Docker Compose

### 4a. Limpiar contenedores viejos (si los hay)
```bash
docker-compose down -v
```

### 4b. Construir imágenes y levantar servicios
```bash
docker-compose up -d
```

**Espera 30-60 segundos para que todos los servicios se inicialicen.**

---

## ✅ PASO 5: Verificar que Todo Funciona

### 5a. Ver estado de contenedores
```bash
docker ps
```

**Deberías ver 8 contenedores:**
- ds-postgres ✓
- ds-rabbitmq ✓
- ds-eureka ✓
- ds-config ✓
- ds-ms-auth ✓
- ds-api-gateway ✓
- ds-frontend ✓
- ds-nginx ✓

### 5b. Ver logs (si algo falla)
```bash
# Todos los logs
docker-compose logs -f

# Logs de un servicio específico
docker-compose logs -f ms-auth
docker-compose logs -f api-gateway
docker-compose logs -f frontend
```

### 5c. Probar endpoints clave

**Eureka:**
```bash
curl http://localhost:8761/actuator/health
# Respuesta: {"status":"UP"}
```

**API Gateway:**
```bash
curl http://localhost:8080/actuator/health
# Respuesta: {"status":"UP",...}
```

**MS-Auth:**
```bash
curl http://localhost:8081/actuator/health
# Respuesta: {"status":"UP"}
```

**Frontend:**
```bash
curl http://localhost
# Devuelve HTML de la página
```

### 5d. Prueba el Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@kynsoft.com","password":"Admin2026!"}'

# Respuesta esperada: Token JWT + datos de usuario
```

---

## 🌐 PASO 6: Acceder a la Aplicación

**En tu navegador, abre:**
```
http://localhost/login
```

**Credenciales:**
- Email: `admin@kynsoft.com`
- Contraseña: `Admin2026!`

---

## 📊 Dashboards y Herramientas

| Herramienta | URL | Usuario | Contraseña |
|-------------|-----|---------|-----------|
| **Aplicación** | http://localhost | admin@kynsoft.com | Admin2026! |
| **RabbitMQ** | http://localhost:15672 | dsrabbit | dsrabbit2026 |
| **Eureka** | http://localhost:8761 | - | - |
| **Adminer (BD)** | http://localhost:8888 | - | - |

---

## 🔄 Desarrollar con Hot Reload

### Opción 1: Frontend (Vue.js) - Hot Reload Automático
```bash
# En una terminal, en el directorio raíz del proyecto
docker-compose up -d

# Edita archivos en driving-school-app/src/
# Los cambios se reflejan automáticamente en http://localhost:3000
```

### Opción 2: Backend (Java) - Necesita Recompilación
```bash
# Haz cambios en el código
# Por ejemplo: backend/ms-auth/src/main/java/...

# Recompila
cd backend/ms-auth
mvn clean package -DskipTests
cd ../..

# Reconstruye y reinicia el contenedor
docker-compose up -d --build ms-auth

# Los cambios están en http://localhost:8080/api/auth/...
```

---

## 🛑 Comandos Útiles

### Detener todo
```bash
docker-compose down
```

### Detener y eliminar volúmenes (resetea BD)
```bash
docker-compose down -v
```

### Reiniciar un servicio
```bash
docker-compose restart ms-auth
```

### Ver logs en tiempo real
```bash
docker-compose logs -f ms-auth
```

### Limpiar espacios en Docker
```bash
docker system prune -a
```

---

## 🆘 Troubleshooting

### Error: "Port already in use"
```bash
# Ver qué está usando el puerto
netstat -ano | findstr :8080  # Windows
lsof -i :8080                  # Mac/Linux

# Liberar el puerto o cambiar en docker-compose.yml
```

### Error: "Cannot connect to Docker daemon"
```bash
# Abre Docker Desktop
# O reinicia el servicio de Docker
```

### Error: "Database does not exist"
```bash
# El contenedor postgres necesita tiempo para iniciar
# Espera 30 segundos y reintenta
docker-compose logs postgres
```

### Error: "Service not found (Eureka)"
```bash
# Los servicios tardan 30-60 segundos en registrarse
# Espera y verifica en http://localhost:8761
```

### Login devuelve "Invalid token"
```bash
# Verifica que JWT_SECRET en .env sea igual en todos lados
# Reinicia los servicios:
docker-compose restart ms-auth api-gateway
```

---

## 📝 Estructura del Proyecto

```
Proyecto_Titulacion_Capstone/
├── backend/
│   ├── eureka-server/          # Servicio de descubrimiento
│   ├── config-server/          # Configuración centralizada
│   ├── api-gateway/            # Puerta de entrada (8080)
│   ├── ms-auth/                # Autenticación (8081)
│   ├── ms-estudiantes/         # Estudiantes (8082)
│   ├── ms-instructores/        # Instructores (8083)
│   ├── ms-vehiculos/           # Vehículos (8084)
│   ├── ms-asignaciones/        # Asignaciones (8085)
│   ├── ms-cobros/              # Cobros (8086)
│   ├── ms-reportes/            # Reportes (8087)
│   └── ms-notificaciones/      # Notificaciones (8088)
├── driving-school-app/         # Frontend Vue.js
│   ├── src/
│   ├── public/
│   └── dist/                   # Compilado (producción)
├── docker-compose.yml          # Orquestación Docker
├── .env                        # Variables de entorno
├── nginx.conf                  # Configuración Nginx
└── README.md                   # Este archivo

```

---

## ⚡ Resumen Rápido (TL;DR)

```bash
# Paso 1: Instalar requisitos
# Git, Docker, Node.js, Maven, Java 21

# Paso 2: Clonar
git clone <repo-url> && cd Proyecto_Titulacion_Capstone

# Paso 3: Compilar backend (5-10 min)
for dir in backend/*/; do (cd "$dir" && mvn clean package -DskipTests -q); done

# Paso 4: Compilar frontend
cd driving-school-app && npm install && npm run build && cd ..

# Paso 5: Levantar Docker
docker-compose up -d

# Paso 6: Esperar 30-60 segundos y acceder
# http://localhost/login
# admin@kynsoft.com / Admin2026!
```

---

## 📞 Necesitas Ayuda?

Si algo falla:
1. Revisa los logs: `docker-compose logs -f`
2. Verifica que los puertos estén libres
3. Asegúrate de tener Internet (para descargar dependencias)
4. Reinicia Docker Desktop
5. Ejecuta `docker system prune -a` para limpiar

---

**¡Listo! 🎉 Tu proyecto está en marcha.**
