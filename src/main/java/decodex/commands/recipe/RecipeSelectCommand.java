package decodex.commands.recipe;

import decodex.logic.Command;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.RecipeManagerException;
import decodex.modules.ModuleManager;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;
import decodex.ui.messages.ErrorMessages;

// @@author rizemon
public class RecipeSelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_HELP_MESSAGE = "Select recipe for editing\n"
            + "Syntax: recipe select <recipeName>";

    private final String recipeName;

    public RecipeSelectCommand(String recipeName) {
        super();
        this.recipeName = recipeName;
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage) throws CommandException, RecipeManagerException {
        if (recipeName.isBlank()) {
            throw new CommandException(ErrorMessages.MISSING_RECIPE_NAME);
        }

        recipeManager.selectRecipeForEditing(recipeName);
        ui.printRecipeSelected(recipeName);
    }
}
