package ca.utoronto.ece419.tester.wrapper.exceptions;

/**
 * Exception that is thrown when invoke returns null when not void
 */
public class NullReturnException extends RuntimeException {
    public NullReturnException(String msg) {
        super(msg);
    }

    public NullReturnException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
