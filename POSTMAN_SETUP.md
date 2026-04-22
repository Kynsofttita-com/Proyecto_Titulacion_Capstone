# 📮 Guía de Configuración Postman - Driving School API

## 📥 Paso 1: Importar Colección

### Opción A: Importar JSON (Recomendado)
1. **Abre Postman**
2. **Click en "Import" (arriba a la izquierda)**
3. **Selecciona "Upload Files"**
4. **Navega a:** `Driving_School_API.postman_collection.json`
5. **Click en "Import"**

### Opción B: Importar desde URL
1. **Click en "Import"**
2. **Selecciona "Link"**
3. Pega la URL del archivo

---

## 🔑 Paso 2: Configurar Variable de Token

### Método Manual (Recomendado para empezar):

1. **En Postman, abre la pestaña "Collections"** (lado izquierdo)
2. **Busca "Driving School API"**
3. **Click derecho → Edit**
4. **Abre la pestaña "Variables"**
5. **Crea/edita la variable:**
   - **Name:** `token`
   - **Initial value:** (dejar vacío)
   - **Current value:** (dejar vacío)
6. **Guarda**

### Método Automático (con Script):

1. **Abre la colección**
2. **Abre el endpoint "Login - Obtener JWT Token"**
3. **Haz click en "Tests" (pestaña)**
4. **Copia el siguiente código:**

```javascript
if (pm.response.code === 200) {
    const responseJson = pm.response.json();
    pm.collectionVariables.set("token", responseJson.token);
    console.log("✅ Token guardado automáticamente");
} else {
    console.log("❌ Error en login");
}
```

5. **Haz click en "Send"**
6. **El token se guardará automáticamente** en la variable `{{token}}`

---

## ✅ Paso 3: Obtener JWT Token

### Opción 1: Usando el Endpoint de Login

1. **Abre la colección "Driving School API"**
2. **Expande "🔐 AUTENTICACIÓN"**
3. **Haz click en "Login - Obtener JWT Token"**
4. **Verifica que el body tenga:**
   ```json
   {
     "email": "admin@kynsoft.com",
     "password": "Admin2026!"
   }
   ```
5. **Haz click en "Send"**
6. **Recibirás una respuesta como:**
   ```json
   {
     "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBr...",
     "user": {
       "id": "550e8400-e29b-41d4-a716-446655440000",
       "email": "admin@kynsoft.com",
       "roles": ["ADMIN"]
     }
   }
   ```
7. **Copia el valor de "token"**

### Opción 2: Usar credenciales diferentes

**Instructor:**
- Email: `instructor@kynsoft.com`
- Password: `Inst2026!`

**Estudiante:**
- Email: `estudiante@kynsoft.com`
- Password: `Est2026!`

---

## 🔐 Paso 4: Usar el Token en Requests Protegidos

### Método 1: Variable (Automático)

1. **Todos los endpoints en "👥 USUARIOS" ya tienen:**
   ```
   Authorization: Bearer {{token}}
   ```
2. **Solo necesitas que el token esté en la variable `{{token}}`**
3. **Haz click en "Send"**

### Método 2: Headers Manuales

Si prefieres configurar manualmente:

1. **Abre cualquier endpoint protegido**
2. **Abre la pestaña "Headers"**
3. **Agrega:**
   - **Key:** `Authorization`
   - **Value:** `Bearer eyJhbGciOiJIUzUxMiJ9...` (tu token)
4. **Haz click en "Send"**

---

## 🧪 Paso 5: Pruebas Rápidas

### Test 1: Login y Obtener Token
```
1. POST /auth/login
2. Copiar token de la respuesta
```

### Test 2: Listar Usuarios
```
1. GET /users
2. Headers: Authorization: Bearer {{token}}
3. Should see status 200 con lista de usuarios
```

### Test 3: Obtener Usuario Específico
```
1. GET /users/{id}
2. Reemplaza {id} con un ID real
3. Headers: Authorization: Bearer {{token}}
```

### Test 4: Crear Nuevo Usuario
```
1. POST /users
2. Headers: Authorization: Bearer {{token}}
3. Body:
{
  "email": "testuser@kynsoft.com",
  "password": "Test123!",
  "name": "Test User"
}
```

---

## 📋 Estructura de la Colección

```
📦 Driving School API
├── 🔐 AUTENTICACIÓN
│   ├── Login - Obtener JWT Token ⭐
│   ├── Login - Instructor
│   ├── Login - Estudiante
│   ├── Forgot Password
│   ├── Reset Password
│   ├── Logout
│   └── Generate Hash (DEV ONLY)
│
└── 👥 USUARIOS (Requieren Token)
    ├── Listar Usuarios
    ├── Obtener Usuario por ID
    ├── Crear Usuario
    ├── Actualizar Usuario
    ├── Cambiar Estado de Usuario
    └── Asignar Rol a Usuario
```

---

## 🛠️ Troubleshooting

### ❌ Error: "Token is invalid or expired"
**Solución:**
1. Haz login nuevamente en "Login - Obtener JWT Token"
2. Copia el nuevo token
3. Actualiza la variable `{{token}}`
4. Reintenta el request

### ❌ Error: "401 Unauthorized"
**Solución:**
1. Verifica que hayas agregado el header `Authorization`
2. Verifica que el token comience con `Bearer `
3. Obtén un nuevo token si el anterior expiró

### ❌ Error: "Connection refused"
**Solución:**
1. Verifica que Docker esté corriendo: `docker-compose ps`
2. Si no está corriendo: `docker-compose up -d`
3. Espera 30 segundos para que se inicialicen los servicios
4. Verifica que el API Gateway esté en el puerto 8080:
   ```bash
   curl http://localhost:8080/actuator/health
   ```

### ❌ Error: "400 Bad Request"
**Solución:**
1. Verifica que el JSON sea válido
2. Verifica que los campos requeridos estén presentes
3. Verifica el Content-Type: `application/json`

---

## 💾 Guardar Configuración

Para guardar tu progreso en Postman:

1. **Click en el botón "Save" (arriba)**
2. **Postman guardará automáticamente todos los cambios**
3. **Tu colección y variables se guardan en la nube (si sincronizas tu cuenta)**

---

## 🚀 Flujo Completo de Ejemplo

```mermaid
1. POST /auth/login
   ↓ (recibe token)
2. Guardar token en {{token}}
   ↓
3. GET /users (con Authorization header)
   ↓ (recibe lista de usuarios)
4. GET /users/{id} (con Authorization header)
   ↓ (recibe detalles del usuario)
5. POST /users (crear nuevo usuario)
   ↓
6. PUT /users/{id} (actualizar usuario)
   ↓
7. PATCH /users/{id}/status (cambiar estado)
```

---

## 📞 Notas Importantes

- **Token válido por:** 24 horas (86400 segundos)
- **Base URL:** `http://localhost:8080/api`
- **Content-Type:** `application/json` (para POST/PUT/PATCH)
- **Todos los endpoints protegidos requieren:** `Authorization: Bearer {{token}}`

---

**¡Listo! Ya puedes usar la colección Postman completamente.**
