package decodex.parser;

import java.util.Arrays;

import decodex.logic.Command;
import decodex.logic.commands.ExitCommand;
import decodex.commands.HelpCommand;
import decodex.commands.InputCommand;
import decodex.commands.ListCommand;
import decodex.commands.ResetCommand;
import decodex.commands.SelectCommand;
import decodex.commands.ShowCommand;
import decodex.data.exception.CommandException;
import decodex.data.exception.ParserException;
import decodex.ui.messages.ErrorMessages;

/**
 * The Parser class handles the parsing and validation of the user input.
 */
public class Parser {

    // Specifies the index of the tokens where specific parameters can be found.
    private static final int COMMAND_INDEX = 0;

    // Specifies the starting index where the arguments can be found.
    protected static final int STARTING_ARGUMENTS_INDEX = 1;

    // Specifies the tokens used to split the user input by.
    private static final String SPLIT_REGEX = "\\s+";
    private static final int SPLIT_LIMIT_VALUE = -1;

    // Specifies the separator used to reconstruct a string from a list of tokens.
    private static final String SPACE_SEPARATOR = " ";

    // Specifies the valid length of the tokens and used to check validity of tokens.
    private static final int VALID_TOKENS_LENGTH_FOR_COMMAND = 1;

    // Specifies the number of tokens for list command with no category and with a category.
    private static final int LIST_COMMAND_LENGTH_NO_CATEGORY = 1;
    private static final int LIST_COMMAND_LENGTH_HAS_CATEGORY = 2;
    private static final int LIST_COMMAND_CATEGORY_STARTING_INDEX = 1;

    // Specifies the valid number of tokens and indexes for category and parameters for select command.
    private static final int VALID_TOKENS_LENGTH_FOR_SELECT_COMMAND = 3;
    private static final int SELECT_CATEGORY_INDEX = 1;
    private static final int SELECT_ITEM_NAME_INDEX = 2;
    private static final int SELECT_PARAMETERS_START_INDEX = 3;

    // Specifies the keyword for recipe related commands.
    private static final String RECIPE_COMMAND_WORD = "recipe";

    // Specifies the number of tokens for commands.
    private static final int EXIT_COMMAND_LENGTH = 1;
    private static final int SHOW_COMMAND_LENGTH = 1;
    private static final int HELP_COMMAND_LENGTH = 1;

    // Initializes the RecipeCommandParser for parsing recipe commands.
    private static final RecipeCommandParser recipeCommandParser = new RecipeCommandParser();

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

        String[] tokens = strippedUserInput.split(SPLIT_REGEX);

        boolean isInvalidTokensLength = tokens.length < VALID_TOKENS_LENGTH_FOR_COMMAND;

        if (isInvalidTokensLength) {
            throw new ParserException(ErrorMessages.MISSING_COMMAND_TYPE);
        }

        String commandType = tokens[COMMAND_INDEX];
        assert !commandType.isEmpty() : "Command Type should not be empty";
        return commandType;
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
        String[] tokens = strippedUserInput.split(SPLIT_REGEX, SPLIT_LIMIT_VALUE);

        String[] argumentArray = Arrays.copyOfRange(tokens, STARTING_ARGUMENTS_INDEX, tokens.length);
        String argumentString = String.join(SPACE_SEPARATOR, argumentArray);
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
     * @throws ParserException  If the command type is unknown or invalid.
     * @throws CommandException If the command has invalid number of arguments.
     */
    public Command parseCommand(String userInput) throws ParserException, CommandException {
        Command command;
        String commandType = getCommandType(userInput);

        switch (commandType) {
        case ExitCommand.COMMAND_WORD:
            command = prepareExitCommand(userInput);
            break;
        case InputCommand.COMMAND_WORD:
            command = prepareInputCommand(userInput);
            break;
        case ListCommand.COMMAND_WORD:
            command = prepareListCommand(userInput);
            break;
        case ResetCommand.COMMAND_WORD:
            command = prepareResetCommand();
            break;
        case SelectCommand.COMMAND_WORD:
            command = prepareSelectCommand(userInput);
            break;
        case HelpCommand.COMMAND_WORD:
            command = prepareHelpCommand(userInput);
            break;
        case ShowCommand.COMMAND_WORD:
            command = prepareShowCommand(userInput);
            break;
        case RECIPE_COMMAND_WORD:
            command = prepareRecipeSubcommands(userInput);
            break;
        default:
            throw new ParserException(ErrorMessages.UNKNOWN_COMMAND);
        }
        return command;
    }

    /**
     * Splits the user input by an arbitrary number of spaces into a list of tokens.
     *
     * @param userInput The user input specified by the user.
     * @return A list of tokens.
     */
    protected String[] getTokens(String userInput) {
        String strippedUserInput = userInput.stripLeading();
        return strippedUserInput.split(SPLIT_REGEX);
    }

    /**
     * Prepares and returns the ExitCommand.
     *
     * @param userInput The user input specified by the user.
     * @return The ExitCommand object.
     * @throws CommandException If the command has any arguments.
     */
    private ExitCommand prepareExitCommand(String userInput) throws CommandException {
        String[] tokens = getTokens(userInput);

        if (tokens.length != EXIT_COMMAND_LENGTH) {
            throw new CommandException(ErrorMessages.TOO_MANY_COMMAND_ARGUMENTS);
        }
        return new ExitCommand();
    }

    /**
     * Prepares and returns the InputCommand using the specified user input.
     *
     * @param userInput The user input specified by the user.
     * @return The InputCommand object.
     * @throws ParserException If the argument string is empty.
     */
    private InputCommand prepareInputCommand(String userInput) throws ParserException {
        String inputData = getInputString(userInput);
        return new InputCommand(inputData);
    }

    /**
     * Returns the list category for the list command.
     *
     * @param userInput The user input specified by the user.
     * @return The list category String.
     * @throws CommandException If the command has more than 1 argument.
     */
    private String getListCategory(String userInput) throws CommandException {
        String[] tokens = getTokens(userInput);

        if (tokens.length == LIST_COMMAND_LENGTH_NO_CATEGORY) {
            return null;
        } else if (tokens.length == LIST_COMMAND_LENGTH_HAS_CATEGORY) {
            return tokens[LIST_COMMAND_CATEGORY_STARTING_INDEX];
        } else {
            throw new CommandException(ErrorMessages.TOO_MANY_COMMAND_ARGUMENTS);
        }
    }

    /**
     * Prepares and returns the ListCommand.
     *
     * @return The ListCommand object.
     * @throws CommandException If the number of command argument is invalid.
     */
    private ListCommand prepareListCommand(String userInput) throws CommandException {
        String listCategory = getListCategory(userInput);
        if (listCategory != null) {
            listCategory = listCategory.strip();
        }
        return new ListCommand(listCategory);
    }

    /**
     * Prepares and returns the ResetCommand.
     *
     * @return The ResetCommand object.
     */
    private ResetCommand prepareResetCommand() {
        return new ResetCommand();
    }

    /**
     * Prepares and returns the SelectCommand using the specified user input.
     *
     * @param userInput The user input specified by the user.
     * @return The SelectCommand object.
     * @throws CommandException If the number of command arguments is invalid.
     */
    private SelectCommand prepareSelectCommand(String userInput) throws CommandException {
        String[] tokens = getTokens(userInput);
        if (tokens.length < VALID_TOKENS_LENGTH_FOR_SELECT_COMMAND) {
            throw new CommandException(ErrorMessages.MISSING_ARGUMENT);
        }

        String selectCategory = tokens[SELECT_CATEGORY_INDEX];
        String itemName = tokens[SELECT_ITEM_NAME_INDEX];
        String[] parameters = Arrays.copyOfRange(tokens, SELECT_PARAMETERS_START_INDEX, tokens.length);

        return new SelectCommand(selectCategory, itemName, parameters);
    }

    /**
     * Shows the original input data.
     *
     * @return The ShowCommand object.
     * @throws CommandException If the original data input is empty.
     */
    private ShowCommand prepareShowCommand(String userInput) throws CommandException {
        String[] tokens = getTokens(userInput);

        if (tokens.length > SHOW_COMMAND_LENGTH) {
            throw new CommandException(ErrorMessages.TOO_MANY_COMMAND_ARGUMENTS);
        }
        return new ShowCommand();
    }

    /**
     * Prepares and returns recipe subcommands using the specified user input.
     *
     * @param userInput The user input specified by the user.
     * @return The respective recipe command.
     * @throws CommandException If the recipe command is invalid.
     */
    private Command prepareRecipeSubcommands(String userInput) throws CommandException {
        return recipeCommandParser.parseCommand(userInput);
    }

    /**
     * Prepares and returns the HelpCommand.
     *
     * @return The HelpCommand object.
     */
    private HelpCommand prepareHelpCommand(String userInput) throws CommandException {
        String[] tokens = getTokens(userInput);

        if (tokens.length > HELP_COMMAND_LENGTH) {
            throw new CommandException(ErrorMessages.TOO_MANY_COMMAND_ARGUMENTS);
        }
        return new HelpCommand();
    }
}
