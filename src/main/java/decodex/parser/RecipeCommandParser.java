package decodex.parser;

import java.util.Arrays;

import decodex.commands.Command;
import decodex.commands.recipe.RecipeDeleteCommand;
import decodex.commands.recipe.RecipeDeselectCommand;
import decodex.commands.recipe.RecipeListCommand;
import decodex.commands.recipe.RecipeNewCommand;
import decodex.commands.recipe.RecipePopCommand;
import decodex.commands.recipe.RecipePushCommand;
import decodex.commands.recipe.RecipeResetCommand;
import decodex.commands.recipe.RecipeSelectCommand;
import decodex.data.exception.CommandException;
import decodex.ui.messages.ErrorMessages;

/**
 * The RecipeCommandParser class handles the parsing and validation of recipe commands.
 */
public class RecipeCommandParser extends Parser {

    private static final int SUBCOMMAND_WORD_INDEX = 0;
    private static final int SUBARGUMENT_STARTING_INDEX = 1;
    private static final int MODULE_PARAMETERS_STARTING_INDEX = 2;
    private static final int SUBCOMMAND_NO_ARGUMENT_LENGTH = 1;
    private static final int SUBCOMMAND_WITH_ARGUMENT_LENGTH = 2;

    public RecipeCommandParser() {
    }

    /**
     * Parses the subcommand tokens and returns its corresponding recipe command.
     *
     * @param userInput The user input specified by the user.
     * @return The respective recipe command.
     * @throws CommandException If the number of arguments is invalid.
     */
    public Command parseCommand(String userInput) throws CommandException {
        assert userInput != null : "userInput should not be null";

        String[] tokens = getTokens(userInput);
        String[] subCommandTokens = Arrays.copyOfRange(tokens, STARTING_ARGUMENTS_INDEX, tokens.length);

        // Check minimum subcommand length
        if (subCommandTokens.length < SUBCOMMAND_NO_ARGUMENT_LENGTH) {
            throw new CommandException(ErrorMessages.MISSING_ARGUMENT);
        }

        Command recipeCommand;

        String subCommand = subCommandTokens[SUBCOMMAND_WORD_INDEX];
        switch (subCommand) {
        case RecipeNewCommand.COMMAND_WORD:
            recipeCommand = prepareRecipeNewCommand(subCommandTokens);
            break;
        case RecipeSelectCommand.COMMAND_WORD:
            recipeCommand = prepareRecipeSelectCommand(subCommandTokens);
            break;
        case RecipeDeselectCommand.COMMAND_WORD:
            recipeCommand = prepareRecipeDeselectCommand(subCommandTokens);
            break;
        case RecipePushCommand.COMMAND_WORD:
            recipeCommand = prepareRecipePushCommand(subCommandTokens);
            break;
        case RecipePopCommand.COMMAND_WORD:
            recipeCommand = prepareRecipePopCommand(subCommandTokens);
            break;
        case RecipeResetCommand.COMMAND_WORD:
            recipeCommand = prepareRecipeResetCommand(subCommandTokens);
            break;
        case RecipeListCommand.COMMAND_WORD:
            recipeCommand = prepareRecipeListCommand(subCommandTokens);
            break;
        case RecipeDeleteCommand.COMMAND_WORD:
            recipeCommand = prepareRecipeDeleteCommand(subCommandTokens);
            break;
        default:
            throw new CommandException(ErrorMessages.INVALID_RECIPE_COMMAND);
        }

        return recipeCommand;
    }

    /**
     * Checks the number of arguments is valid.
     *
     * @param subCommandTokens  The list of recipe subcommand tokens.
     * @param numberOfArguments The valid number of arguments.
     * @throws CommandException If the number of arguments is invalid.
     */
    private void checkValidNumberOfArguments(String[] subCommandTokens, int numberOfArguments)
            throws CommandException {
        if (subCommandTokens.length > numberOfArguments) {
            throw new CommandException(ErrorMessages.TOO_MANY_COMMAND_ARGUMENTS);
        } else if (subCommandTokens.length < numberOfArguments) {
            throw new CommandException(ErrorMessages.TOO_FEW_COMMAND_ARGUMENTS);
        }
    }

    /**
     * Prepares and returns the RecipeNewCommand.
     *
     * @param subCommandTokens The list of recipe subcommand tokens.
     * @return The RecipeNewCommand object.
     * @throws CommandException If the number of arguments is invalid.
     */
    private RecipeNewCommand prepareRecipeNewCommand(String[] subCommandTokens) throws CommandException {
        checkValidNumberOfArguments(subCommandTokens, SUBCOMMAND_WITH_ARGUMENT_LENGTH);
        String recipeName = subCommandTokens[SUBARGUMENT_STARTING_INDEX];
        return new RecipeNewCommand(recipeName);
    }

    /**
     * Prepares and returns the RecipeSelectCommand.
     *
     * @param subCommandTokens The list of recipe subcommand tokens.
     * @return The RecipeSelectCommand object.
     * @throws CommandException If the number of arguments is invalid.
     */
    private RecipeSelectCommand prepareRecipeSelectCommand(String[] subCommandTokens) throws CommandException {
        checkValidNumberOfArguments(subCommandTokens, SUBCOMMAND_WITH_ARGUMENT_LENGTH);
        String recipeName = subCommandTokens[SUBARGUMENT_STARTING_INDEX];
        return new RecipeSelectCommand(recipeName);
    }

    /**
     * Prepares and returns the RecipeDeselectCommand.
     *
     * @param subCommandTokens The list of recipe subcommand tokens.
     * @return The RecipeDeselectCommand object.
     * @throws CommandException If the number of arguments is invalid.
     */
    private RecipeDeselectCommand prepareRecipeDeselectCommand(String[] subCommandTokens) throws CommandException {
        checkValidNumberOfArguments(subCommandTokens, SUBCOMMAND_NO_ARGUMENT_LENGTH);
        return new RecipeDeselectCommand();
    }

    /**
     * Prepares and returns the RecipePushCommand.
     *
     * @param subCommandTokens The list of recipe subcommand tokens.
     * @return The RecipePushCommand object.
     * @throws CommandException If the number of arguments is invalid.
     */
    private RecipePushCommand prepareRecipePushCommand(String[] subCommandTokens) throws CommandException {
        if (subCommandTokens.length < SUBCOMMAND_WITH_ARGUMENT_LENGTH) {
            throw new CommandException(ErrorMessages.MISSING_ARGUMENT);
        }

        String moduleName = subCommandTokens[SUBARGUMENT_STARTING_INDEX];
        String[] parameters = Arrays.copyOfRange(subCommandTokens, MODULE_PARAMETERS_STARTING_INDEX,
                subCommandTokens.length);
        return new RecipePushCommand(moduleName, parameters);
    }

    /**
     * Prepares and returns the RecipePopCommand.
     *
     * @param subCommandTokens The list of recipe subcommand tokens.
     * @return The RecipePopCommand object.
     * @throws CommandException If the number of arguments is invalid.
     */
    private RecipePopCommand prepareRecipePopCommand(String[] subCommandTokens) throws CommandException {
        checkValidNumberOfArguments(subCommandTokens, SUBCOMMAND_NO_ARGUMENT_LENGTH);
        return new RecipePopCommand();
    }

    /**
     * Prepares and returns the RecipeResetCommand.
     *
     * @param subCommandTokens The list of recipe subcommand tokens.
     * @return The RecipeResetCommand object.
     * @throws CommandException If the number of arguments is invalid.
     */
    private RecipeResetCommand prepareRecipeResetCommand(String[] subCommandTokens) throws CommandException {
        checkValidNumberOfArguments(subCommandTokens, SUBCOMMAND_NO_ARGUMENT_LENGTH);
        return new RecipeResetCommand();
    }

    /**
     * Prepares and returns the RecipeDeleteCommand.
     *
     * @param subCommandTokens The list of recipe subcommand tokens.
     * @return The RecipeDeleteCommand object.
     * @throws CommandException If the number of arguments is invalid.
     */
    private RecipeDeleteCommand prepareRecipeDeleteCommand(String[] subCommandTokens) throws CommandException {
        checkValidNumberOfArguments(subCommandTokens, SUBCOMMAND_WITH_ARGUMENT_LENGTH);
        String recipeName = subCommandTokens[SUBARGUMENT_STARTING_INDEX];
        return new RecipeDeleteCommand(recipeName);
    }

    /**
     * Prepares and returns the RecipeListCommand.
     *
     * @param subCommandTokens The list of recipe subcommand tokens.
     * @return The RecipeListCommand object.
     * @throws CommandException If the number of arguments is invalid.
     */
    private RecipeListCommand prepareRecipeListCommand(String[] subCommandTokens) throws CommandException {
        if (subCommandTokens.length == SUBCOMMAND_NO_ARGUMENT_LENGTH) {
            return new RecipeListCommand("");
        }
        if (subCommandTokens.length == SUBCOMMAND_WITH_ARGUMENT_LENGTH) {
            String recipeName = subCommandTokens[SUBARGUMENT_STARTING_INDEX];
            return new RecipeListCommand(recipeName);
        }
        throw new CommandException(ErrorMessages.TOO_MANY_COMMAND_ARGUMENTS);
    }
}
