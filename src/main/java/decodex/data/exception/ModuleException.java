package decodex.data.exception;

/**
 * This exception is raised when an error during module execution occurs.
 */
public class ModuleException extends Exception {

    public static final String BASE64_DECODING_FAILED_MESSAGE = "[x] "
            + "This data does not seem to be in Base64 format";

    public ModuleException(String message) {
        super(message);
    }
}
