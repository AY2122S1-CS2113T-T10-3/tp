package decodex.data.exception;

public class ParserException extends Exception {

    public static final String MISSING_COMMAND_ARGS_MESSAGE = "[-] Your command arguments is empty";
    public static final String MISSING_COMMAND_TYPE_MESSAGE = "[-] Your command cannot be blank";

    public ParserException(String message) {
        super(message);
    }
}
