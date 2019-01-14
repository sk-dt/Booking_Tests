package base;

public class UnLoggingException extends Exception {

    public UnLoggingException() {
        super();
    }

    public UnLoggingException(String message) {
        super(message);
    }

    public UnLoggingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnLoggingException(Throwable cause) {
        super(cause);
    }
}