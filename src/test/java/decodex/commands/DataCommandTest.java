package decodex.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import decodex.data.exception.ModuleException;
import decodex.data.exception.UnknownModuleException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import decodex.data.Data;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.DataManagerException;
import decodex.modules.ModuleManager;
import decodex.ui.Ui;

public class DataCommandTest {

    @Test
    public void run_missingArguments_expectException() {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Ui ui = new Ui();
        String dataString = "";
        Command testCommand = new DataCommand(dataString);
        assertThrows(CommandException.class, () -> testCommand.run(dataManager, moduleManager, ui));
    }

    @Test
    public void run_insertData_insertedData()
            throws CommandException, DataManagerException, UnknownModuleException, ModuleException {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Ui ui = new Ui();
        String dataString = "something";
        Command testCommand = new DataCommand(dataString);
        testCommand.run(dataManager, moduleManager, ui);
        Data testData = new Data(dataString);
        assertTrue(Arrays.equals(dataManager.getOriginalData().getRawBytes(), testData.getRawBytes()));
    }
}
