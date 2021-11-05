package decodex.commands.recipe;

import java.io.IOException;

import decodex.commands.Command;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.ModuleManagerException;
import decodex.data.exception.RecipeManagerException;
import decodex.data.exception.StorageException;
import decodex.modules.Module;
import decodex.modules.ModuleManager;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;
import decodex.ui.messages.ErrorMessages;

// @@author rizemon
public class RecipePushCommand extends Command {

    public static final String COMMAND_WORD = "push";
    public static final String COMMAND_HELP_MESSAGE = "Add module to recipe\n"
            + "Syntax: recipe push <moduleName> {moduleArgument}";

    private final String moduleName;
    private final String[] parameters;

    public RecipePushCommand(String moduleName, String[] parameters) {
        super();
        this.moduleName = moduleName;
        this.parameters = parameters;
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage) throws CommandException, ModuleManagerException, ModuleException, RecipeManagerException,
            IOException {
        if (moduleName.isBlank()) {
            throw new CommandException(ErrorMessages.MISSING_MODULE_NAME);
        }

        Module module = moduleManager.selectModule(moduleName, parameters);
        Recipe editingRecipe =  recipeManager.getEditingRecipe();
        recipeManager.pushModuleIntoEditedRecipe(module);
        try {
            storage.saveRecipeToFile(editingRecipe);
        } catch (IOException | StorageException err) {
            ui.printError(err);
        }
        ui.printModuleAddedToRecipe(module.getName(),  editingRecipe.getName());
    }
}
