package infra.exceptions;

public class DataFormatException extends RuntimeException {
    public DataFormatException(String message) {
        super(message);
    }
}