# 🔑 Endpoints API - Driving School System

## 🌐 Base URL
```
http://localhost:8080/api
```

---

## 🔐 AUTENTICACIÓN

### 1️⃣ Login (Obtener JWT Token)
**POST** `/auth/login`

**Body:**
```json
{
  "email": "admin@kynsoft.com",
  "password": "Admin2026!"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "email": "admin@kynsoft.com",
    "roles": ["ADMIN"]
  },
  "message": "Login successful"
}
```

---

### 2️⃣ Forgot Password
**POST** `/auth/forgot-password`

**Body:**
```json
{
  "email": "admin@kynsoft.com"
}
```

**Response (200 OK):**
```json
{
  "message": "If an account exists with that email, a password reset link will be sent"
}
```

---

### 3️⃣ Reset Password
**POST** `/auth/reset-password`

**Body:**
```json
{
  "token": "reset_token_here",
  "newPassword": "NewPassword123!"
}
```

**Response (200 OK):**
```json
{
  "message": "Password has been reset successfully"
}
```

---

### 4️⃣ Logout
**POST** `/auth/logout?userId=550e8400-e29b-41d4-a716-446655440000`

**Response (200 OK):**
```json
{
  "message": "Logged out successfully"
}
```

---

### 5️⃣ Generate Hash (DEV ONLY)
**GET** `/auth/dev/generate-hash?password=Admin2026!`

**Response (200 OK):**
```json
{
  "password": "Admin2026!",
  "bcrypt_hash": "$2a$10$..."
}
```

---

## 👥 GESTIÓN DE USUARIOS

### 6️⃣ Listar Usuarios (Requiere autenticación)
**GET** `/users?page=0&size=10`

**Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

**Query Parameters (opcionales):**
- `page`: Número de página (default: 0)
- `size`: Cantidad por página (default: 10)
- `status`: ACTIVE, INACTIVE, SUSPENDED

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "email": "admin@kynsoft.com",
      "name": "Admin User",
      "status": "ACTIVE",
      "roles": ["ADMIN"]
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "currentPage": 0
}
```

---

### 7️⃣ Obtener Usuario por ID
**GET** `/users/{id}`

**Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

**Response (200 OK):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "admin@kynsoft.com",
  "name": "Admin User",
  "status": "ACTIVE",
  "roles": ["ADMIN"]
}
```

---

### 8️⃣ Crear Usuario
**POST** `/users`

**Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
Content-Type: application/json
```

**Body:**
```json
{
  "email": "newuser@kynsoft.com",
  "password": "Password123!",
  "name": "New User"
}
```

**Response (201 Created):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "email": "newuser@kynsoft.com",
  "name": "New User",
  "status": "ACTIVE",
  "roles": []
}
```

---

### 9️⃣ Actualizar Usuario
**PUT** `/users/{id}`

**Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
Content-Type: application/json
```

**Body:**
```json
{
  "email": "updated@kynsoft.com",
  "name": "Updated Name"
}
```

**Response (200 OK):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "email": "updated@kynsoft.com",
  "name": "Updated Name",
  "status": "ACTIVE",
  "roles": []
}
```

---

### 🔟 Cambiar Estado de Usuario
**PATCH** `/users/{id}/status`

**Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
Content-Type: application/json
```

**Body:**
```json
{
  "status": "SUSPENDED"
}
```

**Status Options:** `ACTIVE`, `INACTIVE`, `SUSPENDED`

**Response (204 No Content)**

---

### 1️⃣1️⃣ Asignar Rol a Usuario
**POST** `/users/{id}/roles`

**Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
Content-Type: application/json
```

**Body:**
```json
{
  "roleName": "INSTRUCTOR"
}
```

**Role Options:** `ADMIN`, `ADMINISTRATIVO`, `INSTRUCTOR`, `ESTUDIANTE`

**Response (204 No Content)**

---

## 📝 PRUEBAS RÁPIDAS EN POSTMAN

### Paso 1: Obtener Token
1. **Abrir Postman**
2. **Crear nueva Request:**
   - Método: `POST`
   - URL: `http://localhost:8080/api/auth/login`
   - Body (raw, JSON):
   ```json
   {
     "email": "admin@kynsoft.com",
     "password": "Admin2026!"
   }
   ```
3. **Copiar el token de la respuesta**

### Paso 2: Usar Token en Requests
1. **En cualquier request protegida:**
   - Ir a **Headers**
   - Agregar:
     - Key: `Authorization`
     - Value: `Bearer YOUR_TOKEN_HERE`

### Paso 3: Probar Endpoints
- `GET http://localhost:8080/api/users` (con Authorization header)
- `GET http://localhost:8080/api/users?page=0&size=5`

---

## 🔒 Headers Requeridos

### Para Endpoints Protegidos (requieren TOKEN):
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
Content-Type: application/json
```

### Sin Autenticación:
```
Content-Type: application/json
```

---

## 📊 Credenciales Demo

```
✅ Admin
Email: admin@kynsoft.com
Password: Admin2026!
Role: ADMIN

✅ Instructor
Email: instructor@kynsoft.com
Password: Inst2026!
Role: INSTRUCTOR

✅ Estudiante
Email: estudiante@kynsoft.com
Password: Est2026!
Role: ESTUDIANTE
```

---

## ⚠️ Códigos de Error

| Código | Significado |
|--------|-------------|
| 200 | OK - Solicitud exitosa |
| 201 | Created - Recurso creado |
| 204 | No Content - Operación exitosa sin respuesta |
| 400 | Bad Request - Error en el formato de la solicitud |
| 401 | Unauthorized - Token inválido o expirado |
| 403 | Forbidden - No tienes permisos |
| 404 | Not Found - Recurso no encontrado |
| 409 | Conflict - El recurso ya existe |
| 500 | Server Error - Error en el servidor |

---

## 🚀 Próximos Microservicios

Los siguientes endpoints estarán disponibles cuando los microservicios sean implementados:

```
POST /estudiantes         - Crear estudiante
GET  /estudiantes         - Listar estudiantes
GET  /estudiantes/{id}    - Obtener estudiante
PUT  /estudiantes/{id}    - Actualizar estudiante
DELETE /estudiantes/{id}  - Eliminar estudiante

POST /instructores        - Crear instructor
GET  /instructores        - Listar instructores
(y similares...)

POST /vehiculos           - Crear vehículo
GET  /vehiculos           - Listar vehículos
(y similares...)

POST /asignaciones        - Crear asignación
GET  /asignaciones        - Listar asignaciones
(y similares...)

POST /cobros              - Crear cobro
GET  /cobros              - Listar cobros
(y similares...)

POST /reportes            - Generar reporte
GET  /reportes            - Listar reportes
(y similares...)
```

---

**Última actualización:** 2026-04-21  
**Versión API:** 1.0.0
