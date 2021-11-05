package decodex;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import decodex.logic.Command;
import decodex.logic.commands.ExitCommand;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.DataManagerException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.ModuleManagerException;
import decodex.data.exception.ParserException;
import decodex.data.exception.RecipeException;
import decodex.data.exception.RecipeManagerException;
import decodex.data.exception.StorageException;
import decodex.modules.ModuleManager;
import decodex.parser.Parser;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;

public class Decodex {

    public static Logger logger = Logger.getLogger(Decodex.class.getName());

    // Necessary objects to be initialized for Decodex to work properly.
    private static DataManager dataManager;
    private static ModuleManager moduleManager;
    private static RecipeManager recipeManager;
    private static Parser parser;
    private static Ui ui;
    private static Storage storage;

    public Decodex() {
        initDecodex();
    }

    /**
     * Initializes the necessary Objects for Decodex.
     */
    private void initDecodex() {
        logger.setLevel(Level.INFO);
        dataManager = new DataManager();
        moduleManager = new ModuleManager();
        parser = new Parser();
        ui = new Ui();
        storage = new Storage();
        recipeManager = new RecipeManager();
        try {
            loadSavedRecipes();
        } catch (IOException | StorageException err) {
            ui.printError(err);
        }

    }

    /**
     * Decodex entry-point for the java.decodex.Decodex application.
     */
    public static void main(String[] args) {
        new Decodex().run();
    }

    /**
     * Runs the main logic of Decodex.
     */
    public void run() {
        ui.printGreeting();

        Command command = null;
        boolean isExit;
        do {
            String editingRecipeName;
            try {
                editingRecipeName = recipeManager.getEditingRecipe().getName();
            } catch (RecipeManagerException e) {
                editingRecipeName = null;
            }
            ui.printPromptHeader(editingRecipeName);
            String userInput = ui.readInput();
            logger.fine("User input: " + userInput);
            try {
                command = parser.parseCommand(userInput);
                assert command != null : "Command should not be null";
                command.run(dataManager, moduleManager, ui, recipeManager, storage);
            } catch (ParserException | CommandException | ModuleManagerException | DataManagerException
                    | ModuleException | RecipeException | RecipeManagerException | IOException err) {
                ui.printError(err);
                logger.fine(err.getMessage());
            }
            isExit = command instanceof ExitCommand;
        } while (!isExit);
    }

    /**
     * Loads the saved recipes stored in the recipe directory.
     *
     * @throws IOException      If an error occurred when creating the recipe directory,
     *                          or reading the recipe files.
     * @throws StorageException If the recipe directory is not an actual directory,
     *                          or if it failed to list the recipe files,
     *                          or if the respective recipe file is not an actual file.
     */
    private void loadSavedRecipes() throws IOException, StorageException {
        storage.loadRecipesFromDirectory(moduleManager, recipeManager, ui);
    }
}
