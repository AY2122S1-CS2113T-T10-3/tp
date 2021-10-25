package decodex.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.RecipeManagerException;
import decodex.modules.ModuleManager;
import decodex.modules.base64.Base64Encoder;
import decodex.modules.binary.BinaryEncoder;
import decodex.modules.hex.HexEncoder;
import decodex.modules.rot.RotEncoder;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.ui.Ui;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeListCommandTest {

    private static final String TEST_RECIPE_NAME = "testRecipe";

    private ByteArrayOutputStream outputStream;
    private final PrintStream originalOutputStream = System.out;

    private final DataManager dataManager = new DataManager();
    private final ModuleManager moduleManager = new ModuleManager();
    private final RecipeManager recipeManager = new RecipeManager();
    private final Ui ui = new Ui();

    @BeforeEach
    public void createTestRecipe() throws RecipeManagerException {
        Recipe recipe = new Recipe(TEST_RECIPE_NAME);

        recipe.push(new HexEncoder());
        recipe.push(new Base64Encoder());
        recipe.push(new RotEncoder(5));
        recipe.push(new BinaryEncoder());

        recipeManager.addRecipe(recipe);
    }

    @BeforeEach
    public void setOutputStream() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreOutputStream() {
        System.setOut(originalOutputStream);
    }

    @Test
    void run_listRecipeName_success() throws RecipeManagerException, CommandException, ModuleException {
        RecipeListCommand testCommand = new RecipeListCommand(TEST_RECIPE_NAME);
        testCommand.run(dataManager, moduleManager, ui, recipeManager);
        assertFalse(outputStream.toString().isBlank());
    }

    @Test
    void run_listBlankNoEditingRecipe_expectException() {
        RecipeListCommand testCommand = new RecipeListCommand("");
        assertThrows(RecipeManagerException.class,
            () -> testCommand.run(dataManager, moduleManager, ui, recipeManager));
    }

    @Test
    void run_listBlankHasEditingRecipe_success() throws RecipeManagerException, CommandException, ModuleException {
        recipeManager.selectRecipeForEditing(TEST_RECIPE_NAME);
        RecipeListCommand testCommand = new RecipeListCommand("");
        testCommand.run(dataManager, moduleManager, ui, recipeManager);
        assertFalse(outputStream.toString().isBlank());
    }

    @Test
    void run_listUnknownRecipe_expectException() {
        RecipeListCommand testCommand = new RecipeListCommand("unknownRecipe");
        assertThrows(RecipeManagerException.class,
            () -> testCommand.run(dataManager, moduleManager, ui, recipeManager));
    }

    @Test
    void run_listEmptyRecipe_expectException() throws RecipeManagerException, CommandException, ModuleException {
        recipeManager.getRecipe(TEST_RECIPE_NAME).reset();
        RecipeListCommand testCommand = new RecipeListCommand(TEST_RECIPE_NAME);
        testCommand.run(dataManager, moduleManager, ui, recipeManager);
        assertFalse(outputStream.toString().isBlank());
    }
}
