package ca.utoronto.ece419.tester.wrapper.exceptions;

/**
 * Exception that is thrown when encountering invoke/newInstance
 */
public class EncounteredException extends RuntimeException{
    public EncounteredException(String msg) {
        super(msg);
    }

    public EncounteredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
