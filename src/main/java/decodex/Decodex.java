package decodex;

import decodex.commands.Command;
import decodex.commands.ExitCommand;
import decodex.data.DataManager;
import decodex.data.exception.CommandException;
import decodex.data.exception.DataManagerException;
import decodex.data.exception.ModuleException;
import decodex.data.exception.ParserException;
import decodex.data.exception.UnknownModuleException;
import decodex.modules.ModuleManager;
import decodex.parser.Parser;
import decodex.ui.Ui;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Decodex {

    public static Logger logger = Logger.getLogger(Decodex.class.getName());


    /**
     * Necessary objects to be initialized for Decodex to work properly.
     */
    private static DataManager dataManager;
    private static ModuleManager moduleManager;
    private static Parser parser;
    private static Ui ui;

    public Decodex() {
        initDecodex();
    }

    /**
     * Initializes the necessary Objects for Decodex.
     */
    public static void initDecodex() {
        logger.setLevel(Level.INFO);
        dataManager = new DataManager();
        moduleManager = new ModuleManager();
        parser = new Parser();
        ui = new Ui();
    }

    /**
     * Decodex entry-point for the java.decodex.Decodex application.
     */
    public static void main(String[] args) {
        new Decodex().run();
    }

    public void run() {
        ui.printGreeting();

        Command command = null;

        do {
            String userInput = ui.readInput();
            logger.fine("User input: " + userInput);
            try {
                command = parser.parseCommand(userInput);
                assert command != null : "Command should not be null";
                command.run(dataManager, moduleManager, ui);
            } catch (ParserException | CommandException | UnknownModuleException
                    | DataManagerException | ModuleException err) {
                ui.printError(err);
                logger.fine(err.getMessage());
            }
        } while (!(command instanceof ExitCommand));
    }
}
