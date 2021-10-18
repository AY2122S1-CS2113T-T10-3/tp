package decodex.commands;

import decodex.data.Data;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.modules.ModuleManager;
import decodex.ui.Ui;

public class InputCommand extends Command {

    public static final String COMMAND_WORD = "input";
    public static final String COMMAND_DESCRIPTION = "Stores the data to be processed by modules";

    private final String dataString;

    public InputCommand(String dataString) {
        super();
        this.dataString = dataString;
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui) throws CommandException {
        if (dataString.isEmpty()) {
            throw new CommandException("[-] Missing argument");
        }
        Data userData = new Data(dataString);
        dataManager.setOriginalData(userData);
        System.out.printf("[+] Input: \"%s\"\n", dataString);
    }
}
