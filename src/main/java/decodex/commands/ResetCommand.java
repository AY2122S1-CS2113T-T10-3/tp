package decodex.commands;

import decodex.data.DataManager;
import decodex.modules.ModuleManager;
import decodex.ui.Ui;
import decodex.ui.messages.RegularMessages;

public class ResetCommand extends Command {

    public static final String COMMAND_WORD = "reset";
    public static final String COMMAND_DESCRIPTION = "Resets the transformed data back to the original input";

    public ResetCommand() {
        super();
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui) {
        dataManager.resetToOriginalData();
        ui.printSuccess(RegularMessages.REVERTED_ALL_CHANGES);
    }
}
