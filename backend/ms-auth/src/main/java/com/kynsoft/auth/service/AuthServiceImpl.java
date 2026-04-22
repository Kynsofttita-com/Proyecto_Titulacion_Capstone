package com.kynsoft.auth.service;

import com.kynsoft.auth.dto.ForgotPasswordRequest;
import com.kynsoft.auth.dto.LoginRequest;
import com.kynsoft.auth.dto.LoginResponse;
import com.kynsoft.auth.dto.ResetPasswordRequest;
import com.kynsoft.auth.dto.UserResponse;
import com.kynsoft.auth.entity.AuditLog;
import com.kynsoft.auth.entity.PasswordResetToken;
import com.kynsoft.auth.entity.User;
import com.kynsoft.auth.enums.AuditAction;
import com.kynsoft.auth.enums.UserStatus;
import com.kynsoft.auth.exception.AccountBlockedException;
import com.kynsoft.auth.exception.EmailNotFoundException;
import com.kynsoft.auth.exception.InvalidCredentialsException;
import com.kynsoft.auth.exception.TokenExpiredException;
import com.kynsoft.auth.repository.AuditLogRepository;
import com.kynsoft.auth.repository.PasswordResetTokenRepository;
import com.kynsoft.auth.repository.UserRepository;
import com.kynsoft.auth.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        // Verificar si la cuenta está bloqueada
        if (user.getStatus() == UserStatus.BLOCKED) {
            if (user.getLockedUntil() != null && user.getLockedUntil().isAfter(LocalDateTime.now())) {
                Map<String, Object> detailsMap = new HashMap<>();
                detailsMap.put("reason", "Account is locked");
                auditLogRepository.save(AuditLog.builder()
                        .user(user)
                        .action(AuditAction.LOGIN_FAILED)
                        .ipAddress(getClientIp())
                        .userAgent(httpServletRequest.getHeader("User-Agent"))
                        .details(detailsMap)
                        .build());
                throw new AccountBlockedException("Account is locked. Try again later");
            }
        }

        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            user.setFailedAttempts(user.getFailedAttempts() + 1);

            // Si hay 3 intentos fallidos, bloquear por 15 minutos
            if (user.getFailedAttempts() >= 3) {
                user.setStatus(UserStatus.BLOCKED);
                user.setLockedUntil(LocalDateTime.now().plusMinutes(15));
                userRepository.save(user);

                Map<String, Object> blockedDetails = new HashMap<>();
                blockedDetails.put("reason", "Account blocked after 3 failed login attempts");
                auditLogRepository.save(AuditLog.builder()
                        .user(user)
                        .action(AuditAction.USER_BLOCKED)
                        .ipAddress(getClientIp())
                        .userAgent(httpServletRequest.getHeader("User-Agent"))
                        .details(blockedDetails)
                        .build());

                throw new AccountBlockedException("Account blocked after 3 failed attempts. Try again in 15 minutes");
            }

            userRepository.save(user);

            Map<String, Object> failDetails = new HashMap<>();
            failDetails.put("reason", "Invalid password");
            failDetails.put("attempt", user.getFailedAttempts());
            auditLogRepository.save(AuditLog.builder()
                    .user(user)
                    .action(AuditAction.LOGIN_FAILED)
                    .ipAddress(getClientIp())
                    .userAgent(httpServletRequest.getHeader("User-Agent"))
                    .details(failDetails)
                    .build());

            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Login exitoso
        user.setFailedAttempts(0);
        user.setStatus(UserStatus.ACTIVE);
        user.setLockedUntil(null);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        auditLogRepository.save(AuditLog.builder()
                .user(user)
                .action(AuditAction.LOGIN_SUCCESS)
                .ipAddress(getClientIp())
                .userAgent(httpServletRequest.getHeader("User-Agent"))
                .build());

        String token = jwtTokenProvider.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .expiresIn(86400000L) // 24 horas en ms
                .user(buildUserResponse(user))
                .build();
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            log.warn("Forgot password request for non-existent email: {}", request.getEmail());
            return;
        }

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plusHours(1))
                .used(false)
                .build();

        passwordResetTokenRepository.save(resetToken);

        log.info("Password reset token generated for user: {}", user.getEmail());
        // TODO: Send email with reset link
        // emailService.sendPasswordResetEmail(user.getEmail(), resetToken.getToken());
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new TokenExpiredException("Invalid or expired reset token"));

        if (resetToken.getUsed()) {
            throw new TokenExpiredException("Token has already been used");
        }

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Reset token has expired");
        }

        User user = resetToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        auditLogRepository.save(AuditLog.builder()
                .user(user)
                .action(AuditAction.PASSWORD_RESET)
                .ipAddress(getClientIp())
                .userAgent(httpServletRequest.getHeader("User-Agent"))
                .build());

        log.info("Password reset successful for user: {}", user.getEmail());
    }

    @Override
    @Transactional
    public void logout(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new EmailNotFoundException("User not found"));

        auditLogRepository.save(AuditLog.builder()
                .user(user)
                .action(AuditAction.LOGOUT)
                .ipAddress(getClientIp())
                .userAgent(httpServletRequest.getHeader("User-Agent"))
                .build());

        log.info("User logged out: {}", user.getEmail());
    }

    private UserResponse buildUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus().toString())
                .roles(user.getRoles().stream()
                        .map(role -> role.getName().toString())
                        .collect(Collectors.toList()))
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .build();
    }

    private String getClientIp() {
        String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0];
        }
        return httpServletRequest.getRemoteAddr();
    }
}
