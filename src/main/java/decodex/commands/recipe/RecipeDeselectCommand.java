package decodex.commands.recipe;

import java.io.IOException;

import decodex.logic.Command;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.DataManagerException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.ModuleManagerException;
import decodex.data.exception.RecipeException;
import decodex.data.exception.RecipeManagerException;
import decodex.modules.ModuleManager;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;

public class RecipeDeselectCommand extends Command {

    public static final String COMMAND_WORD = "deselect";
    public static final String COMMAND_HELP_MESSAGE = "Deselects the recipe currently being edited\n"
            + "Syntax: recipe deselect";

    public RecipeDeselectCommand() {
        super();
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage) throws CommandException, ModuleManagerException, DataManagerException, ModuleException,
            RecipeException, RecipeManagerException, IOException {
        Recipe editingRecipe = recipeManager.getEditingRecipe();
        String recipeName = editingRecipe.getName();
        recipeManager.deselectEditingRecipe();
        ui.printRecipeDeselected(recipeName);
    }
}
