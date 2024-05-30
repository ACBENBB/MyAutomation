package infra.exceptions;

public class TimeoutException extends RuntimeException {

    public TimeoutException(String errorMessage) {
        super(errorMessage);
    }

    public TimeoutException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
