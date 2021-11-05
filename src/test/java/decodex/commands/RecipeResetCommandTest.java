package decodex.commands;

import java.io.IOException;

import decodex.commands.recipe.RecipeResetCommand;
import decodex.data.DataManager;
import decodex.data.exception.ModuleException;
import decodex.data.exception.ModuleManagerException;
import decodex.data.exception.RecipeException;
import decodex.data.exception.RecipeManagerException;
import decodex.modules.Module;
import decodex.modules.ModuleManager;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

// @@author rizemon
class RecipeResetCommandTest {

    @Test
    public void run_oneModuleInEditingRecipe_recipeSizeIsZero() throws RecipeException, RecipeManagerException,
            ModuleManagerException, ModuleException, IOException {
        RecipeManager recipeManager = new RecipeManager();
        String testRecipeName = "test";
        Recipe testRecipe = new Recipe(testRecipeName);
        recipeManager.addRecipe(testRecipe);
        recipeManager.selectRecipeForEditing(testRecipeName);

        ModuleManager moduleManager = new ModuleManager();
        String moduleName = "base64encode";
        String[] parameters = {};
        Module module = moduleManager.selectModule(moduleName, parameters);
        testRecipe.push(module);

        RecipeResetCommand testCommand = new RecipeResetCommand();
        DataManager dataManager = new DataManager();
        Ui ui = new Ui();
        Storage storage = new Storage();
        testCommand.run(dataManager, moduleManager, ui, recipeManager, storage);

        assertEquals(recipeManager.getEditingRecipe().getModuleList().size(), 0);
    }
}
