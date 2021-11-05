package decodex.recipes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import decodex.data.exception.RecipeException;
import decodex.data.exception.RecipeManagerException;
import decodex.modules.Module;
import decodex.modules.hex.HexEncoder;

// @@author rizemon
class RecipeManagerTest {

    private RecipeManager recipeManager;

    @BeforeEach
    public void createNewRecipeManager() {
        recipeManager = new RecipeManager();
    }

    @Test
    void addRecipe_repeatedRecipeName_expectException() throws RecipeException, RecipeManagerException, IOException {
        Recipe newRecipe = new Recipe("sameRecipeName");
        recipeManager.addRecipe(newRecipe);

        Recipe repeatedRecipe = new Recipe("sameRecipeName");
        assertThrows(RecipeManagerException.class, () -> recipeManager.addRecipe(repeatedRecipe));
    }

    @Test
    void removeRecipe_oneRecipe_removedRecipeIsTheOneRecipe()
            throws RecipeException, RecipeManagerException, IOException {
        String testRecipeName = "testRecipeName";
        Recipe testRecipe = new Recipe(testRecipeName);
        recipeManager.addRecipe(testRecipe);
        Recipe removedRecipe = recipeManager.removeRecipe(testRecipeName);
        assertEquals(removedRecipe.getName(), testRecipeName);
    }

    @Test
    void removeRecipe_noRecipe_expectException() {
        String testRecipeName = "testRecipeName";
        assertThrows(RecipeManagerException.class, () -> recipeManager.removeRecipe(testRecipeName));
    }

    @Test
    void removeRecipe_recipeIsEditedRecipe_expectException()
            throws RecipeException, RecipeManagerException, IOException {
        String testRecipeName = "testRecipeName";
        Recipe testRecipe = new Recipe(testRecipeName);
        recipeManager.addRecipe(testRecipe);

        recipeManager.selectRecipeForEditing(testRecipeName);
        recipeManager.removeRecipe(testRecipeName);
        assertThrows(RecipeManagerException.class, () -> recipeManager.getEditingRecipe());
    }

    @Test
    void getRecipe_oneRecipe_managerContainOneRecipe() throws RecipeException, RecipeManagerException, IOException {
        String testRecipeName = "testRecipeName";
        Recipe testRecipe = new Recipe(testRecipeName);
        recipeManager.addRecipe(testRecipe);

        Recipe retrievedRecipe = recipeManager.getRecipe(testRecipeName);
        assertEquals(testRecipe.getName(), retrievedRecipe.getName());
    }

    @Test
    void getRecipe_noRecipe_expectException() {
        String testRecipeName = "testRecipeName";
        assertThrows(RecipeManagerException.class, () -> recipeManager.getRecipe(testRecipeName));
    }

    @Test
    void getEditedRecipe_oneEditedRecipe_editedRecipe() throws RecipeException, RecipeManagerException, IOException {
        String testRecipeName = "testRecipeName";
        Recipe testRecipe = new Recipe(testRecipeName);
        recipeManager.addRecipe(testRecipe);
        recipeManager.selectRecipeForEditing(testRecipeName);

        assertEquals(recipeManager.getEditingRecipe().getName(), testRecipeName);
    }

    @Test
    void getEditedRecipe_noEditedRecipe_expectException() {
        String testRecipeName = "testRecipeName";

        assertThrows(RecipeManagerException.class, () -> recipeManager.selectRecipeForEditing(testRecipeName));
    }

    @Test
    void selectRecipe_oneEditedRecipe_editedRecipe() throws RecipeException, RecipeManagerException, IOException {
        String testRecipeName = "testRecipeName";
        Recipe testRecipe = new Recipe(testRecipeName);
        recipeManager.addRecipe(testRecipe);

        recipeManager.selectRecipeForEditing(testRecipeName);
        assertEquals(recipeManager.getEditingRecipe().getName(), testRecipeName);
    }

    @Test
    void selectRecipe_nonExistentRecipeName_expectException() {
        String testRecipeName = "testRecipeName";

        assertThrows(RecipeManagerException.class, () -> recipeManager.selectRecipeForEditing(testRecipeName));
    }

    @Test
    void pushModuleIntoEditedRecipe_oneEditedRecipe_oneModuele()
            throws RecipeException, RecipeManagerException, IOException {
        String testRecipeName = "testRecipeName";
        Recipe testRecipe = new Recipe(testRecipeName);
        recipeManager.addRecipe(testRecipe);
        recipeManager.selectRecipeForEditing(testRecipeName);

        HexEncoder insertedModule = new HexEncoder();

        recipeManager.pushModuleIntoEditedRecipe(insertedModule);

        assertEquals(recipeManager.getEditingRecipe().getModuleList().size(), 1);
    }

    @Test
    void pushModuleIntoEditedRecipe_noEditedRecipe_expectException()
            throws RecipeException, RecipeManagerException, IOException {
        String testRecipeName = "testRecipeName";
        Recipe testRecipe = new Recipe(testRecipeName);
        recipeManager.addRecipe(testRecipe);

        Module insertedModule = new HexEncoder();

        assertThrows(RecipeManagerException.class, () -> recipeManager.pushModuleIntoEditedRecipe(insertedModule));
    }

    @Test
    void popModuleFromEditedRecipe_oneEditedRecipe_poppedModule()
            throws RecipeException, RecipeManagerException, IOException {
        String testRecipeName = "testRecipeName";
        Recipe testRecipe = new Recipe(testRecipeName);
        Module insertedModule = new HexEncoder();
        testRecipe.push(insertedModule);
        recipeManager.addRecipe(testRecipe);
        recipeManager.selectRecipeForEditing(testRecipeName);

        Module poppedModule = recipeManager.popModuleFromEditedRecipe();
        assertEquals(insertedModule.getName(), poppedModule.getName());
    }

    @Test
    void popModuleFromEditedRecipe_noEditedRecipe_expectException()
            throws RecipeException, RecipeManagerException, IOException {
        String testRecipeName = "testRecipeName";
        Recipe testRecipe = new Recipe(testRecipeName);
        Module insertedModule = new HexEncoder();
        testRecipe.push(insertedModule);
        recipeManager.addRecipe(testRecipe);

        assertThrows(RecipeManagerException.class, () -> recipeManager.popModuleFromEditedRecipe());
    }

    @Test
    void resetEditedRecipe_oneEditedRecipe_noError() throws RecipeException, RecipeManagerException, IOException {
        String testRecipeName = "testRecipeName";
        Recipe testRecipe = new Recipe(testRecipeName);
        recipeManager.addRecipe(testRecipe);
        recipeManager.selectRecipeForEditing(testRecipeName);

        recipeManager.resetEditedRecipe();

        assertEquals(recipeManager.getEditingRecipe().getModuleList().size(), 0);
    }

    @Test
    void resetEditedRecipe_noEditedRecipe_expectException()
            throws RecipeException, RecipeManagerException, IOException {
        String testRecipeName = "testRecipeName";
        Recipe testRecipe = new Recipe(testRecipeName);
        recipeManager.addRecipe(testRecipe);

        assertThrows(RecipeManagerException.class, () -> recipeManager.resetEditedRecipe());
    }
}
