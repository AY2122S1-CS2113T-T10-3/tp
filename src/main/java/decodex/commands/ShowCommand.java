package decodex.commands;

import decodex.data.Data;
import decodex.data.DataManager;
import decodex.data.exception.DataManagerException;
import decodex.logic.Command;
import decodex.modules.ModuleManager;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;

public class ShowCommand extends Command {
    public static final String COMMAND_WORD = "show";
    public static final String COMMAND_HELP_MESSAGE = "Shows the current data\n"
            + "Syntax: show";

    public ShowCommand() {
        super();
    }

    /**
     * Shows the current data.
     *
     * @param dataManager   The DataManager object.
     * @param moduleManager The ModuleManager object.
     * @param ui            The Ui object.
     * @param recipeManager The RecipeManager object.
     * @param storage       The Storage object.
     * @throws DataManagerException If the current data is empty.
     */
    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage) throws DataManagerException {
        Data currentData = dataManager.getCurrentData();
        String currentDataInput = currentData.toString();
        ui.printCurrentData(currentDataInput);
    }
}
