package persistence.exceptions;

/**
 * Custom exception to raise when something goes wrong in the persistence layer.
 *
 * <p>This is meant to abstract any possible exceptions that raise naturally depending on the underlying technology used to persist data.
 *
 * @author DPOO 20-21 Team
 * @version 1.0
 */
public class PersistenceException extends Exception {

    /**
     * Constructor with parameters for a custom persistence exception caused by another one.
     *
     * @param message a string representation of the reason why the exception was raised (which is saved for later retrieval by the {@link #getMessage()} method)
     * @param cause the throwable (usually an exception) that caused this one to be raised (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public PersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
