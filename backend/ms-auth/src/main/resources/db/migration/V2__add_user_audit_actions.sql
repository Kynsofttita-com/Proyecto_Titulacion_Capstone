-- =============================================
-- V2: Add new user management audit actions
-- =============================================

-- Actualizar constraint en audit_log para agregar nuevos actions
ALTER TABLE audit_log
DROP CONSTRAINT audit_log_action_check;

ALTER TABLE audit_log
ADD CONSTRAINT audit_log_action_check
CHECK (action IN (
    'LOGIN_SUCCESS',
    'LOGIN_FAILED',
    'LOGOUT',
    'PASSWORD_RESET',
    'USER_CREATED',
    'USER_BLOCKED',
    'USER_UPDATED',
    'USER_STATUS_CHANGED',
    'ROLE_ASSIGNED'
));
