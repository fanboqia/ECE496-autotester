package ca.utoronto.ece419.tester.wrapper.exceptions;

/**
 * Exception that is thrown when called method returns an exception
 */
public class ForwardedException extends RuntimeException {
    public ForwardedException(String msg) {
        super(msg);
    }

    public ForwardedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
