# Driving School System - Contexto del Proyecto

## Stack tecnológico
- Backend: Java 21 + Spring Boot 3.5 + Spring Cloud
- Frontend: Vue.js 3 + Vite + Tailwind
- BD: PostgreSQL 16 (8 bases de datos separadas)
- Mensajería: RabbitMQ 3.13
- Contenedores: Docker + Docker Compose
- Seguridad: JWT (jjwt 0.12.5) + Spring Security + BCrypt
- Migraciones: Flyway
- Docs API: Swagger/OpenAPI

## Arquitectura
Microservicios con arquitectura hexagonal por capas:
- presentation/ → Controllers REST
- application/service/ → Lógica de negocio
- infrastructure/ → JPA, repositorios
- security/jwt/ → JWT

## Microservicios y puertos
- API Gateway: 8080
- MS-Auth: 8081 → db_auth
- MS-Estudiantes: 8082 → db_estudiantes
- MS-Instructores: 8083 → db_instructores
- MS-Vehículos: 8084 → db_vehiculos
- MS-Asignaciones: 8085 → db_asignaciones
- MS-Cobros: 8086 → db_cobros
- MS-Reportes: 8087 → db_reportes
- MS-Notificaciones: 8088 → db_notificaciones
- Eureka Server: 8761
- Config Server: 8888
- PostgreSQL: 5432
- RabbitMQ: 5672 / Panel: 15672

## Sprint actual: Sprint 2 - MS-Auth
### Completado:
- Entidades JPA: User, Role, AuditLog, PasswordResetToken
- Enums: UserStatus, RoleName, AuditAction
- Repositorios JPA
- Migración Flyway V1__create_auth_tables.sql
- JwtTokenProvider
- JwtAuthenticationFilter
- SecurityConfig
- DTOs: LoginRequest, LoginResponse, ForgotPasswordRequest, ResetPasswordRequest
- Excepciones: InvalidCredentialsException, AccountBlockedException, TokenExpiredException
- GlobalExceptionHandler
- AuthService + AuthServiceImpl
- AuthController

### Pendiente Sprint 2:
- Implementar CRUD usuarios (UserService, UserController)
- Filtro JWT en API Gateway
- Frontend Login con Vue.js
- Docker del MS-Auth
- Pruebas unitarias

## Rama Git actual
feature/ms-auth → develop → main

## Convención de commits
feat(ms-auth): nueva funcionalidad
fix(ms-auth): corrección
chore: configuración
docs: documentación
ci: pipelines
infra: docker

## Credenciales Docker (desarrollo)
DB_USER=dsadmin
DB_PASS=dspass2026
RABBITMQ_USER=dsrabbit
RABBITMQ_PASS=dsrabbit2026
Admin email: admin@kynsoft.com
Admin pass: Admin2026!