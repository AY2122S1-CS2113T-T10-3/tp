package decodex.commands;

import decodex.data.DataManager;
import decodex.modules.Module;
import decodex.modules.ModuleManager;
import decodex.ui.Ui;

public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_DESCRIPTION = "Shows a list of all available modules";

    public ListCommand() {
        super();
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui) {
        Module[] modules = moduleManager.getModules();
        assert modules.length > 0 : "Number of modules should be greater than 0";

        StringBuilder moduleListString = new StringBuilder();
        int maxNameWidth = 0;

        // Find number of characters of the longest module name
        for (Module module : modules) {
            String moduleName = module.getName();
            if (moduleName.length() > maxNameWidth) {
                maxNameWidth = moduleName.length();
            }
        }

        // Prepare and format list of modules
        for (Module module : modules) {
            String moduleName = module.getName();
            String moduleDescription = module.getDescription();
            moduleListString.append(String.format("  %-" + maxNameWidth + "s - %s\n", moduleName, moduleDescription));
        }

        ui.printModuleList(moduleListString.toString());
    }
}
