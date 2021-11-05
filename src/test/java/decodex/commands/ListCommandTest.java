package decodex.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.modules.ModuleManager;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListCommandTest {

    private ByteArrayOutputStream outputStream;
    private final PrintStream originalOutputStream = System.out;

    private final DataManager dataManager = new DataManager();
    private final ModuleManager moduleManager = new ModuleManager();
    private final Storage storage = new Storage();
    private final RecipeManager recipeManager = new RecipeManager();
    private final Ui ui = new Ui();

    @BeforeEach
    public void setOutputStream() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreOutputStream() {
        System.setOut(originalOutputStream);
    }

    @Test
    public void run_listNull_success() throws CommandException {
        String listCategory = null;
        ListCommand listCommand = new ListCommand(listCategory);
        listCommand.run(dataManager, moduleManager, ui, recipeManager, storage);
        assertFalse(outputStream.toString().isBlank());
    }

    @Test
    public void run_listModules_success() throws CommandException {
        String listCategory = "modules";
        ListCommand listCommand = new ListCommand(listCategory);
        listCommand.run(dataManager, moduleManager, ui, recipeManager, storage);
        assertFalse(outputStream.toString().isBlank());
    }

    @Test
    public void run_listRecipes_success() throws CommandException {
        String listCategory = "recipes";
        ListCommand listCommand = new ListCommand(listCategory);
        listCommand.run(dataManager, moduleManager, ui, recipeManager, storage);
        assertFalse(outputStream.toString().isBlank());
    }

    @Test
    public void run_listUnknown_throwsCommandException() {
        String listCategory = "unknown";
        ListCommand listCommand = new ListCommand(listCategory);
        assertThrows(CommandException.class, () -> listCommand.run(dataManager, moduleManager, ui, recipeManager,
                storage));
    }
}
