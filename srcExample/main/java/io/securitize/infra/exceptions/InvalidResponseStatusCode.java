package io.securitize.infra.exceptions;

public class InvalidResponseStatusCode extends RuntimeException {
    private static final String messageTemplate = "Expected status code: %s but actual status code: %s";
    public InvalidResponseStatusCode(int expectedStatusCode, int actualStatusCode) {
        super(String.format(messageTemplate, expectedStatusCode, actualStatusCode));
    }

    public InvalidResponseStatusCode(int expectedStatusCode, int actualStatusCode, Throwable err) {
        super(String.format(messageTemplate, expectedStatusCode, actualStatusCode), err);
    }
}