package io.securitize.infra.exceptions;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String errorMessage) {
        super(errorMessage);
    }
}