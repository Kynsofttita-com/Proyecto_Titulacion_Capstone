package com.kynsoft.msauth.dto.response;

public record LoginResponse(
    String token,
    String tokenType,
    Long expiresIn,
    String role,
    UserResponse user
) {
    public LoginResponse(String token, Long expiresIn, String role, UserResponse user) {
        this(token, "Bearer", expiresIn, role, user);
    }
}