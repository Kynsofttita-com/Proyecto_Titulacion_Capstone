package com.kynsoft.msauth.service.impl;

import com.kynsoft.msauth.dto.request.ForgotPasswordRequest;
import com.kynsoft.msauth.dto.request.LoginRequest;
import com.kynsoft.msauth.dto.request.ResetPasswordRequest;
import com.kynsoft.msauth.dto.response.LoginResponse;
import com.kynsoft.msauth.dto.response.UserResponse;
import com.kynsoft.msauth.entity.AuditLog;
import com.kynsoft.msauth.entity.PasswordResetToken;
import com.kynsoft.msauth.entity.User;
import com.kynsoft.msauth.enums.AuditAction;
import com.kynsoft.msauth.enums.UserStatus;
import com.kynsoft.msauth.exception.AccountBlockedException;
import com.kynsoft.msauth.exception.InvalidCredentialsException;
import com.kynsoft.msauth.exception.TokenExpiredException;
import com.kynsoft.msauth.repository.AuditLogRepository;
import com.kynsoft.msauth.repository.PasswordResetTokenRepository;
import com.kynsoft.msauth.repository.UserRepository;
import com.kynsoft.msauth.security.jwt.JwtTokenProvider;
import com.kynsoft.msauth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final int LOCK_DURATION_MINUTES = 15;
    private static final int RESET_TOKEN_EXPIRY_HOURS = 1;

    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RabbitTemplate rabbitTemplate;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${app.frontend-url:http://localhost:5173}")
    private String frontendUrl;

    public AuthServiceImpl(UserRepository userRepository,
                           AuditLogRepository auditLogRepository,
                           PasswordResetTokenRepository passwordResetTokenRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.auditLogRepository = auditLogRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        // Verificar si está bloqueado
        if (user.getStatus() == UserStatus.BLOCKED) {
            throw new AccountBlockedException("Cuenta bloqueada permanentemente");
        }

        // Verificar bloqueo temporal
        if (user.getLockedUntil() != null &&
                user.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new AccountBlockedException(
                "Cuenta bloqueada temporalmente hasta: " + user.getLockedUntil());
        }

        // Verificar contraseña
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            handleFailedLogin(user);
            throw new InvalidCredentialsException();
        }

        // Login exitoso - resetear intentos fallidos
        user.setFailedAttempts(0);
        user.setLockedUntil(null);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Registrar auditoría
        registrarAudit(user.getId(), AuditAction.LOGIN_SUCCESS, null);

        // Generar token
        String token = jwtTokenProvider.generateToken(user);

        // Obtener rol principal
        String role = user.getRoles().stream()
                .findFirst()
                .map(r -> r.getName().name())
                .orElse("ESTUDIANTE");

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getStatus().name()
        );

        return new LoginResponse(token, jwtExpiration, role, userResponse);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            // Generar token único
            String token = UUID.randomUUID().toString();

            PasswordResetToken resetToken = PasswordResetToken.builder()
                    .user(user)
                    .token(token)
                    .expiresAt(LocalDateTime.now().plusHours(RESET_TOKEN_EXPIRY_HOURS))
                    .used(false)
                    .build();

            passwordResetTokenRepository.save(resetToken);

            // Publicar evento en RabbitMQ
            Map<String, String> event = new HashMap<>();
            event.put("email", user.getEmail());
            event.put("firstName", user.getFirstName());
            event.put("token", token);
            event.put("resetUrl", frontendUrl + "/reset-password?token=" + token);

            try {
                rabbitTemplate.convertAndSend(
                    "notificaciones.exchange",
                    "password.reset",
                    event
                );
                log.info("Evento password reset publicado para: {}", user.getEmail());
            } catch (Exception e) {
                log.error("Error publicando evento RabbitMQ: {}", e.getMessage());
            }
        });
        // Siempre responder igual por seguridad (no revelar si el email existe)
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = passwordResetTokenRepository
                .findByToken(request.token())
                .orElseThrow(TokenExpiredException::new);

        // Verificar validez del token
        if (resetToken.getUsed()) {
            throw new TokenExpiredException();
        }
        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }

        // Actualizar contraseña
        User user = resetToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        // Marcar token como usado
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        // Registrar auditoría
        registrarAudit(user.getId(), AuditAction.PASSWORD_RESET, null);

        log.info("Contraseña reseteada para usuario: {}", user.getEmail());
    }

    @Override
    public void logout(String userId) {
        registrarAudit(UUID.fromString(userId), AuditAction.LOGOUT, null);
        log.info("Logout registrado para usuario: {}", userId);
    }

    private void handleFailedLogin(User user) {
        int attempts = user.getFailedAttempts() + 1;
        user.setFailedAttempts(attempts);

        if (attempts >= MAX_FAILED_ATTEMPTS) {
            user.setLockedUntil(
                LocalDateTime.now().plusMinutes(LOCK_DURATION_MINUTES));
            log.warn("Usuario bloqueado temporalmente: {}", user.getEmail());
        }

        userRepository.save(user);
        registrarAudit(user.getId(), AuditAction.LOGIN_FAILED, null);
    }

    private void registrarAudit(UUID userId, AuditAction action,
                                  Map<String, Object> details) {
        AuditLog audit = AuditLog.builder()
                .userId(userId)
                .action(action)
                .details(details)
                .build();
        auditLogRepository.save(audit);
    }
}