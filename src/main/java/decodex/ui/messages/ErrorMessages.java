package decodex.ui.messages;

public class ErrorMessages {

    public static final String MISSING_ARGUMENT = "Missing argument";
    public static final String MISSING_COMMAND_TYPE = "Your command cannot be blank";
    public static final String MISSING_MODULE_NAME = "Missing module name";
    public static final String MISSING_RECIPE_NAME = "Missing recipe name";
    public static final String INPUT_EMPTY = "Your input is empty";
    public static final String UNKNOWN_COMMAND = "Unknown command, please enter a valid command";
    public static final String NO_DATA_FOUND = "No data found";
    public static final String BASE64_DECODING_FAILED_MESSAGE = "Your data does not seem to be in Base64 format";
    public static final String HEX_DECODING_FAILED_MESSAGE = "Invalid hexadecimal string";
    public static final String BINARY_DECODING_FAILED_MESSAGE = "Invalid binary string";
    public static final String EMPTY_RECIPE_MESSAGE = "The recipe is currently empty. There is no module to be "
            + "removed.";
    public static final String INVALID_RECIPE_NAME = "Recipe names can contain only alphabets, numbers and "
            + "underscores and must be non-empty.";
    public static final String DUPLICATE_RECIPE_NAME_MESSAGE = "A recipe with the given name already exists.";
    public static final String RECIPE_NOT_FOUND_MESSAGE = "The given recipe could not be found.";
    public static final String EDITING_RECIPE_NOT_FOUND_MESSAGE = "No recipe selected for editing.";
    public static final String TOO_MANY_COMMAND_ARGUMENTS = "Too many command arguments";
    public static final String TOO_FEW_COMMAND_ARGUMENTS = "Too few command arguments";
    public static final String INVALID_LIST_CATEGORY = "Invalid list category";
    public static final String UNKNOWN_MODULE_NAME = "Unknown module name";
    public static final String INVALID_ROTENCODE_PARAMETER = "The rotation offset must be an integer between "
            + "-2147483648 (MIN_INT) and 2147483647 (MAX_INT)";
    public static final String MISSING_MODULE_PARAMETERS = "Missing module parameters";
    public static final String TOO_MANY_MODULE_PARAMETERS = "Too many module parameters";
    public static final String UNKNOWN_SELECTION_CATEGORY = "Unknown selection category";
    public static final String INPUT_FILE_DOES_NOT_EXIST_MESSAGE =
            "The default input file does not exists";
    public static final String RECIPE_WRITE_FAILED_MESSAGE = "Something went wrong while writing/updating recipe file";
    public static final String FILE_READ_ERROR_MESSAGE = "Something went wrong when reading from the file";
    public static final String DIRECTORY_INSTANTIATION_FAILED_MESSAGE = "Failed to create the directory for ";
    public static final String RECIPE_FILE_DELETE_FAILED_MESSAGE = "Failed to delete the corresponding recipe file";
    public static final String FAILED_TO_LOAD_RECIPES_FILE_MESSAGE = "Failed to load following recipes into Decodex:";
    public static final String INVALID_RECIPE_COMMAND = "Invalid recipe command";
    public static final String INVALID_DIRECTORY_ACCESS = "Failed to access %s directory";
    public static final String INVALID_RECIPE_FILE = "The recipe file is not a valid file or is missing";
    public static final String FAILED_TO_LIST_FILES_MESSAGE = "Something went wrong when listing files in recipe";
    public static final String TOO_MANY_ARGUMENTS_MESSAGE = "Too many arguments";
    public static final String RECIPE_EMPTY = "There are no modules in the recipe";
}
