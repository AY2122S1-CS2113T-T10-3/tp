package decodex.commands;

import java.io.IOException;

import decodex.commands.recipe.RecipeSelectCommand;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.RecipeException;
import decodex.data.exception.RecipeManagerException;
import decodex.modules.ModuleManager;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// @@author rizemon
class RecipeSelectCommandTest {

    @Test
    public void run_selectExistingRecipe_existingRecipe() throws RecipeException, RecipeManagerException,
            CommandException, IOException {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        Ui ui = new Ui();

        String testRecipeName = "test";
        Recipe testRecipe = new Recipe(testRecipeName);
        recipeManager.addRecipe(testRecipe);

        RecipeSelectCommand testCommand = new RecipeSelectCommand(testRecipeName);
        testCommand.run(dataManager, moduleManager, ui, recipeManager, storage);
        assertEquals(recipeManager.getEditingRecipe().getName(), testRecipeName);
    }

    @Test
    public void run_selectNonExistentRecipe_expectException() {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        Ui ui = new Ui();

        String testRecipeName = "test";

        RecipeSelectCommand testCommand = new RecipeSelectCommand(testRecipeName);
        assertThrows(RecipeManagerException.class,
            () -> testCommand.run(dataManager, moduleManager, ui, recipeManager, storage));
    }

    @Test
    public void run_nullRecipe_expectException() {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        Ui ui = new Ui();

        String testRecipeName = "";

        RecipeSelectCommand testCommand = new RecipeSelectCommand(testRecipeName);
        assertThrows(CommandException.class,
            () -> testCommand.run(dataManager, moduleManager, ui, recipeManager, storage));
    }
}
