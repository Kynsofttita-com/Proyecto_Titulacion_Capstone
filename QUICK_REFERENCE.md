# ⚡ Guía Rápida - Driving School API

## 🎯 Lo Que Necesitas Saber

```
BASE URL: http://localhost:8080/api
```

---

## 🔑 PASO 1: Obtener Token

### Login Admin (Recomendado)
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@kynsoft.com",
    "password": "Admin2026!"
  }'
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "email": "admin@kynsoft.com",
    "roles": ["ADMIN"]
  }
}
```

**Copiar el valor de `token`** ⬅️ **Esto es lo importante**

---

## 📋 TODOS LOS ENDPOINTS

### 🔐 Autenticación (SIN token requerido)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/login` | Obtener JWT token |
| POST | `/auth/forgot-password` | Solicitar reset de contraseña |
| POST | `/auth/reset-password` | Cambiar contraseña |
| POST | `/auth/logout` | Cerrar sesión |
| GET | `/auth/dev/generate-hash` | Generar hash BCrypt |

---

### 👥 Usuarios (REQUIEREN token)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/users` | Listar todos los usuarios |
| GET | `/users/{id}` | Obtener usuario por ID |
| POST | `/users` | Crear nuevo usuario |
| PUT | `/users/{id}` | Actualizar usuario |
| PATCH | `/users/{id}/status` | Cambiar estado (ACTIVE/INACTIVE/SUSPENDED) |
| POST | `/users/{id}/roles` | Asignar rol (ADMIN/INSTRUCTOR/ESTUDIANTE) |

---

## 🚀 EJEMPLOS DE USO

### ✅ GET - Listar Usuarios
```bash
curl -X GET http://localhost:8080/api/users?page=0&size=10 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### ✅ POST - Crear Usuario
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "newuser@kynsoft.com",
    "password": "Password123!",
    "name": "New User"
  }'
```

### ✅ PUT - Actualizar Usuario
```bash
curl -X PUT http://localhost:8080/api/users/550e8400-e29b-41d4-a716-446655440000 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "updated@kynsoft.com",
    "name": "Updated Name"
  }'
```

### ✅ PATCH - Cambiar Estado
```bash
curl -X PATCH http://localhost:8080/api/users/550e8400-e29b-41d4-a716-446655440000/status \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "SUSPENDED"
  }'
```

### ✅ POST - Asignar Rol
```bash
curl -X POST http://localhost:8080/api/users/550e8400-e29b-41d4-a716-446655440000/roles \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "roleName": "INSTRUCTOR"
  }'
```

---

## 🔒 Headers Obligatorios

### Para Login (sin token):
```
Content-Type: application/json
```

### Para Usuarios (con token):
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
Content-Type: application/json
```

---

## 📊 Credenciales Demo

| Rol | Email | Password |
|-----|-------|----------|
| ADMIN | admin@kynsoft.com | Admin2026! |
| INSTRUCTOR | instructor@kynsoft.com | Inst2026! |
| ESTUDIANTE | estudiante@kynsoft.com | Est2026! |

---

## 💡 Postman Tips

### Guardar Token Automáticamente
En el endpoint de login, abre la pestaña "Tests" y agrega:
```javascript
if (pm.response.code === 200) {
    const responseJson = pm.response.json();
    pm.collectionVariables.set("token", responseJson.token);
}
```

Luego en otros endpoints, usa `{{token}}` en los headers.

---

## ⚙️ Estados y Roles

### Estados Válidos:
- `ACTIVE` - Usuario activo
- `INACTIVE` - Usuario inactivo
- `SUSPENDED` - Usuario suspendido

### Roles Válidos:
- `ADMIN` - Administrador
- `ADMINISTRATIVO` - Personal administrativo
- `INSTRUCTOR` - Instructor de conducción
- `ESTUDIANTE` - Estudiante

---

## 🆘 Errores Comunes

```
401 - Unauthorized
→ Token inválido o expirado
→ Solución: Obtener nuevo token

403 - Forbidden
→ No tienes permiso (necesitas ser ADMIN)
→ Solución: Usar usuario admin

404 - Not Found
→ Usuario/recurso no existe
→ Solución: Verificar el ID

400 - Bad Request
→ JSON inválido o campos faltantes
→ Solución: Revisar el body de la solicitud

409 - Conflict
→ El email ya existe
→ Solución: Usar un email diferente
```

---

## 📥 Importar en Postman

1. **Abre Postman**
2. **Click "Import"** (arriba a la izquierda)
3. **Selecciona "Upload Files"**
4. **Abre:** `Driving_School_API.postman_collection.json`
5. **¡Listo!** Todos los endpoints están listos para usar

---

## 🔗 URLs de Referencia

```
API Base:           http://localhost:8080/api
Login:              http://localhost:8080/api/auth/login
Usuarios:           http://localhost:8080/api/users
Health Check:       http://localhost:8080/actuator/health
Frontend:           http://localhost/login
```

---

## ✨ Resumen Rápido

1. **LOGIN:**
   ```
   POST /auth/login
   Body: {"email": "admin@kynsoft.com", "password": "Admin2026!"}
   ```

2. **COPIAR TOKEN** de la respuesta

3. **USAR TOKEN** en header:
   ```
   Authorization: Bearer TOKEN_AQUI
   ```

4. **HACER REQUESTS** a cualquier endpoint de usuarios

---

**¡Eso es todo! 🚀**
