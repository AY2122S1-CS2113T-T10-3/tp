package decodex.data.exception;

/**
 * This exception is raised when an error during module execution occurs.
 */
public class ModuleException extends Exception {

    public ModuleException(String message) {
        super(message);
    }
}
