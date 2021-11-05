package decodex.commands.recipe;

import java.util.ArrayList;

import decodex.commands.Command;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.RecipeManagerException;
import decodex.modules.Module;
import decodex.modules.ModuleManager;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;
import decodex.ui.messages.RegularMessages;

public class RecipeListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_HELP_MESSAGE = "List modules in recipe\n"
            + "Syntax: recipe list {recipeName}";
    private static final int ARRAY_INDEX_OFFSET = -1;

    private final String recipeName;

    public RecipeListCommand(String recipeName) {
        super();
        this.recipeName = recipeName;
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage)
            throws CommandException, ModuleException, RecipeManagerException {
        Recipe recipe;

        if (recipeName.isBlank()) {
            recipe = recipeManager.getEditingRecipe();
        } else {
            recipe = recipeManager.getRecipe(recipeName);
        }

        ui.printRecipeModulesList(getModuleList(recipe), recipe.getName());
    }

    /**
     * Gets and prepares an indexed list of the modules in the given recipe.
     *
     * @param recipe The recipe to list.
     * @return A formatted, indexed list of modules in the recipe.
     */
    private String getModuleList(Recipe recipe) {
        ArrayList<Module> recipeModuleList = recipe.getModuleList();

        if (recipeModuleList.isEmpty()) {
            return RegularMessages.RECIPE_EMPTY;
        }

        String[] recipeModuleNames = recipeModuleList.stream()
                .map(Module::toString)
                .toArray(String[]::new);
        StringBuilder recipeListString = new StringBuilder();

        for (int index = 1; index <= recipeModuleNames.length; index++) {
            recipeListString.append(String.format("  %d. %s\n", index, recipeModuleNames[index + ARRAY_INDEX_OFFSET]));
        }

        return recipeListString.toString();
    }

}
