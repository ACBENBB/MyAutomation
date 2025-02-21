package io.securitize.infra.exceptions;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}