package decodex.recipes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import decodex.data.Data;
import decodex.data.exception.ModuleException;
import decodex.data.exception.RecipeException;
import decodex.modules.Module;
import decodex.modules.base64.Base64Decoder;
import decodex.modules.base64.Base64Encoder;
import decodex.modules.binary.BinaryEncoder;
import decodex.modules.hex.HexDecoder;
import decodex.modules.hex.HexEncoder;
import decodex.modules.rot.RotEncoder;

class RecipeTest {

    @Test
    void recipe_allowedCharactersInName_recipeCreated() throws RecipeException {
        String validRecipeName = "test_Recipe";
        Recipe testRecipe = new Recipe(validRecipeName);
        assertNotNull(testRecipe);
    }

    @Test
    void recipe_emptyRecipeName_recipeCreated() {
        String emptyRecipeName = "";
        assertThrows(RecipeException.class, () -> new Recipe(emptyRecipeName));
    }

    @Test
    void recipe_illegalCharactersInName_recipeCreated() {
        String invalidRecipeName = "**bad-name?!";
        assertThrows(RecipeException.class, () -> new Recipe(invalidRecipeName));
    }

    @Test
    void push_singleModule_recipeContainsOneModule() throws RecipeException {
        Recipe recipe = new Recipe("testRecipe");
        Module module = new HexEncoder();
        recipe.push(module);

        assertEquals(recipe.getModuleList().size(), 1);
    }

    @Test
    void pop_singleModule_latestModule() throws RecipeException {
        Recipe recipe = new Recipe("testRecipe");
        Module module = new HexDecoder();
        recipe.push(module);

        Module poppedModule = recipe.pop();

        assertEquals(module, poppedModule);
    }

    @Test
    void pop_emptyRecipe_expectException() throws RecipeException {
        Recipe recipe = new Recipe("testRecipe");

        assertThrows(RecipeException.class, () -> recipe.pop());
    }

    @Test
    void reset_emptyModuleList() throws RecipeException {
        Recipe recipe = new Recipe("testRecipe");
        recipe.push(new HexEncoder());
        recipe.push(new HexDecoder());
        recipe.push(new Base64Encoder());
        recipe.push(new Base64Decoder());

        recipe.reset();

        assertTrue(recipe.getModuleList().isEmpty());
    }

    @Test
    void run_chainOfModules_encodedOutput() throws RecipeException, ModuleException {
        Recipe recipe = new Recipe("rainbowTest");

        recipe.push(new HexEncoder());
        recipe.push(new Base64Encoder());
        recipe.push(new RotEncoder(5));
        recipe.push(new BinaryEncoder());

        Data inputData = new Data("egg");

        Data bakedData = recipe.run(inputData);
        String result = bakedData.toString();
        String expectedOutput = "0101001101101111010110100011001001010011011001010100010000110011";

        assertEquals(result, expectedOutput);
    }

    @Test()
    void run_chainOfModulesFailHalfway_expectException() throws RecipeException {
        Recipe recipe = new Recipe("rainbowTest");

        recipe.push(new HexEncoder());
        recipe.push(new Base64Encoder());
        recipe.push(new RotEncoder(5));
        recipe.push(new HexDecoder());
        recipe.push(new BinaryEncoder());

        Data inputData = new Data("egg");

        assertThrows(ModuleException.class, () -> recipe.run(inputData));
    }

}
