package com.kynsoft.auth.controller;

import com.kynsoft.auth.dto.ForgotPasswordRequest;
import com.kynsoft.auth.dto.LoginRequest;
import com.kynsoft.auth.dto.LoginResponse;
import com.kynsoft.auth.dto.ResetPasswordRequest;
import com.kynsoft.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user with email and password")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("===== LOGIN ATTEMPT =====");
        log.info("Email: {}", request.getEmail());
        log.info("Password length: {}", request.getPassword() != null ? request.getPassword().length() : "null");
        try {
            LoginResponse response = authService.login(request);
            log.info("LOGIN SUCCESSFUL for: {}", request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("LOGIN ERROR: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password", description = "Request a password reset token")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        log.info("Forgot password request for email: {}", request.getEmail());
        authService.forgotPassword(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "If an account exists with that email, a password reset link will be sent");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Reset user password with valid token")
    public ResponseEntity<Map<String, String>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        log.info("Reset password request received");
        authService.resetPassword(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password has been reset successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Logout user and record audit log")
    public ResponseEntity<Map<String, String>> logout(@RequestParam String userId) {
        log.info("Logout request for user: {}", userId);
        authService.logout(userId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dev/generate-hash")
    @Operation(summary = "DEV ONLY: Generate BCrypt hash", description = "Development endpoint to generate BCrypt hash for a password")
    public ResponseEntity<Map<String, String>> generateHash(@RequestParam String password) {
        Map<String, String> result = new HashMap<>();
        String hash = passwordEncoder.encode(password);
        result.put("password", password);
        result.put("bcrypt_hash", hash);
        log.warn("BCrypt hash generated for development (password: {})", password);
        return ResponseEntity.ok(result);
    }

}
