-- =============================================
-- V1: Create Auth Tables
-- =============================================

-- Tabla: users
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE' 
        CHECK (status IN ('ACTIVE', 'INACTIVE', 'BLOCKED')),
    failed_attempts INT DEFAULT 0,
    locked_until TIMESTAMP NULL,
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Tabla: roles
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
        CHECK (name IN ('ADMIN', 'ADMINISTRATIVO', 'INSTRUCTOR', 'ESTUDIANTE')),
    description VARCHAR(255)
);

-- Tabla: user_roles
CREATE TABLE user_roles (
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id INT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP DEFAULT NOW(),
    assigned_by UUID REFERENCES users(id) NULL,
    PRIMARY KEY (user_id, role_id)
);

-- Tabla: audit_log
CREATE TABLE audit_log (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID REFERENCES users(id) NULL,
    action VARCHAR(50) NOT NULL
        CHECK (action IN (
            'LOGIN_SUCCESS',
            'LOGIN_FAILED', 
            'LOGOUT',
            'PASSWORD_RESET',
            'USER_CREATED',
            'USER_BLOCKED'
        )),
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    details JSONB,
    created_at TIMESTAMP DEFAULT NOW()
);

-- Tabla: password_reset_tokens
CREATE TABLE password_reset_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token VARCHAR(255) UNIQUE NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW()
);

-- =============================================
-- ÍNDICES
-- =============================================
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_audit_log_user_id ON audit_log(user_id);
CREATE INDEX idx_audit_log_action ON audit_log(action);
CREATE INDEX idx_audit_log_created_at ON audit_log(created_at);
CREATE INDEX idx_password_reset_token ON password_reset_tokens(token);
CREATE INDEX idx_password_reset_user ON password_reset_tokens(user_id);

-- =============================================
-- SEED DATA: Roles
-- =============================================
INSERT INTO roles (name, description) VALUES
    ('ADMIN', 'Administrador del sistema con acceso completo'),
    ('ADMINISTRATIVO', 'Personal administrativo de la escuela'),
    ('INSTRUCTOR', 'Instructor de manejo'),
    ('ESTUDIANTE', 'Estudiante matriculado');

-- =============================================
-- SEED DATA: Usuario Admin por defecto
-- Password: Admin2026! (bcrypt hash, cost=10)
-- =============================================
INSERT INTO users (
    id,
    email,
    password_hash,
    first_name,
    last_name,
    status
) VALUES (
    gen_random_uuid(),
    'admin@kynsoft.com',
    '$2a$10$h70JsZR1piK48H5iVbtOr.Tdg4sp4.WKvD6V3WARMRFbC3EMsoc7K',
    'Admin',
    'Kynsoft',
    'ACTIVE'
);

-- Asignar rol ADMIN al usuario admin
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'admin@kynsoft.com'
AND r.name = 'ADMIN';