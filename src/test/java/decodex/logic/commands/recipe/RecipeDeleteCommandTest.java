package decodex.logic.commands.recipe;

import static org.junit.jupiter.api.Assertions.assertThrows;

import decodex.logic.commands.recipe.RecipeDeleteCommand;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.DataManagerException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.ModuleManagerException;
import decodex.data.exception.RecipeException;
import decodex.data.exception.RecipeManagerException;
import decodex.logic.Command;
import decodex.modules.ModuleManager;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class RecipeDeleteCommandTest {

    @Test
    void run_deleteValidRecipe_recipeDeleted() throws CommandException, RecipeException, ModuleException,
            RecipeManagerException, DataManagerException, ModuleManagerException, IOException {
        String testRecipeName = "BaconPancakes";

        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Ui ui = new Ui();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();

        recipeManager.addRecipe(new Recipe(testRecipeName));

        Command recipeDeleteCommand = new RecipeDeleteCommand(testRecipeName);
        recipeDeleteCommand.run(dataManager, moduleManager, ui, recipeManager, storage);

        assertThrows(RecipeManagerException.class, () -> recipeManager.getRecipe(testRecipeName));
    }

    @Test
    void run_deleteInvalidRecipe_expectException() throws RecipeException, RecipeManagerException, IOException {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Ui ui = new Ui();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();

        recipeManager.addRecipe(new Recipe("BaconPancakes"));

        String testRecipeName = "IDoNotExist";
        Command recipeDeleteCommand = new RecipeDeleteCommand(testRecipeName);

        assertThrows(RecipeManagerException.class,
            () -> recipeDeleteCommand.run(dataManager, moduleManager, ui, recipeManager, storage));
    }
}
