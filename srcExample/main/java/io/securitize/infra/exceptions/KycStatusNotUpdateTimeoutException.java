package io.securitize.infra.exceptions;

public class KycStatusNotUpdateTimeoutException extends RuntimeException {

    public KycStatusNotUpdateTimeoutException(String errorMessage) {
        super(errorMessage);
    }

    public KycStatusNotUpdateTimeoutException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}