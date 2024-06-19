package infra.exceptions;

public class InvalidURLException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "URL is not set or is empty. Please check the properties file.";

    public InvalidURLException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

    public InvalidURLException(Throwable err) {
        super(DEFAULT_ERROR_MESSAGE, err);
    }
}