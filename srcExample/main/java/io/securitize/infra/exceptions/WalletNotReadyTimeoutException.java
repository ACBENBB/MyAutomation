package io.securitize.infra.exceptions;

public class WalletNotReadyTimeoutException extends RuntimeException {
    public WalletNotReadyTimeoutException(String errorMessage) {
        super(errorMessage);
    }

    public WalletNotReadyTimeoutException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}