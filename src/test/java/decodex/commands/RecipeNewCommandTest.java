package decodex.commands;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import decodex.commands.recipe.RecipeNewCommand;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.DataManagerException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.ModuleManagerException;
import decodex.data.exception.RecipeException;
import decodex.data.exception.RecipeManagerException;
import decodex.modules.ModuleManager;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;

class RecipeNewCommandTest {

    @Test
    void run_createNewRecipe_newRecipeCreated() throws CommandException, RecipeException, ModuleException,
            RecipeManagerException, DataManagerException, ModuleManagerException, IOException {
        String testRecipeName = "BaconPancakes";
        Command newRecipeCommand = new RecipeNewCommand(testRecipeName);

        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Ui ui = new Ui();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();

        newRecipeCommand.run(dataManager, moduleManager, ui, recipeManager, storage);

        assertNotNull(recipeManager.getRecipe("BaconPancakes"));
    }

    @Test
    void run_createNewRecipeWithNoName_expectException() {
        String testRecipeName = "";
        Command newRecipeCommand = new RecipeNewCommand(testRecipeName);

        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Ui ui = new Ui();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();

        assertThrows(CommandException.class,
            () -> newRecipeCommand.run(dataManager, moduleManager, ui, recipeManager, storage));
    }
}
