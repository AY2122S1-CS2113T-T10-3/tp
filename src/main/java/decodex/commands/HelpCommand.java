package decodex.commands;

import decodex.data.DataManager;
import decodex.modules.ModuleManager;
import decodex.ui.Ui;

public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    private static final String COMMAND_DESCRIPTION = "Shows a list of all available commands";

    private static final String LIST_COMMANDS = "Here are the list of supported commands:";

    private static final int COMMAND_WORD_INDEX = 0;
    private static final int COMMAND_DESCRIPTION_INDEX = 1;

    private static String[][] COMMAND_LIST = {
            {ListCommand.COMMAND_WORD, ListCommand.COMMAND_DESCRIPTION},
            {InputCommand.COMMAND_WORD, InputCommand.COMMAND_DESCRIPTION},
            {SelectCommand.COMMAND_WORD, SelectCommand.COMMAND_DESCRIPTION},
            {ResetCommand.COMMAND_WORD, ResetCommand.COMMAND_DESCRIPTION},
            {HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_DESCRIPTION},
            {ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_DESCRIPTION},
    };

    public HelpCommand() {
        super();
    }

    @Override
    public void run(DataManager dataManager, ModuleManager moduleManager, Ui ui) {
        assert COMMAND_LIST.length > 0 : "Number of commands should be greater than 0";

        StringBuilder commandListString = new StringBuilder();
        int maxNameWidth = 0;

        // Find number of characters of the longest command
        for (String[] command : COMMAND_LIST) {
            assert command.length == 2 : "Each command should have a command word and description";
            String commandWord = command[COMMAND_WORD_INDEX];
            if (commandWord.length() > maxNameWidth) {
                maxNameWidth = commandWord.length();
            }
        }

        // Prepare and format list of commands
        for (String[] command : COMMAND_LIST) {
            String commandWord = command[COMMAND_WORD_INDEX];
            String commandDescription = command[COMMAND_DESCRIPTION_INDEX];
            commandListString.append(String.format("  %-" + maxNameWidth + "s - %s\n",
                    commandWord, commandDescription));
        }

        printCommandList(commandListString.toString());
    }

    /**
     * Prints the list of available commands.
     *
     * @param commandList String of the list of available commands.
     */
    public void printCommandList(String commandList) {
        assert commandList != null : "Command list should not be null";
        assert !commandList.isBlank() : "Commmand list should not be empty";
        System.out.println(LIST_COMMANDS + "\n" + commandList);
    }
}
