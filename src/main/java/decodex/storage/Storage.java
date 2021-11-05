package decodex.storage;

import decodex.data.exception.ModuleException;
import decodex.data.exception.ModuleManagerException;
import decodex.data.exception.RecipeException;
import decodex.data.exception.RecipeManagerException;
import decodex.data.exception.StorageException;
import decodex.modules.ModuleManager;
import decodex.modules.Module;
import decodex.recipes.Recipe;
import decodex.recipes.RecipeManager;
import decodex.ui.Ui;
import decodex.ui.messages.ErrorMessages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

// @@author Kair0s3
public class Storage {

    // Specifies the default directories for recipes.
    private static final String DEFAULT_RECIPE_DIRECTORY = "recipe";

    // Specifies the corresponding file prefixes.
    private static final String RECIPE_FILE_PREFIX = ".txt";

    // Specifies the different regexes.
    private static final String LINE_BREAK_REGEX = "\\r?\\n";
    private static final String FILENAME_EXTENSION_SPLIT_REGEX = "[.]";


    // Specifies the index for the corresponding fields.
    private static final int RECIPE_NAME_INDEX = 0;
    private static final int MODULE_NAME_INDEX = 0;
    private static final int STARTING_PARAMETER_INDEX = 1;

    // Specifies other miscellaneous constants for condition checking.
    private static final int EMPTY_LENGTH = 0;

    // Specifies the constant values.
    private static final String EMPTY_STRING = "";

    /**
     * Initializes a new Storage.
     */
    public Storage() {
    }

    /**
     * Loads all the recipes previously saved in the recipe directory.
     *
     * @param moduleManager The ModuleManager object.
     * @param recipeManager The RecipeManager object.
     * @param ui            The Ui object.
     * @throws IOException      If an error occurred when creating the recipe directory,
     *                          or reading the recipe files.
     * @throws StorageException If the recipe directory is not an actual directory,
     *                          or if it failed to list the recipe files,
     *                          or if the respective recipe file is not an actual file.
     */
    public void loadRecipesFromDirectory(ModuleManager moduleManager, RecipeManager recipeManager, Ui ui)
            throws IOException, StorageException {
        instantiateDirectoryIfNotExist(DEFAULT_RECIPE_DIRECTORY);
        File[] recipeFiles = getAllRecipeFiles();

        if (recipeFiles.length == EMPTY_LENGTH) {
            return;
        }
        ArrayList<String> failedRecipes = new ArrayList<>();
        for (File recipeFile : recipeFiles) {
            String recipeFilename = recipeFile.getName();
            String recipeName = recipeFilename.split(FILENAME_EXTENSION_SPLIT_REGEX)[RECIPE_NAME_INDEX];
            try {
                Recipe recipe = readRecipeFromFile(recipeName, recipeFile, moduleManager);
                recipeManager.addRecipe(recipe);
            } catch (IOException | ModuleException | ModuleManagerException
                    | RecipeManagerException | RecipeException err) {
                failedRecipes.add(recipeName);
            }
        }

        if (failedRecipes.isEmpty()) {
            return;
        }
        ui.printFailedToLoadFromStorageMessage(failedRecipes);
    }

    /**
     * Gets all the recipe files in the recipe directory.
     *
     * @return The list of recipe File objects.
     * @throws StorageException If it failed to list the recipe files.
     */
    private File[] getAllRecipeFiles() throws StorageException {
        File recipeDirectory = new File(DEFAULT_RECIPE_DIRECTORY);

        File[] files = recipeDirectory.listFiles();

        if (files == null) {
            throw new StorageException(ErrorMessages.FAILED_TO_LIST_FILES_MESSAGE);
        }

        File[] recipeFiles = Arrays.stream(files)
                .filter(file -> file.isFile())
                .filter(file -> {
                    String fileName = file.getName();
                    return fileName.endsWith(RECIPE_FILE_PREFIX);
                })
                .toArray(size -> new File[size]);

        return recipeFiles;
    }

    /**
     * Parses the saved recipe content and returns the Recipe.
     *
     * @param recipeFileName The name of the recipe file.
     * @param recipeContent  The contents of the recipe file.
     * @param moduleManager  The ModuleManger object.
     * @return The Recipe object.
     * @throws ModuleException        If module parameters are invalid.
     * @throws ModuleManagerException If provided module name is not an available module.
     * @throws RecipeException        If recipe name is not valid.
     */
    private Recipe parseContentToRecipe(String recipeFileName, String recipeContent, ModuleManager moduleManager)
            throws ModuleException, ModuleManagerException, RecipeException {
        Recipe recipe = new Recipe(recipeFileName);
        String[] recipeLines = recipeContent.split(LINE_BREAK_REGEX);

        for (String recipeLine : recipeLines) {
            String trimmedRecipeLine = recipeLine.trim();
            if (trimmedRecipeLine.isEmpty()) {
                continue;
            }
            String[] tokens = trimmedRecipeLine.split(" ");
            String moduleName = tokens[MODULE_NAME_INDEX];
            String[] moduleParameters = Arrays.copyOfRange(tokens, STARTING_PARAMETER_INDEX, tokens.length);
            Module module = moduleManager.selectModule(moduleName, moduleParameters);
            recipe.push(module);
        }
        return recipe;
    }

    /**
     * Reads the recipe file contents and returns the Recipe.
     *
     * @param recipeFilename The name of the recipe.
     * @param recipeFile     The recipe File object.
     * @param moduleManager  The ModuleManager object.
     * @return The Recipe object.
     * @throws IOException            If an error occurred when reading the file.
     * @throws ModuleException        If module parameters are invalid.
     * @throws ModuleManagerException If provided module name is not an available module.
     * @throws RecipeException        If recipe name is not valid.
     */
    public Recipe readRecipeFromFile(String recipeFilename, File recipeFile, ModuleManager moduleManager)
            throws IOException, ModuleException, ModuleManagerException, RecipeException {
        Path recipeFilePath = recipeFile.toPath();

        byte[] recipeContentBytes = readContentFromFile(recipeFilePath);
        String recipeContent = convertByteArrayToString(recipeContentBytes);
        Recipe loadedRecipe = parseContentToRecipe(recipeFilename, recipeContent, moduleManager);
        return loadedRecipe;
    }

    /**
     * Reads and returns the contents from the file.
     *
     * @param readFilePath The Path object of the file to be read.
     * @return The bytes of the read content.
     * @throws IOException           If an error occurred when reading the file.
     * @throws FileNotFoundException If the input file does not exist.
     */
    private byte[] readContentFromFile(Path readFilePath) throws IOException, FileNotFoundException {
        try {
            return Files.readAllBytes(readFilePath);
        } catch (FileNotFoundException err) {
            throw new FileNotFoundException(ErrorMessages.INPUT_FILE_DOES_NOT_EXIST_MESSAGE);
        } catch (IOException err) {
            throw new IOException(ErrorMessages.FILE_READ_ERROR_MESSAGE);
        }
    }

    /**
     * Saves the provided recipe into a file.
     *
     * @param recipe The recipe to be saved.
     * @throws IOException      If an error occurred when creating the file
     *                          or when writing to the file.
     * @throws StorageException If the recipe directory is not an actual directory
     *                          or if the recipe file is not an actual file.
     */
    public void saveRecipeToFile(Recipe recipe) throws IOException, StorageException {
        instantiateDirectoryIfNotExist(DEFAULT_RECIPE_DIRECTORY);

        String newRecipeFileName = recipe.getName() + RECIPE_FILE_PREFIX;

        File recipeDirectory = new File(DEFAULT_RECIPE_DIRECTORY);
        File outputRecipeFile = new File(recipeDirectory, newRecipeFileName);
        saveRecipeModulesToFile(outputRecipeFile, recipe);
    }

    /**
     * Saves the list of recipe's modules into the file.
     *
     * @param writeFile       The file to be saved to.
     * @param recipeToBeSaved The recipe to be saved.
     * @throws IOException      If an error occurred when creating the file
     *                          or when writing to the file.
     * @throws StorageException If the recipe file is not an actual file.
     */
    private void saveRecipeModulesToFile(File writeFile, Recipe recipeToBeSaved) throws IOException, StorageException {
        // To ensure only writes to file happen.
        if (writeFile.isDirectory()) {
            throw new StorageException(ErrorMessages.INVALID_RECIPE_FILE);
        }
        ArrayList<Module> modules = recipeToBeSaved.getModuleList();

        try {
            FileWriter writer = new FileWriter(writeFile);
            String formattedModuleList = formatModuleListForSaving(modules);
            writer.write(formattedModuleList);
            writer.close();
        } catch (IOException err) {
            throw new IOException(ErrorMessages.RECIPE_WRITE_FAILED_MESSAGE);
        }
    }

    /**
     * Formats the list of modules for saving to file.
     *
     * @param modules The list of modules to be saved.
     * @return The formatted list of modules.
     */
    private String formatModuleListForSaving(ArrayList<Module> modules) {
        if (modules.isEmpty()) {
            return EMPTY_STRING;
        }

        String formattedModuleList = EMPTY_STRING;
        for (Module module : modules) {
            formattedModuleList = formattedModuleList.concat(module.toString() + "\n");
        }
        return formattedModuleList;
    }

    /**
     * Deletes the saved recipe file.
     *
     * @param recipeName The name of the deleted recipe.
     * @throws IOException If an error occurred when deleting the file
     *                     or when the file failed to be deleted.
     * @throws StorageException If the recipe file is not an actual file.
     */
    public void deleteRecipeFile(String recipeName) throws IOException, StorageException {
        String newRecipeFileName = recipeName + RECIPE_FILE_PREFIX;

        File recipeDirectory = new File(DEFAULT_RECIPE_DIRECTORY);
        File outputRecipeFile = new File(recipeDirectory, newRecipeFileName);

        if (!outputRecipeFile.isFile()) {
            throw new StorageException(ErrorMessages.INVALID_RECIPE_FILE);
        }

        if (!outputRecipeFile.delete()) {
            throw new IOException(ErrorMessages.RECIPE_FILE_DELETE_FAILED_MESSAGE);
        }
    }

    /**
     * Instantiates the given directory if it does not exist yet.
     *
     * @throws IOException      If the directory failed to be created.
     * @throws StorageException If the given directory is not an actual directory.
     */
    private void instantiateDirectoryIfNotExist(String directoryName) throws IOException, StorageException {
        File directory = new File(directoryName);
        if (directory.isDirectory()) {
            return;
        }
        if (directory.isFile()) {
            // throw error if not a valid directory.
            String formattedErrorMessage = String.format(ErrorMessages.INVALID_DIRECTORY_ACCESS, directory);
            throw new StorageException(formattedErrorMessage);
        }

        boolean isSuccessful = directory.mkdir();
        if (!isSuccessful) {
            throw new IOException(ErrorMessages.DIRECTORY_INSTANTIATION_FAILED_MESSAGE + directoryName);
        }
    }

    /**
     * Converts a byte array to string value.
     *
     * @param contentBytes The bytes of the content.
     * @return The string value of the content.
     */
    private String convertByteArrayToString(byte[] contentBytes) {
        return new String(contentBytes);
    }
}
