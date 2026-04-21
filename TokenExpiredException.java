package com.kynsoft.msauth.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException() {
        super("El token ha expirado o es inválido");
    }
}
