package decodex.commands;

import java.io.IOException;

import decodex.data.Data;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.DataManagerException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.ModuleManagerException;
import decodex.data.exception.RecipeException;
import decodex.data.exception.RecipeManagerException;
import decodex.modules.ModuleManager;
import decodex.modules.base64.Base64Encoder;
import decodex.modules.hex.HexEncoder;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.storage.Storage;
import decodex.ui.Ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SelectCommandTest {

    @Test
    public void run_validModuleName_success() throws ModuleManagerException, CommandException, DataManagerException,
            ModuleException, RecipeException, RecipeManagerException {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        Ui ui = new Ui();

        Data data = new Data("hello world");
        dataManager.setOriginalData(data);

        String selectCategory = "module";
        String moduleName = "base64encode";
        String[] parameters = {};
        SelectCommand selectCommand = new SelectCommand(selectCategory, moduleName, parameters);
        selectCommand.run(dataManager, moduleManager, ui, recipeManager, storage);

        assertEquals("aGVsbG8gd29ybGQ=", dataManager.getCurrentData().toString());
    }

    @Test
    public void run_moduleWithParameters_success() throws ModuleManagerException, CommandException, ModuleException,
            DataManagerException, RecipeException, RecipeManagerException {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Ui ui = new Ui();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();

        Data data = new Data("hello world");
        dataManager.setOriginalData(data);

        String selectCategory = "module";
        String moduleName = "rotencode";
        String[] parameters = {"13"};
        SelectCommand selectCommand = new SelectCommand(selectCategory, moduleName, parameters);
        selectCommand.run(dataManager, moduleManager, ui, recipeManager, storage);

        assertEquals("uryyb jbeyq", dataManager.getCurrentData().toString());
    }

    @Test
    public void run_blankModuleName_expectException() {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        Ui ui = new Ui();

        Data data = new Data("hello world");
        dataManager.setOriginalData(data);

        String selectCategory = "module";
        String moduleName = "";
        String[] parameters = {};
        SelectCommand selectCommand = new SelectCommand(selectCategory, moduleName, parameters);

        assertThrows(CommandException.class, () -> selectCommand.run(dataManager, moduleManager, ui, recipeManager,
                storage));
    }

    @Test
    public void run_unknownModuleName_expectException() {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        Ui ui = new Ui();

        Data data = new Data("hello world");
        dataManager.setOriginalData(data);

        String selectCategory = "module";
        String moduleName = "unknownModule";
        String[] parameters = {};
        SelectCommand selectCommand = new SelectCommand(selectCategory, moduleName, parameters);

        assertThrows(ModuleManagerException.class, () -> selectCommand.run(dataManager, moduleManager, ui,
                recipeManager, storage));
    }

    @Test
    public void run_validRecipeName_success() throws ModuleManagerException, CommandException, DataManagerException,
            ModuleException, RecipeException, RecipeManagerException, IOException {
        DataManager dataManager = new DataManager();
        Data data = new Data("hello world");
        dataManager.setOriginalData(data);

        RecipeManager recipeManager = new RecipeManager();
        String recipeName = "testRecipe";
        Recipe recipe = new Recipe(recipeName);
        recipe.push(new HexEncoder());
        recipe.push(new Base64Encoder());
        recipeManager.addRecipe(recipe);

        ModuleManager moduleManager = new ModuleManager();
        Ui ui = new Ui();
        Storage storage = new Storage();

        String selectCategory = "recipe";
        String[] parameters = {};
        SelectCommand selectCommand = new SelectCommand(selectCategory, recipeName, parameters);
        selectCommand.run(dataManager, moduleManager, ui, recipeManager, storage);

        assertEquals("Njg2NTZjNmM2ZjIwNzc2ZjcyNmM2NA==", dataManager.getCurrentData().toString());
    }

    @Test
    public void run_emptyRecipe_success() throws ModuleManagerException, CommandException, DataManagerException,
            ModuleException, RecipeException, RecipeManagerException, IOException {
        DataManager dataManager = new DataManager();
        String dataString = "hello world";
        Data data = new Data(dataString);
        dataManager.setOriginalData(data);

        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        String recipeName = "testRecipe";
        Recipe recipe = new Recipe(recipeName);
        recipeManager.addRecipe(recipe);

        ModuleManager moduleManager = new ModuleManager();
        Ui ui = new Ui();

        String selectCategory = "recipe";
        String[] parameters = {};
        SelectCommand selectCommand = new SelectCommand(selectCategory, recipeName, parameters);
        selectCommand.run(dataManager, moduleManager, ui, recipeManager, storage);

        assertEquals(dataString, dataManager.getCurrentData().toString());
    }

    @Test
    public void run_blankRecipeName_expectRecipeManagerException() {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        Ui ui = new Ui();

        String selectCategory = "recipe";
        String recipeName = "testRecipe";
        String[] parameters = {};
        SelectCommand selectCommand = new SelectCommand(selectCategory, recipeName, parameters);

        assertThrows(RecipeManagerException.class, () -> selectCommand.run(dataManager, moduleManager, ui,
                recipeManager, storage));
    }

    @Test
    public void run_unknownRecipe_expectRecipeManagerException() {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        Ui ui = new Ui();

        String selectCategory = "recipe";
        String recipeName = "unknownRecipe";
        String[] parameters = {};
        SelectCommand selectCommand = new SelectCommand(selectCategory, recipeName, parameters);

        assertThrows(RecipeManagerException.class, () -> selectCommand.run(dataManager, moduleManager, ui,
                recipeManager, storage));
    }

    @Test
    public void run_invalidSelectCategory_expectCommandException() {
        DataManager dataManager = new DataManager();
        ModuleManager moduleManager = new ModuleManager();
        Storage storage = new Storage();
        RecipeManager recipeManager = new RecipeManager();
        Ui ui = new Ui();

        String selectCategory = "unknownCategory";
        String recipeName = "unknownItem";
        String[] parameters = {};
        SelectCommand selectCommand = new SelectCommand(selectCategory, recipeName, parameters);

        assertThrows(CommandException.class, () -> selectCommand.run(dataManager, moduleManager, ui, recipeManager,
                storage));
    }
}
