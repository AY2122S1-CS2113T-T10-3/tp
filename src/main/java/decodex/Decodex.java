package decodex;

import decodex.commands.Command;
import decodex.commands.ExitCommand;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.DataManagerException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.UnknownModuleException;
import decodex.modules.ModuleManager;
import decodex.parser.Parser;
import decodex.data.exception.ParserException;
import decodex.ui.Ui;

import java.util.Scanner;

public class Decodex {

    /**
     * Necessary objects to be initialized for Decodex to work properly.
     */
    private static DataManager dataManager;
    private static ModuleManager moduleManager;
    private static Parser parser;
    private static Scanner in;
    private static Ui ui;


    /**
     * Logo to be changed - possibly to a welcome message instead for Decodex.
     */
    private static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    public Decodex() {
        printGreeting();
        initDecodex();
    }

    /**
     * Initializes the necessary Objects for Decodex.
     */
    public static void initDecodex() {
        dataManager = new DataManager();
        moduleManager = new ModuleManager();
        parser = new Parser();
        in = new Scanner(System.in);
        ui = new Ui();
    }

    /**
     * Decodex entry-point for the java.decodex.Decodex application.
     */
    public static void main(String[] args) {
        new Decodex().run();
    }

    public void run() {
        Command command = null;

        do {
            printPromptHeader();
            String userInput = in.nextLine();

            try {
                command = parser.parseCommand(userInput);
                command.run(dataManager, moduleManager, ui);
            } catch (ParserException | CommandException | UnknownModuleException
                    | DataManagerException | ModuleException err) {
                printErrorMessage(err.getMessage());
            }
        } while (!(command instanceof ExitCommand));

        System.exit(0);
    }

    /**
     * Prints the greeting message.
     */
    public static void printGreeting() {
        System.out.println("Hello from\n" + LOGO);
    }

    /**
     * Prints the prompt header.
     */
    public static void printPromptHeader() {
        System.out.print("Decodex > ");
    }

    /**
     * Prints the handled error messages.
     *
     * @param errorMessage The respective error message.
     */
    public static void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }
}
