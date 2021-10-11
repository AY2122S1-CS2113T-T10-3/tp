package decodex.parser;

import decodex.commands.Command;
import decodex.commands.DataCommand;
import decodex.commands.ExitCommand;
import decodex.commands.ListCommand;
import decodex.commands.ResetCommand;
import decodex.commands.SelectCommand;
import decodex.data.exception.ParserException;

import java.util.Arrays;

/**
 * Parses and validates the user input.
 */
public class Parser {

    /**
     * Specifies the index of the tokens where specific parameters can be found.
     */
    private static final int COMMAND_INDEX = 0;


    /**
     * Specifies the starting index where the arguments can be found.
     */
    private static final int STARTING_ARGUMENTS_INDEX = 1;

    /**
     * Specifies the valid length of the tokens and used to check validity of tokens.
     */
    private static final int VALID_TOKENS_LENGTH_FOR_COMMAND = 1;
    private static final int VALID_TOKENS_LENGTH_FOR_ARGUMENTS = 2;

    /**
     * Returns the type of command that user has entered.
     *
     * @param userInput The input specified by the user.
     * @return The type of command.
     */
    public String getCommandType(String userInput) throws ParserException {
        String strippedUserInput = userInput.stripLeading();
        if (strippedUserInput.isEmpty()) {
            throw new ParserException(ParserException.MISSING_COMMAND_TYPE_MESSAGE);
        }

        String[] tokens = strippedUserInput.split(" ");

        boolean isInvalidTokensLength = tokens.length < VALID_TOKENS_LENGTH_FOR_COMMAND;

        if (isInvalidTokensLength) {
            throw new ParserException(ParserException.MISSING_COMMAND_TYPE_MESSAGE);
        }
        return tokens[COMMAND_INDEX];
    }

    /**
     * Returns the Argument portion of the user input as a whole string.
     *
     * @param userInput The input specified by the user.
     * @return The argument portion of the user input as an array.
     */
    public String getUserArgument(String userInput) throws ParserException {
        String strippedUserInput = userInput.stripLeading();
        String[] tokens = strippedUserInput.split(" ", -1);

        if (tokens.length < VALID_TOKENS_LENGTH_FOR_ARGUMENTS) {
            throw new ParserException(ParserException.MISSING_COMMAND_ARGS_MESSAGE);
        }
        String[] argumentTokens = Arrays.copyOfRange(tokens, STARTING_ARGUMENTS_INDEX, tokens.length);

        if (argumentTokens.length > 1) {
            String joinedArguments = String.join(" ", argumentTokens);
            return joinedArguments;
        }
        String singleArgument = argumentTokens[0];
        return singleArgument;
    }

    /**
     * Parses the user input provided by user and returns its respective command. Returns a null object if the command
     * is invalid.
     *
     * @param userInput The user input specified by the user.
     * @return The respective Command object.
     */
    public Command parseCommand(String userInput) throws ParserException {
        Command command;
        String commandType = getCommandType(userInput);

        switch (commandType) {
        case ExitCommand.COMMAND_WORD:
            command = craftExitCommand();
            break;
        case DataCommand.COMMAND_WORD:
            command = craftDataCommand(userInput);
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
        default:
            throw new ParserException("[-] Unknown command, please enter a valid command");
        }
        return command;
    }

    /**
     * Crafts and returns the Exit Command.
     *
     * @return The Exit command.
     */
    private Command craftExitCommand() {
        return new ExitCommand();
    }

    /**
     * Crafts and returns the Data Command given the user input.
     *
     * @param userInput The user input specified by the user.
     * @return The Data command
     * @throws ParserException ParserException
     */
    private Command craftDataCommand(String userInput) throws ParserException {
        String arguments = getUserArgument(userInput);
        return new DataCommand(arguments);
    }

    /**
     * Crafts and returns the List Command.
     *
     * @return The List command.
     */
    private Command craftListCommand() {
        return new ListCommand();
    }

    /**
     * Crafts and returns the Reset Command.
     *
     * @return The Reset command.
     */
    private Command craftResetCommand() {
        return new ResetCommand();
    }

    /**
     * Crafts and returns the Select Command.
     *
     * @param userInput The user input specified by the user.
     * @return The Select command.
     * @throws ParserException ParserException
     */
    private Command craftSelectCommand(String userInput) throws ParserException {
        String moduleName = getUserArgument(userInput);
        return new SelectCommand(moduleName);
    }

}
