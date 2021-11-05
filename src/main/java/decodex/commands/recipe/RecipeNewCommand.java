package decodex.commands.recipe;

import java.io.IOException;

import decodex.commands.Command;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.RecipeException;
import decodex.data.exception.RecipeManagerException;
import decodex.data.exception.StorageException;
import decodex.modules.ModuleManager;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;
import decodex.ui.messages.ErrorMessages;

public class RecipeNewCommand extends Command {

    public static final String COMMAND_WORD = "new";
    public static final String COMMAND_HELP_MESSAGE = "Create new recipe\n"
            + "Syntax: recipe new <recipeName>";

    private final String recipeName;

    public RecipeNewCommand(String recipeName) {
        super();
        this.recipeName = recipeName;
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage)
            throws CommandException, ModuleException, RecipeException, RecipeManagerException {
        if (recipeName.isBlank()) {
            throw new CommandException(ErrorMessages.MISSING_RECIPE_NAME);
        }

        Recipe recipe = new Recipe(recipeName);

        recipeManager.addRecipe(recipe);
        try {
            storage.saveRecipeToFile(recipe);
        } catch (IOException | StorageException err) {
            ui.printError(err);
        }
        recipeManager.selectRecipeForEditing(recipeName);

        ui.printNewRecipeCreated(recipe.getName());
    }
}
