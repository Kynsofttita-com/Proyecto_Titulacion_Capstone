package com.kynsoft.msauth.controller;

import com.kynsoft.msauth.dto.request.ForgotPasswordRequest;
import com.kynsoft.msauth.dto.request.LoginRequest;
import com.kynsoft.msauth.dto.request.ResetPasswordRequest;
import com.kynsoft.msauth.dto.response.LoginResponse;
import com.kynsoft.msauth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints de autenticación y manejo de contraseñas")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y retorna un JWT")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Olvidé mi contraseña",
               description = "Envía email con token para resetear contraseña")
    public ResponseEntity<String> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        return ResponseEntity.ok(
            "Si el email existe, recibirás instrucciones para resetear tu contraseña");
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Resetear contraseña",
               description = "Cambia la contraseña usando el token recibido por email")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("Contraseña actualizada exitosamente");
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Registra el logout del usuario")
    public ResponseEntity<String> logout(
            @AuthenticationPrincipal String userId) {
        authService.logout(userId);
        return ResponseEntity.ok("Sesión cerrada exitosamente");
    }
}