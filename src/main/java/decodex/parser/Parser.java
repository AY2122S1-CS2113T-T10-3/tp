package decodex.parser;

import decodex.commands.Command;
import decodex.commands.HelpCommand;
import decodex.commands.ExitCommand;
import decodex.commands.InputCommand;
import decodex.commands.ListCommand;
import decodex.commands.ResetCommand;
import decodex.commands.SelectCommand;
import decodex.data.exception.ParserException;
import decodex.ui.messages.ErrorMessages;
import java.util.Arrays;

/**
 * The Parser class handles the parsing and validation of the user input.
 */
public class Parser {

    /**
     * Specifies the index of the tokens where specific parameters can be found.
     */
    private static final int COMMAND_INDEX = 0;
    private static final int MODULE_NAME_INDEX_IN_TOKENS = 0;

    /**
     * Specifies the starting index where the arguments can be found.
     */
    private static final int STARTING_ARGUMENTS_INDEX = 1;

    /**
     * Specifies the valid length of the tokens and used to check validity of tokens.
     */
    private static final int VALID_TOKENS_LENGTH_FOR_COMMAND = 1;

    /**
     * Returns the type of command that the user has specified.
     *
     * @param userInput The input specified by the user.
     * @return The type of the command.
     * @throws ParserException If the user input is empty or missing the command type.
     */
    public String getCommandType(String userInput) throws ParserException {
        String strippedUserInput = userInput.stripLeading();
        if (strippedUserInput.isEmpty()) {
            throw new ParserException(ErrorMessages.MISSING_COMMAND_TYPE);
        }

        String[] tokens = strippedUserInput.split(" ");

        boolean isInvalidTokensLength = tokens.length < VALID_TOKENS_LENGTH_FOR_COMMAND;

        if (isInvalidTokensLength) {
            throw new ParserException(ErrorMessages.MISSING_COMMAND_TYPE);
        }

        String commandType = tokens[COMMAND_INDEX];
        assert !commandType.isEmpty() : "Command Type should not be empty";
        return commandType;
    }

    /**
     * Returns the name of the module that the user has specified.
     *
     * @param userInput The input specified by the user.
     * @return The name of the module.
     * @throws ParserException If the user input is missing the module name.
     */
    public String getModuleName(String userInput) throws ParserException {
        String strippedUserInput = userInput.stripLeading();
        String[] tokens = strippedUserInput.split(" ", -1);

        String[] argumentTokens = Arrays.stream(Arrays.copyOfRange(tokens, STARTING_ARGUMENTS_INDEX, tokens.length))
                .filter(value -> !value.isBlank())
                .toArray(size -> new String[size]);
        if (argumentTokens.length == 0) {
            throw new ParserException(ErrorMessages.MISSING_MODULE_NAME);
        }
        return argumentTokens[MODULE_NAME_INDEX_IN_TOKENS];
    }

    /**
     * Returns the InputCommand's argument string that the user has specified.
     *
     * @param userInput The input specified by the user.
     * @return The argument string.
     * @throws ParserException If the argument string is empty.
     */
    public String getInputString(String userInput) throws ParserException {
        String strippedUserInput = userInput.stripLeading();
        String[] tokens = strippedUserInput.split(" ", -1);

        String argumentString = String.join(" ",
                Arrays.copyOfRange(tokens, STARTING_ARGUMENTS_INDEX, tokens.length));
        if (argumentString.isEmpty()) {
            throw new ParserException(ErrorMessages.INPUT_EMPTY);
        }
        return argumentString;
    }

    /**
     * Parses the user input specified by the user and returns its corresponding Command.
     *
     * @param userInput The user input specified by the user.
     * @return The corresponding Command object.
     * @throws ParserException If the command type is unknown or invalid.
     */
    public Command parseCommand(String userInput) throws ParserException {
        Command command;
        String commandType = getCommandType(userInput);

        switch (commandType) {
        case ExitCommand.COMMAND_WORD:
            command = craftExitCommand();
            break;
        case InputCommand.COMMAND_WORD:
            command = craftInputCommand(userInput);
            break;
        case ListCommand.COMMAND_WORD:
            command = craftListCommand();
            break;
        case ResetCommand.COMMAND_WORD:
            command = craftResetCommand();
            break;
        case SelectCommand.COMMAND_WORD:
            command = craftSelectCommand(userInput);
            break;
        case HelpCommand.COMMAND_WORD:
            command = craftHelpCommand();
            break;
        default:
            throw new ParserException(ErrorMessages.UNKNOWN_COMMAND);
        }
        return command;
    }

    /**
     * Prepares and returns the ExitCommand.
     *
     * @return The ExitCommand object.
     */
    private ExitCommand craftExitCommand() {
        return new ExitCommand();
    }

    /**
     * Prepares and returns the InputCommand using the specified user input.
     *
     * @param userInput The user input specified by the user.
     * @return The InputCommand object.
     * @throws ParserException If the argument string is empty.
     */
    private InputCommand craftInputCommand(String userInput) throws ParserException {
        String inputData = getInputString(userInput);
        return new InputCommand(inputData);
    }

    /**
     * Prepares and returns the ListCommand.
     *
     * @return The ListCommand object.
     */
    private ListCommand craftListCommand() {
        return new ListCommand();
    }

    /**
     * Prepares and returns the ResetCommand.
     *
     * @return The ResetCommand object.
     */
    private ResetCommand craftResetCommand() {
        return new ResetCommand();
    }

    /**
     * Prepares and returns the SelectCommand using the specified user input.
     *
     * @param userInput The user input specified by the user.
     * @return The SelectCommand object.
     * @throws ParserException If the user input is missing the module name.
     */
    private SelectCommand craftSelectCommand(String userInput) throws ParserException {
        String moduleName = getModuleName(userInput);
        return new SelectCommand(moduleName);
    }

    /**
     * Prepares and returns the HelpCommand.
     *
     * @return The HelpCommand object.
     */
    private HelpCommand craftHelpCommand() {
        return new HelpCommand();
    }
}
