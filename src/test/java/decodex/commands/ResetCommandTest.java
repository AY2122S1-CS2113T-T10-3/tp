package decodex.commands;

import java.util.Arrays;

import decodex.data.Data;
import decodex.data.DataManager;
import decodex.data.exception.DataManagerException;
import decodex.modules.ModuleManager;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @@author rizemon
class ResetCommandTest {

    @Test
    public void run_noChanges_sameOriginalData() throws DataManagerException {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        Ui ui = new Ui();

        Data originalData = new Data("hi");
        dataManager.setOriginalData(originalData);

        ResetCommand testCommand = new ResetCommand();
        testCommand.run(dataManager, moduleManager, ui, recipeManager, storage);
        assertTrue(Arrays.equals(dataManager.getCurrentData().getRawBytes(), originalData.getRawBytes()));
    }

    @Test
    public void run_newData_sameOriginalData() throws DataManagerException {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        Ui ui = new Ui();

        Data originalData = new Data("hi");
        dataManager.setOriginalData(originalData);
        Data newData = new Data("bye");
        dataManager.setCurrentData(newData);

        ResetCommand testCommand = new ResetCommand();
        testCommand.run(dataManager, moduleManager, ui, recipeManager, storage);
        assertTrue(Arrays.equals(dataManager.getCurrentData().getRawBytes(), originalData.getRawBytes()));
    }
}
