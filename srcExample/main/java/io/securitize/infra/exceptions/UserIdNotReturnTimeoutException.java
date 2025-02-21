package io.securitize.infra.exceptions;

public class UserIdNotReturnTimeoutException extends RuntimeException {
    public UserIdNotReturnTimeoutException(String errorMessage) {
        super(errorMessage);
    }

    public UserIdNotReturnTimeoutException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}