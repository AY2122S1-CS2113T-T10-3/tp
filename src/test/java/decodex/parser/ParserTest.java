package decodex.parser;

import decodex.logic.Command;
import decodex.logic.commands.ExitCommand;
import decodex.commands.InputCommand;
import decodex.commands.ListCommand;
import decodex.commands.ResetCommand;
import decodex.commands.SelectCommand;
import decodex.data.exception.CommandException;
import decodex.data.exception.ParserException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    Parser parser = new Parser();

    @Test
    public void getCommandType_stringWithNoSeparator_expectCommandTypeString() throws ParserException {
        String testInput = "test";
        String expectedOutput = "test";
        String commandType = parser.getCommandType(testInput);
        assertEquals(expectedOutput, commandType);
    }

    @Test
    public void getCommandType_stringWithOneSpaceSeparator_expectCommandTypeString() throws ParserException {
        String testInput = "test data";
        String expectedOutput = "test";
        String commandType = parser.getCommandType(testInput);
        assertEquals(expectedOutput, commandType);
    }

    @Test
    public void getUserArguments_userInputWithOneSpaceSeparator_expectOneArgumentString() throws ParserException {
        String testInput = "test data";
        String expectedOutput = "data";
        String arguments = parser.getInputString(testInput);
        assertEquals(expectedOutput, arguments);
    }


    /* The JUnit test methods below are for testing "normal" usage flows for the respective commands.*/
    @Test
    public void parseCommand_userInputSpecifyingValidData_expectInputCommand() throws ParserException,
            CommandException {
        String userInput = "input dummyData";
        Command command = parser.parseCommand(userInput);
        assertTrue(command instanceof InputCommand);
    }


    @Test
    public void parseCommand_userInputSpecifyingExit_expectExitCommand() throws ParserException, CommandException {
        String userInput = "exit";
        Command command = parser.parseCommand(userInput);
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    public void parseCommand_userInputSpecifyingValidListBlank_expectListCommand() throws ParserException,
            CommandException {
        String userInput = "list";
        Command command = parser.parseCommand(userInput);
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void parseCommand_userInputSpecifyingValidListModules_expectListCommand() throws ParserException,
            CommandException {
        String userInput = "list modules";
        Command command = parser.parseCommand(userInput);
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void parseCommand_userInputSpecifyingValidListRecipes_expectListCommand() throws ParserException,
            CommandException {
        String userInput = "list recipes";
        Command command = parser.parseCommand(userInput);
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void parseCommand_userInputSpecifyingValidListSpaces_expectListCommand() throws ParserException,
            CommandException {
        String userInput = "list      modules     ";
        Command command = parser.parseCommand(userInput);
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void parseCommand_userInputSpecifyingValidListAdditionalArguments_expectListCommand() {
        String userInput = "list modules a";
        assertThrows(CommandException.class, () -> parser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_userInputSpecifyingReset_expectResetCommand() throws ParserException, CommandException {
        String userInput = "reset";
        Command command = parser.parseCommand(userInput);
        assertTrue(command instanceof ResetCommand);
    }

    @Test
    public void parseCommand_userInputSpecifyingValidSelectModule_expectSelectCommand() throws ParserException,
            CommandException {
        String userInput = "select module base64encode";
        Command command = parser.parseCommand(userInput);
        assertTrue(command instanceof SelectCommand);
    }
}
