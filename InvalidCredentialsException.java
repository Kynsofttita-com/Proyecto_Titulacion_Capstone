package com.kynsoft.msauth.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Credenciales inválidas");
    }
    public InvalidCredentialsException(String message) {
        super(message);
    }
}