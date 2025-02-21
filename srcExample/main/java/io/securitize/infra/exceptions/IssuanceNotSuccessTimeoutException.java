package io.securitize.infra.exceptions;

public class IssuanceNotSuccessTimeoutException extends RuntimeException {
    public IssuanceNotSuccessTimeoutException(String errorMessage) {
        super(errorMessage);
    }

    public IssuanceNotSuccessTimeoutException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}