package io.securitize.infra.exceptions;

public class LivenessCheckException extends RuntimeException {
    public LivenessCheckException(String errorMessage) {
        super(errorMessage);
    }

    public LivenessCheckException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}