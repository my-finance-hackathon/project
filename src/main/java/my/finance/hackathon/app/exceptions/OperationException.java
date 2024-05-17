package my.finance.hackathon.app.exceptions;

public class OperationException extends RuntimeException {
    public OperationException(String message) {
        super(message);
    }
}
