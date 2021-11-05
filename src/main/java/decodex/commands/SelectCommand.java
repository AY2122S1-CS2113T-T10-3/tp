package decodex.commands;

import decodex.data.Data;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.DataManagerException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.ModuleManagerException;
import decodex.data.exception.RecipeException;
import decodex.data.exception.RecipeManagerException;
import decodex.logic.Command;
import decodex.modules.Module;
import decodex.modules.ModuleManager;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;
import decodex.ui.messages.ErrorMessages;

public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_HELP_MESSAGE = "Selects a module or recipe to run\n"
            + "Syntax: select module <moduleName> {moduleArgument}, select recipe <recipeName>";

    private static final String SELECT_CATEGORY_MODULE = "module";
    private static final String SELECT_CATEGORY_RECIPE = "recipe";

    private final String selectCategory;
    private final String itemName;
    private final String[] parameters;

    public SelectCommand(String selectCategory, String itemName, String[] parameters) {
        super();
        this.selectCategory = selectCategory;
        this.itemName = itemName;
        this.parameters = parameters;
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage)
            throws ModuleManagerException, CommandException, DataManagerException, ModuleException, RecipeException,
            RecipeManagerException {
        assert selectCategory != null : "selectCategory should not be null";
        assert itemName != null : "itemName should not be null";
        assert parameters != null : "parameters should not be null";

        switch (selectCategory) {
        case SELECT_CATEGORY_MODULE:
            runModule(dataManager, moduleManager, ui);
            break;
        case SELECT_CATEGORY_RECIPE:
            runRecipe(dataManager, recipeManager, ui);
            break;
        default:
            throw new CommandException(ErrorMessages.UNKNOWN_SELECTION_CATEGORY);
        }
    }

    /**
     * Selects and runs a module.
     *
     * @param dataManager   The provided DataManager.
     * @param moduleManager The provided ModuleManager.
     * @param ui            The provided Ui.
     * @throws CommandException       If the command parameters are invalid.
     * @throws ModuleManagerException If the selected module is invalid.
     * @throws ModuleException        If the module execution has failed.
     * @throws DataManagerException   If the data is invalid.
     */
    private void runModule(DataManager dataManager, ModuleManager moduleManager, Ui ui)
            throws CommandException, ModuleManagerException, ModuleException, DataManagerException {
        if (itemName.isBlank()) {
            throw new CommandException(ErrorMessages.MISSING_MODULE_NAME);
        }

        Module module = moduleManager.selectModule(itemName, parameters);
        Data newData = module.run(dataManager.getCurrentData());
        dataManager.setCurrentData(newData);

        ui.printOutput(newData.toString());
    }

    /**
     * Selects and runs a module.
     *
     * @param dataManager   The provided DataManager.
     * @param recipeManager The provided RecipeManager.
     * @param ui            The provided Ui.
     * @throws CommandException       If the command parameters are invalid.
     * @throws RecipeManagerException If the recipe is invalid.
     * @throws ModuleException        If the module execution has failed.
     * @throws DataManagerException   If the data is invalid.
     */
    private void runRecipe(DataManager dataManager, RecipeManager recipeManager, Ui ui)
            throws CommandException, RecipeManagerException, ModuleException, DataManagerException {
        if (itemName.isBlank()) {
            throw new CommandException(ErrorMessages.MISSING_RECIPE_NAME);
        }

        if (parameters.length > 0) {
            throw new CommandException(ErrorMessages.TOO_MANY_ARGUMENTS_MESSAGE);
        }

        Recipe recipe = recipeManager.getRecipe(itemName);
        Data newData = recipe.run(dataManager.getCurrentData());
        dataManager.setCurrentData(newData);

        ui.printRecipeModules(recipe);
        ui.printOutput(newData.toString());
    }
}
