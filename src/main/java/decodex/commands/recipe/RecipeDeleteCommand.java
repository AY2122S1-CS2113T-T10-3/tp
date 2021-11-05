package decodex.commands.recipe;

import java.io.IOException;

import decodex.commands.Command;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.RecipeManagerException;
import decodex.data.exception.StorageException;
import decodex.modules.ModuleManager;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;
import decodex.ui.messages.ErrorMessages;

public class RecipeDeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_HELP_MESSAGE = "Delete recipe\n"
            + "Syntax: recipe delete <recipeName>";

    private final String recipeName;

    public RecipeDeleteCommand(String recipeName) {
        super();
        this.recipeName = recipeName;
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage)
            throws CommandException, ModuleException, RecipeManagerException, IOException {
        if (recipeName.isBlank()) {
            throw new CommandException(ErrorMessages.MISSING_RECIPE_NAME);
        }

        recipeManager.removeRecipe(recipeName);
        try {
            storage.deleteRecipeFile(recipeName);
        } catch (IOException | StorageException err) {
            ui.printError(err);
        }
        ui.printRecipeDeleted(recipeName);
    }
}
