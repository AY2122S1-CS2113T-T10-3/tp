package decodex.parser;

import decodex.commands.Command;
import decodex.commands.DataCommand;
import decodex.commands.ExitCommand;
import decodex.commands.ListCommand;
import decodex.commands.ResetCommand;
import decodex.commands.SelectCommand;
import decodex.data.exception.ParserException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    Parser parser = new Parser();

    @Test
    void getCommandType_stringWithNoSeparator_expectCommandTypeString() throws ParserException {
        String testInput = "test";
        String expectedOutput = "test";

        String commandType = parser.getCommandType(testInput);

        assertEquals(expectedOutput, commandType);
    }

    @Test
    void getCommandType_stringWithOneSpaceSeparator_expectCommandTypeString() throws ParserException {
        String testInput = "test data";
        String expectedOutput = "test";

        String commandType = parser.getCommandType(testInput);

        assertEquals(expectedOutput, commandType);
    }

    @Test
    void getUserArguments_userInputWithOneSpaceSeparator_expectOneArgumentString() throws ParserException {
        String testInput = "test data";
        String expectedOutput = "data";

        String arguments = parser.getUserArgument(testInput);

        assertEquals(expectedOutput, arguments);
    }


    /* The JUnit test methods below are for testing "normal" usage flows for the respective commands.*/
    @Test
    void parseCommand_userInputSpecifyingValidData_expectDataCommand() throws ParserException {
        String userInput = "data dummyData";

        Command command = parser.parseCommand(userInput);

        assertTrue(command instanceof DataCommand);
    }


    @Test
    void parseCommand_userInputSpecifyingExit_expectExitCommand() throws ParserException {
        String userInput = "exit";

        Command command = parser.parseCommand(userInput);

        assertTrue(command instanceof ExitCommand);
    }

    @Test
    void parseCommand_userInputSpecifyingValidData_expectListCommand() throws ParserException {
        String userInput = "list";

        Command command = parser.parseCommand(userInput);

        assertTrue(command instanceof ListCommand);
    }

    @Test
    void parseCommand_userInputSpecifyingReset_expectResetCommand() throws ParserException {
        String userInput = "reset";

        Command command = parser.parseCommand(userInput);

        assertTrue(command instanceof ResetCommand);
    }

    @Test
    void parseCommand_userInputSpecifyingValidSelect_expectSelectCommand() throws ParserException {
        String userInput = "select dummyMod";

        Command command = parser.parseCommand(userInput);

        assertTrue(command instanceof SelectCommand);
    }
}
