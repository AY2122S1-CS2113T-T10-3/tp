package decodex.commands;

import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.logic.Command;
import decodex.modules.ModuleManager;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;
import decodex.ui.messages.ErrorMessages;

// @@author arraysius
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_HELP_MESSAGE = "List available modules and recipes\n"
            + "Syntax: list {category}\n"
            + "Acceptable values of category: modules, recipes";

    public static final String LIST_CATEGORY_MODULES = "modules";
    public static final String LIST_CATEGORY_RECIPE = "recipes";
    private static final int MODULE_NAME_INDEX = 0;
    private static final int MODULE_DESCRIPTION_INDEX = 1;

    private final String listCategory;

    public ListCommand(String listCategory) {
        super();
        this.listCategory = listCategory;
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage)
            throws CommandException {
        boolean isPrintModuleList = listCategory == null || listCategory.equals(LIST_CATEGORY_MODULES);
        boolean isPrintRecipeList = listCategory == null || listCategory.equals(LIST_CATEGORY_RECIPE);

        if (isPrintModuleList) {
            ui.printModuleList(getModuleList(moduleManager));
        }
        if (isPrintRecipeList) {
            ui.printRecipeList(getRecipeList(recipeManager));
        }
        if (!isPrintModuleList && !isPrintRecipeList) {
            throw new CommandException(ErrorMessages.INVALID_LIST_CATEGORY);
        }
    }

    /**
     * Prepares the list of supported modules.
     *
     * @param moduleManager The ModuleManager to retrieve supported modules.
     * @return A formatted String of modules.
     */
    private String getModuleList(ModuleManager moduleManager) {
        String[][] modules = moduleManager.getModules();
        assert modules.length > 0 : "Number of modules should be greater than 0";

        StringBuilder moduleListString = new StringBuilder();
        int maxNameWidth = 0;

        // Find number of characters of the longest module name
        for (String[] module : modules) {
            String moduleName = module[MODULE_NAME_INDEX];
            if (moduleName.length() > maxNameWidth) {
                maxNameWidth = moduleName.length();
            }
        }

        // Prepare and format list of modules
        for (String[] module : modules) {
            String moduleName = module[MODULE_NAME_INDEX];
            String moduleDescription = module[MODULE_DESCRIPTION_INDEX];
            moduleListString.append(String.format("  %-" + maxNameWidth + "s - %s\n", moduleName, moduleDescription));
        }

        return moduleListString.toString();
    }

    /**
     * Prepares the list of available recipes.
     *
     * @param recipeManager The RecipeManager to retrieve available recipes.
     * @return A formatted String of recipes.
     */
    private String getRecipeList(RecipeManager recipeManager) {
        String[] recipeNames = recipeManager.getRecipeNames();

        StringBuilder recipeListString = new StringBuilder();

        // Prepare and format list of recipes
        for (String recipeName : recipeNames) {
            recipeListString.append(String.format("  %s\n", recipeName));
        }

        return recipeListString.toString();
    }
}
