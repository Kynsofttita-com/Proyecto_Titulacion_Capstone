package com.kynsoft.msauth.exception;

public class AccountBlockedException extends RuntimeException {
    public AccountBlockedException() {
        super("Cuenta bloqueada temporalmente. Intente más tarde.");
    }
    public AccountBlockedException(String message) {
        super(message);
    }
}
