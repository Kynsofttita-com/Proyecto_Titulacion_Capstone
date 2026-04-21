package com.kynsoft.msauth.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException() {
        super("Si el email existe, recibirás instrucciones");
    }
}