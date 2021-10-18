package decodex.ui;

import decodex.ui.messages.RegularMessages;
import java.util.Scanner;

/**
 * The Ui class handles all user input and message output ot the console.
 */
public class Ui {

    private static final String PROMPT_HEADER = "Decodex > ";
    private static final String SUCCESS_ICON = "[+]";
    private static final String ERROR_ICON = "[x]";
    private static final String INPUT_PREFIX = "Input:";
    private static final String OUTPUT_PREFIX = "Output:";

    private final Scanner in;

    public Ui() {
        in = new Scanner(System.in);
    }

    /**
     * Prints the greeting message.
     */
    public void printGreeting() {
        System.out.println(RegularMessages.GREETING);
    }

    /**
     * Prints the goodbye message.
     */
    public void printGoodbye() {
        System.out.println(RegularMessages.GOODBYE);
    }

    /**
     * Reads user input from console.
     *
     * @return String of the user input.
     */
    public String readInput() {
        printPromptHeader();
        return in.nextLine();
    }

    /**
     * Prints the read user input.
     *
     * @param input The user input read.
     */
    public void printInput(String input) {
        assert input != null : "Input should not be null";
        assert !input.isBlank() : "Input should not be empty";
        printSuccess(String.format("%s \"%s\"", INPUT_PREFIX, input));
    }

    /**
     * Prints the output after data manipulation.
     *
     * @param output String output of the data.
     */
    public void printOutput(String output) {
        assert output != null : "Output should not be null";
        printSuccess(String.format("%s \"%s\"", OUTPUT_PREFIX, output));
    }

    /**
     * Prints the list of available modules.
     *
     * @param moduleList String of the list of available modules.
     */
    public void printModuleList(String moduleList) {
        assert moduleList != null : "Module list should not be null";
        assert !moduleList.isBlank() : "Module list should not be empty";
        System.out.println(RegularMessages.LIST_MODULES + "\n" + moduleList);
    }

    /**
     * Prints the list of available commands.
     *
     * @param commandList String of the list of available commands.
     */
    public void printCommandList(String commandList) {
        assert commandList != null : "Command list should not be null";
        assert !commandList.isBlank() : "Commmand list should not be empty";
        System.out.println(RegularMessages.LIST_COMMANDS + "\n" + commandList);
    }

    /**
     * Prints a message with a success status icon.
     *
     * @param message Success message
     */
    public void printSuccess(String message) {
        assert message != null : "Message should not be null";
        assert !message.isBlank() : "Message should not be empty";
        System.out.println(SUCCESS_ICON + " " + message);
    }

    /**
     * Prints the error message from the thrown exception with an error icon.
     *
     * @param exception The thrown exception.
     */
    public void printError(Exception exception) {
        assert exception.getMessage() != null : "Exception message should not be null";
        assert !exception.getMessage().isBlank() : "Exception message should not be empty";
        System.out.println(ERROR_ICON + " " + exception.getMessage());
    }

    /**
     * Prints the prompt header.
     */
    private void printPromptHeader() {
        System.out.print(PROMPT_HEADER);
    }
}
