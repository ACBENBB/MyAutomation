package io.securitize.infra.exceptions;

public class SumsubBypassFailureException extends RuntimeException {
    public SumsubBypassFailureException(String errorMessage) {
        super(errorMessage);
    }
}