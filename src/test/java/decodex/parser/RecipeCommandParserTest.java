package decodex.parser;

import decodex.logic.Command;
import decodex.logic.commands.recipe.RecipeDeleteCommand;
import decodex.logic.commands.recipe.RecipeNewCommand;
import decodex.commands.recipe.RecipePopCommand;
import decodex.commands.recipe.RecipePushCommand;
import decodex.commands.recipe.RecipeResetCommand;
import decodex.commands.recipe.RecipeSelectCommand;
import decodex.data.exception.CommandException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeCommandParserTest {

    RecipeCommandParser recipeCommandParser;

    @BeforeEach
    public void setupRecipeCommandParser() {
        recipeCommandParser = new RecipeCommandParser();
    }

    @Test
    public void parseCommand_validRecipeNew_expectRecipeNewCommand() throws CommandException {
        String userInput = "recipe new testRecipe";
        Command recipeCommand = recipeCommandParser.parseCommand(userInput);
        assertTrue(recipeCommand instanceof RecipeNewCommand);
    }

    @Test
    public void parseCommand_recipeNewWithoutRecipeName_expectCommandExceptionThrown() {
        String userInput = "recipe new";
        assertThrows(CommandException.class, () -> recipeCommandParser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_recipeNewAdditionalArguments_expectCommandExceptionThrown() {
        String userInput = "recipe new testRecipe 1";
        assertThrows(CommandException.class, () -> recipeCommandParser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_validRecipeSelect_expectRecipeSelectCommand() throws CommandException {
        String userInput = "recipe select testRecipe";
        Command recipeCommand = recipeCommandParser.parseCommand(userInput);
        assertTrue(recipeCommand instanceof RecipeSelectCommand);
    }

    @Test
    public void parseCommand_recipeSelectWithoutRecipeName_expectCommandExceptionThrown() {
        String userInput = "recipe select";
        assertThrows(CommandException.class, () -> recipeCommandParser.parseCommand(userInput));
    }


    @Test
    public void parseCommand_recipeSelectAdditionalArguments_expectCommandExceptionThrown() {
        String userInput = "recipe select testRecipe 1";
        assertThrows(CommandException.class, () -> recipeCommandParser.parseCommand(userInput));
    }


    @Test
    public void parseCommand_validRecipePush_expectRecipePushCommand() throws CommandException {
        String userInput = "recipe push base64encode";
        Command recipeCommand = recipeCommandParser.parseCommand(userInput);
        assertTrue(recipeCommand instanceof RecipePushCommand);
    }

    @Test
    public void parseCommand_validRecipePushWithParameters_expectRecipePushCommand() throws CommandException {
        String userInput = "recipe push rotencode 13";
        Command recipeCommand = recipeCommandParser.parseCommand(userInput);
        assertTrue(recipeCommand instanceof RecipePushCommand);
    }

    @Test
    public void parseCommand_recipePushWithoutModuleName_expectCommandExceptionThrown() {
        String userInput = "recipe push";
        assertThrows(CommandException.class, () -> recipeCommandParser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_validRecipePop_expectRecipePopCommand() throws CommandException {
        String userInput = "recipe pop";
        Command recipeCommand = recipeCommandParser.parseCommand(userInput);
        assertTrue(recipeCommand instanceof RecipePopCommand);
    }

    @Test
    public void parseCommand_recipePopAdditionalArguments_expectCommandExceptionThrown() {
        String userInput = "recipe pop 1";
        assertThrows(CommandException.class, () -> recipeCommandParser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_validRecipeReset_expectRecipeResetCommand() throws CommandException {
        String userInput = "recipe reset";
        Command recipeCommand = recipeCommandParser.parseCommand(userInput);
        assertTrue(recipeCommand instanceof RecipeResetCommand);
    }

    @Test
    public void parseCommand_recipeResetAdditionalArguments_expectCommandExceptionThrown() {
        String userInput = "recipe reset 1";
        assertThrows(CommandException.class, () -> recipeCommandParser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_validRecipeDelete_expectRecipeDeleteCommand() throws CommandException {
        String userInput = "recipe delete testRecipe";
        Command recipeCommand = recipeCommandParser.parseCommand(userInput);
        assertTrue(recipeCommand instanceof RecipeDeleteCommand);
    }

    @Test
    public void parseCommand_recipeDeleteNoRecipeName_expectCommandExceptionThrown() {
        String userInput = "recipe delete";
        assertThrows(CommandException.class, () -> recipeCommandParser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_recipeDeleteAdditionalArguments_expectCommandExceptionThrown() {
        String userInput = "recipe delete testRecipe 1";
        assertThrows(CommandException.class, () -> recipeCommandParser.parseCommand(userInput));
    }
}
