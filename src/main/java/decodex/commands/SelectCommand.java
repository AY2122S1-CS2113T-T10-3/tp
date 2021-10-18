package decodex.commands;

import decodex.data.Data;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.DataManagerException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.UnknownModuleException;
import decodex.modules.Module;
import decodex.modules.ModuleManager;
import decodex.ui.Ui;

public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_DESCRIPTION = "Selects a module and processes the data accordingly";

    private final String moduleName;

    public SelectCommand(String moduleName) {
        super();
        this.moduleName = moduleName;
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui)
            throws UnknownModuleException, CommandException, DataManagerException, ModuleException {
        if (moduleName.isBlank()) {
            throw new CommandException("[-] Missing module name");
        }

        Module module = moduleManager.selectModule(moduleName);
        Data newData = module.run(dataManager.getCurrentData());
        dataManager.setCurrentData(newData);

        System.out.printf("[+] Output: \"%s\"\n", newData.toString());
    }
}
