package decodex.commands;

import decodex.data.Data;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.logic.Command;
import decodex.modules.ModuleManager;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;
import decodex.ui.messages.ErrorMessages;

// @@author rizemon
public class InputCommand extends Command {

    public static final String COMMAND_WORD = "input";
    public static final String COMMAND_HELP_MESSAGE = "Input of data\n"
            + "Syntax: input <data>";

    private final String dataString;

    public InputCommand(String dataString) {
        super();
        this.dataString = dataString;
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui, RecipeManager recipeManager,
            Storage storage) throws CommandException {
        if (dataString.isEmpty()) {
            throw new CommandException(ErrorMessages.MISSING_ARGUMENT);
        }

        Data userData = new Data(dataString);
        dataManager.setOriginalData(userData);

        ui.printInput(dataString);
    }
}
