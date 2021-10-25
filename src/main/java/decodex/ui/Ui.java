package decodex.ui;

import java.util.ArrayList;
import java.util.Scanner;

import decodex.modules.Module;
import decodex.recipes.Recipe;
import decodex.ui.messages.RegularMessages;

/**
 * The Ui class handles all user input and message output ot the console.
 */
public class Ui {

    private static final String PROMPT_HEADER = "Decodex > ";
    private static final String SUCCESS_ICON = "[+]";
    private static final String ERROR_ICON = "[x]";
    private static final String INPUT_PREFIX = "Input:";
    private static final String OUTPUT_PREFIX = "Output:";

    private final Scanner in;

    public Ui() {
        in = new Scanner(System.in);
    }

    /**
     * Prints the greeting message.
     */
    public void printGreeting() {
        System.out.println(RegularMessages.GREETING);
    }

    /**
     * Prints the goodbye message.
     */
    public void printGoodbye() {
        System.out.println(RegularMessages.GOODBYE);
    }

    /**
     * Reads user input from console.
     *
     * @return String of the user input.
     */
    public String readInput() {
        printPromptHeader();
        return in.nextLine();
    }

    /**
     * Prints the read user input.
     *
     * @param input The user input read.
     */
    public void printInput(String input) {
        assert input != null : "Input should not be null";
        assert !input.isBlank() : "Input should not be empty";
        printSuccess(String.format("%s \"%s\"", INPUT_PREFIX, input));
    }

    /**
     * Prints the output after data manipulation.
     *
     * @param output String output of the data.
     */
    public void printOutput(String output) {
        assert output != null : "Output should not be null";
        printSuccess(String.format("%s \"%s\"", OUTPUT_PREFIX, output));
    }

    /**
     * Prints the list of supported modules.
     *
     * @param moduleList The list of supported modules.
     */
    public void printModuleList(String moduleList) {
        assert moduleList != null : "Module list should not be null";
        assert !moduleList.isBlank() : "Module list should not be empty";
        System.out.println(RegularMessages.LIST_MODULES + "\n" + moduleList);
    }

    /**
     * Prints the list of available recipes.
     *
     * @param recipeList The list of available recipes.
     */
    public void printRecipeList(String recipeList) {
        assert recipeList != null : "Recipe list should not be null";
        if (!recipeList.isBlank()) {
            System.out.println(RegularMessages.LIST_RECIPES + "\n" + recipeList);
        } else {
            System.out.println(RegularMessages.NO_RECIPES + "\n");
        }
    }

    /**
     * Prints the modules in a recipe.
     *
     * @param recipe The provided recipe.
     */
    public void printRecipeModules(Recipe recipe) {
        if (recipe.getModuleList().size() == 0) {
            printSuccess(RegularMessages.RECIPE_EMPTY);
            return;
        }

        ArrayList<Module> recipeModuleList = recipe.getModuleList();
        String[] recipeModuleNames = recipeModuleList.stream().map(Module::getName).toArray(String[]::new);

        String message = RegularMessages.RECIPE_MODULES_EXECUTED + " " + String.join(", ", recipeModuleNames);
        printSuccess(message);
    }

    /**
     * Prints the list of modules in a recipe.
     *
     * @param moduleList The list of modules in the recipe.
     * @param recipeName The name of the recipe.
     */
    public void printRecipeModulesList(String moduleList, String recipeName) {
        assert moduleList != null : "Module list should not be null";
        assert !moduleList.isBlank() : "Module list should not be empty";
        assert recipeName != null : "Recipe name should not be null";
        assert !recipeName.isBlank() : "Recipe name should not be empty";
        System.out.println(String.format(RegularMessages.LIST_RECIPE_MODULES + "\n%s", recipeName, moduleList));
    }

    /**
     * Prints the message when a recipe is reset.
     *
     * @param recipeName The name of the recipe that was reset.
     */
    public void printRecipeReset(String recipeName) {
        assert recipeName != null : "Recipe name should not be null";
        assert !recipeName.isBlank() : "Recipe name should not be empty";
        printSuccess(String.format(RegularMessages.RESETTED_EDITING_RECIPE, recipeName));
    }

    /**
     * Prints a message showing that a given module was removed from a given recipe.
     *
     * @param moduleName The name of the module removed.
     * @param recipeName The name of the recipe which the module was removed from.
     */
    public void printModuleRemovedFromRecipe(String moduleName, String recipeName) {
        assert moduleName != null : "Module name should not be null";
        assert !moduleName.isBlank() : "Module name should not be empty";
        assert recipeName != null : "Recipe name should not be null";
        assert !recipeName.isBlank() : "Recipe name should not be empty";
        printSuccess(String.format(RegularMessages.REMOVED_RECIPE_FROM_RECIPE + "\n", moduleName, recipeName));
    }

    /**
     * Prints a message showing that a given module was added to a given recipe.
     *
     * @param moduleName The name of the module added.
     * @param recipeName The name of the recipe which the module was added to.
     */
    public void printModuleAddedToRecipe(String moduleName, String recipeName) {
        assert moduleName != null : "Module name should not be null";
        assert !moduleName.isBlank() : "Module name should not be empty";
        assert recipeName != null : "Recipe name should not be null";
        assert !recipeName.isBlank() : "Recipe name should not be empty";
        printSuccess(String.format(RegularMessages.ADDED_MODULE_TO_RECIPE + "\n", moduleName, recipeName));
    }

    /**
     * Prints a message showing that a recipe was selected.
     *
     * @param recipeName The name of the selected recipe.
     */
    public void printRecipeSelected(String recipeName) {
        assert recipeName != null : "Recipe name should not be null";
        assert !recipeName.isBlank() : "Recipe name should not be empty";
        printSuccess(String.format(RegularMessages.RECIPE_SELECT + "\n", recipeName));
    }

    /**
     * Prints a message with a success status icon.
     *
     * @param message Success message
     */
    public void printSuccess(String message) {
        assert message != null : "Message should not be null";
        assert !message.isBlank() : "Message should not be empty";
        System.out.println(SUCCESS_ICON + " " + message);
    }

    /**
     * Prints the error message from the thrown exception with an error icon.
     *
     * @param exception The thrown exception.
     */
    public void printError(Exception exception) {
        assert exception.getMessage() != null : "Exception message should not be null";
        assert !exception.getMessage().isBlank() : "Exception message should not be empty";
        System.out.println(ERROR_ICON + " " + exception.getMessage());
    }

    /**
     * Prints a message indicating successful deletion of a recipe.
     *
     * @param recipeName The name of the recipe that was deleted.
     */
    public void printRecipeDeleted(String recipeName) {
        assert recipeName != null : "Recipe name should not be null";
        assert !recipeName.isBlank() : "Recipe name should not be empty";
        printSuccess(String.format(RegularMessages.RECIPE_DELETED + "\n", recipeName));
    }

    /**
     * Prints a message indicating the creation of a new recipe of given name.
     *
     * @param recipeName The name of the recipe created.
     */
    public void printNewRecipeCreated(String recipeName) {
        assert recipeName != null : "Recipe name should not be null";
        assert !recipeName.isBlank() : "Recipe name should not be empty";
        printSuccess(String.format(RegularMessages.NEW_RECIPE_CREATED + "\n", recipeName));
    }

    /**
     * Prints the prompt header.
     */
    private void printPromptHeader() {
        System.out.print(PROMPT_HEADER);
    }
}
