package decodex.commands.recipe;

import java.io.IOException;

import decodex.commands.Command;
import decodex.data.DataManager;
import decodex.data.exception.RecipeManagerException;
import decodex.data.exception.StorageException;
import decodex.modules.ModuleManager;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;

// @@author rizemon
public class RecipeResetCommand extends Command {

    public static final String COMMAND_WORD = "reset";
    public static final String COMMAND_HELP_MESSAGE = "Clear all modules from recipe\n"
            + "Syntax: recipe reset";

    public RecipeResetCommand() {
        super();
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage) throws RecipeManagerException, IOException {
        Recipe editingRecipe = recipeManager.getEditingRecipe();
        recipeManager.resetEditedRecipe();
        try {
            storage.saveRecipeToFile(editingRecipe);
        } catch (IOException | StorageException err) {
            ui.printError(err);
        }
        ui.printRecipeReset(editingRecipe.getName());
    }
}
