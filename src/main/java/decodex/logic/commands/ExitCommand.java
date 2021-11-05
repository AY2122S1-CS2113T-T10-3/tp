package decodex.logic.commands;

import decodex.data.DataManager;
import decodex.logic.Command;
import decodex.modules.ModuleManager;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;

public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String COMMAND_HELP_MESSAGE = "Exit the program\n"
            + "Syntax: exit";

    public ExitCommand() {
        super();
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage) {
        ui.printGoodbye();
    }
}
