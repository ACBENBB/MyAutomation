package io.securitize.infra.exceptions;

public class HealthCheckException extends RuntimeException {
    public HealthCheckException(String errorMessage) {
        super(errorMessage);
    }
}