package infra.exceptions;

public class BrowserInitializationException extends RuntimeException {

    public BrowserInitializationException(String message) {
        super(message);
    }

    public BrowserInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}