# 🚀 Guía Completa: Claude Code + DriveSchool System
## Configuración desde cero en VSCode

---

## PASO 1: Instalar Claude Code en VSCode

1. Abre **VSCode**
2. Presiona `Ctrl+Shift+X` (Extensions)
3. Busca **"Claude"** de Anthropic
4. Clic en **Install**
5. Reinicia VSCode
6. Presiona `Ctrl+Shift+P` → busca **"Claude: Sign In"**
7. Inicia sesión con tu cuenta Anthropic

---

## PASO 2: Clonar el repositorio

```bash
# En terminal de VSCode (Ctrl+`)
cd C:\Users\hmate\OneDrive\Documentos\Udla\TITA Semestre 2

git clone https://github.com/Kynsofttita-com/Proyecto_Titulacion_Capstone.git

cd Proyecto_Titulacion_Capstone

code .
```

---

## PASO 3: Copiar archivos de configuración Claude Code

Copia estos archivos al repositorio:

```
Proyecto_Titulacion_Capstone/
├── CLAUDE.md                    ← Guía maestra del proyecto
└── .claude/
    └── agents/
        ├── backend-java.md      ← Agente backend
        ├── frontend-vue.md      ← Agente frontend
        ├── devops.md            ← Agente DevOps
        └── database.md          ← Agente base de datos
```

```bash
# Crear carpeta de agentes
mkdir -p .claude/agents

# Copiar los archivos descargados aquí
# (CLAUDE.md, y los 4 archivos de agentes)
```

---

## PASO 4: Verificar estructura del proyecto

```bash
# Ver estructura actual
ls -la

# Debe mostrar:
# backend/
# frontend/
# infrastructure/
# docs/
# CLAUDE.md        ← nuevo
# .claude/         ← nuevo
# README.md
```

---

## PASO 5: Instalar extensiones recomendadas de VSCode

Instala estas extensiones para el proyecto:

```
Extension Pack for Java     → Microsoft
Spring Boot Extension Pack  → VMware
Vue - Official              → Vue
Tailwind CSS IntelliSense   → Tailwind Labs
Docker                      → Microsoft
GitLens                     → GitKraken
Thunder Client             → Thunder Client (Postman alternativo)
Draw.io Integration         → Henning Dieterichs
```

O instala todas de una vez creando este archivo:

```bash
# En la raíz del proyecto
cat > .vscode/extensions.json << 'EOF'
{
  "recommendations": [
    "vscjava.vscode-java-pack",
    "vmware.vscode-spring-boot",
    "Vue.volar",
    "bradlc.vscode-tailwindcss",
    "ms-azuretools.vscode-docker",
    "eamodio.gitlens",
    "rangav.vscode-thunder-client",
    "hediet.vscode-drawio",
    "humao.rest-client",
    "ms-vscode.vscode-json"
  ]
}
EOF
```

---

## PASO 6: Configurar VSCode Settings para el proyecto

```bash
cat > .vscode/settings.json << 'EOF'
{
  "editor.formatOnSave": true,
  "editor.tabSize": 4,
  "editor.insertSpaces": true,
  "java.configuration.updateBuildConfiguration": "automatic",
  "java.compile.nullAnalysis.mode": "automatic",
  "spring-boot.ls.java.home": "",
  "tailwindCSS.includeLanguages": {
    "vue": "html"
  },
  "editor.quickSuggestions": {
    "strings": true
  },
  "files.associations": {
    "*.vue": "vue"
  },
  "[java]": {
    "editor.defaultFormatter": "redhat.java"
  },
  "[vue]": {
    "editor.defaultFormatter": "Vue.volar"
  },
  "[javascript]": {
    "editor.defaultFormatter": "esbenp.prettier-vscode"
  }
}
EOF
```

---

## PASO 7: Levantar la infraestructura Docker

```bash
# Verificar que Docker Desktop está corriendo
docker --version

# Levantar servicios base
cd infrastructure/docker
docker-compose up -d postgres rabbitmq eureka-server config-server api-gateway

# Verificar que están corriendo
docker ps

# Ver logs si hay error
docker logs ds-postgres
docker logs ds-eureka
```

---

## PASO 8: Usar Claude Code correctamente

### Abrir Claude Code:
- `Ctrl+Shift+P` → **"Open Claude"**
- O usa el ícono de Claude en la barra lateral

### Comandos útiles con Claude Code:

```
# Pedirle que implemente una tarea completa:
"Implementa el UserService y UserController para el MS-Auth 
siguiendo las convenciones del CLAUDE.md. Incluye tests."

# Usar un agente específico:
"Usa el agente backend-java para crear la migración Flyway 
V2 para la tabla de estudiantes"

# Pedir revisión de código:
"Revisa este archivo y verifica que sigue las convenciones 
del proyecto definidas en CLAUDE.md"

# Debug:
"Tengo este error en Spring Boot: [pegar error]. 
Analiza el contexto del proyecto y corrígelo"
```

### Workflow recomendado con Claude Code:

```
1. Abrir Claude Code en VSCode
2. Decirle: "Lee el CLAUDE.md y dime en qué tarea del Sprint 2 
   debemos trabajar ahora"
3. Claude Code lee el contexto y propone el siguiente paso
4. Apruebas o modificas
5. Claude Code implementa el código directamente en los archivos
6. Revisas el código
7. Haces commit
```

---

## PASO 9: Flujo de trabajo diario

```bash
# Al inicio del día:
git checkout develop
git pull origin develop
git checkout feature/ms-auth

# Abrir VSCode con Claude Code
code .

# Decirle a Claude Code:
# "Continuemos con el Sprint 2. El último avance fue [describir].
#  Necesito implementar [siguiente tarea]"

# Al terminar:
git add .
git commit -m "feat(ms-auth): descripción clara"
git push origin feature/ms-auth
```

---

## PASO 10: Verificar que todo funciona

```bash
# Backend MS-Auth
cd backend/ms-auth
mvn spring-boot:run

# En otra terminal - verificar Swagger
# Abrir: http://localhost:8081/swagger-ui.html

# Frontend
cd frontend/driving-school-app
npm run dev
# Abrir: http://localhost:5173

# Probar login
# POST http://localhost:8080/api/auth/login
# {"email": "admin@kynsoft.com", "password": "Admin2026!"}
```

---

## 🎯 PRÓXIMAS TAREAS (Sprint 2 pendientes)

Dile esto a Claude Code en orden:

### Tarea 1:
```
"Implementa UserService interface y UserServiceImpl en el MS-Auth.
Debe incluir: createUser, updateUser, changeStatus, findById, 
findAll con paginación y filtro por status, assignRole.
Sigue las convenciones del CLAUDE.md"
```

### Tarea 2:
```
"Implementa UserController en /api/users con todos los endpoints 
CRUD. Todos deben tener @PreAuthorize('hasRole(ADMIN)') y 
documentación Swagger completa"
```

### Tarea 3:
```
"Implementa AuditController en /api/audit con paginación,
filtros por userId, action, from, to. Solo ADMIN."
```

### Tarea 4:
```
"Escribe los tests unitarios para AuthServiceImpl y UserServiceImpl.
Mínimo 15 tests en total. Cubre: happy path, credenciales inválidas,
bloqueo por 3 intentos, token expirado, email duplicado"
```

### Tarea 5:
```
"Configura Swagger/OpenAPI en el MS-Auth con @OpenAPIDefinition,
@SecurityScheme para JWT Bearer. Anota todos los controllers."
```

### Tarea 6:
```
"Crea el filtro JWT en el API Gateway. Debe validar el token
antes de rutear a los microservicios y retornar 401 si es inválido."
```

### Tarea 7:
```
"Crea el proyecto Vue.js 3 completo con Vite en frontend/driving-school-app/.
Diseño premium dark theme con los colores del CLAUDE.md.
LoginView con diseño Lovable/Linear style."
```

### Tarea 8:
```
"Crea el Dockerfile para el MS-Auth y agrégalo al docker-compose.yml.
Usa el template del agente devops."
```

---

## ⚡ TIPS para usar Claude Code efectivamente

1. **Da contexto siempre**: "Estamos en Sprint 2, el MS-Auth, 
   implementando el UserController"

2. **Referencia el CLAUDE.md**: "Sigue las convenciones del CLAUDE.md"

3. **Pide que verifique**: "Después de implementar, verifica que 
   compila y corre los tests"

4. **Un agente a la vez**: Para código Java usa el agente backend-java,
   para Vue.js usa frontend-vue

5. **Commits frecuentes**: Después de cada tarea completa, 
   haz commit antes de la siguiente

6. **Si hay error**: Comparte el error completo y di "¿Cómo lo corriges
   siguiendo las convenciones del proyecto?"