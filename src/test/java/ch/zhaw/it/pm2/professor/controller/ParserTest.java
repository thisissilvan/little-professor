package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static ch.zhaw.it.pm2.professor.Config.Command.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ParserTest.
 * Test-methods are described here.
 */
class ParserTest {

    List<Config.Command> acceptedCommands;
    Parser parser;
    private static final String VALID_INPUT_DOWN_TEST_TRIM = "  down     ";
    private static final String VALID_INPUT_GO = "go";
    private static final String VALID_INPUT_HELP = "help";
    private static final String VALID_INPUT_LEFT_TEST_TRIM = "   left               ";
    private static final String VALID_INPUT_QUIT = "quit";
    private static final String VALID_INPUT_RIGHT = "right";
    private static final String VALID_INPUT_UNKNOWN = "?";
    private static final String VALID_INPUT_UP_TEST_TRIM = "     up  ";
    private static final String INVALID_INPUT_ONE = "SomethingSeemsToBeWrong";
    private static final String INVALID_INPUT_TWO = "d0wn";
    private static final String EMPTY_STRING = "";

    private static final String VALID_USERNAME = "Cellestine";
    private static final String VALID_USERNAME_TEST_TRIM_15_CHARS = " Cellestine    ";
    private static final String VALID_USERNAME_MIN_CHARS = "Tim";
    private static final String VALID_USERNAME_MAX_CHARS = "ThisUsername";
    private static final String USERNAME_TOO_SHORT = "Jo";
    private static final String USERNAME_TOO_LONG = "ThisUsernameHasToManyCharsAndIsNotAccepted";

    @BeforeEach
    void setUp() {
        parser = new Parser();
        acceptedCommands = new ArrayList<>();
        acceptedCommands.add(DOWN);
        acceptedCommands.add(GO);
        acceptedCommands.add(HELP);
        acceptedCommands.add(LEFT);
        acceptedCommands.add(QUIT);
        acceptedCommands.add(RIGHT);
        acceptedCommands.add(UNKNOWN);
        acceptedCommands.add(UP);
        assertNotNull(acceptedCommands);
    }

    /**
     * Test parseInput:
     * Test a valid input with spare spaces. Method should parse the input to the command DOWN and trim
     * the spare spaces.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidDownInputWithSpaces() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands, VALID_INPUT_DOWN_TEST_TRIM);
        assertEquals(DOWN, actualCommand);
    }

    /**
     * Test parseInput:
     * Test a valid input with spare spaces. Method should parse the input to the command LEFT and trim
     * the spare spaces.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidInputLeftWithSpaces() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands, VALID_INPUT_LEFT_TEST_TRIM);
        assertEquals(LEFT, actualCommand);
    }

    /**
     * Test parseInput:
     * Test a valid input with spare spaces. Method should parse the input to the command UP.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidInputUpWithSpaces() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands, VALID_INPUT_UP_TEST_TRIM);
        assertEquals(UP, actualCommand);
    }

    /**
     * Test parseInput:
     * Test a valid input without spare spaces. Method should parse the input to the command GO.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidInputGo() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands, VALID_INPUT_GO);
        assertEquals(GO, actualCommand);
    }

    /**
     * Test parseInput:
     * Test a valid input without spare spaces. Method should parse the input to the command HELP.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidInputHelp() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands, VALID_INPUT_HELP);
        assertEquals(HELP, actualCommand);
    }

    /**
     * Test parseInput:
     * Test a valid input without spare spaces. Method should parse the input to the command QUIT.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidInputQuit() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands, VALID_INPUT_QUIT);
        assertEquals(QUIT, actualCommand);
    }

    /**
     * Test parseInput:
     * Test a valid input without spare spaces. Method should parse the input to the command RIGHT.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidInputRight() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands, VALID_INPUT_RIGHT);
        assertEquals(RIGHT, actualCommand);
    }

    /**
     * Test parseInput:
     * Test a valid input without spare spaces. Method should parse the String "?" input
     * to the command UNKNOWN.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidInputUnknown() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands, VALID_INPUT_UNKNOWN);
        assertEquals(UNKNOWN, actualCommand);
    }

    /**
     * Test parseInput:
     * Invalid Input which is not in the List with acceptedCommands. Should throw a InvalidInputException.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseInvalidInput() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseInput(acceptedCommands, INVALID_INPUT_ONE));
    }

    /**
     * Test parseInput:
     * Invalid Input which is not in the List with acceptedCommands. Should throw a InvalidInputException.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseAnotherInvalidInput() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseInput(acceptedCommands, INVALID_INPUT_TWO));
    }

    /**
     * Test parseInput:
     * EMPTY_STRING as input. Should throw a InvalidInputException.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseEmptyString() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseInput(acceptedCommands, EMPTY_STRING));
    }

    /**
     * Test parseInput:
     * null-object as input, should throw a NullPointerException.
     *
     * @throws NullPointerException
     */
    @Test
    void testParseNullInput() throws NullPointerException {
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> parser.parseInput(acceptedCommands, null));
    }

    /**
     * Test parseName:
     * Valid username, should pass.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidName() throws InvalidInputException {
        String actualUsername = parser.parseName(VALID_USERNAME);
        assertEquals("Cellestine", actualUsername);
    }

    /**
     * Test parseName:
     * Valid username, minimum chars, should pass.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidNameMinChars() throws InvalidInputException {
        String actualUsername = parser.parseName(VALID_USERNAME_MIN_CHARS);
        assertEquals("Tim", actualUsername);
    }

    /**
     * Test parseName:
     * Valid username, maximum chars, should pass.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidNameMaxChars() throws InvalidInputException {
        String actualUsername = parser.parseName(VALID_USERNAME_MAX_CHARS);
        assertEquals("ThisUsername", actualUsername);
    }

    /**
     * Test parseName:
     * Valid username, accidentally spare spaces, should trim the spare spaces and should pass.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseValidNameTrimAccidentallyGivenSpaces() throws InvalidInputException {
        String actualUsername = parser.parseName(VALID_USERNAME_TEST_TRIM_15_CHARS);
        assertEquals("Cellestine", actualUsername);
    }

    /**
     * Test parseName:
     * Username too long. Should throw an InvalidInputException.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseTooLongName() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseName(USERNAME_TOO_LONG));
    }

    /**
     * Test parseName:
     * Username too short. Should throw an InvalidInputException.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseTooShortName() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseName(USERNAME_TOO_SHORT));
    }

    /**
     * Test parseName:
     * EMPTY_STRING. Should throw an InvalidInputException.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseInvalidNameEmptyString() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseName(EMPTY_STRING));
    }

    /**
     * Test parseName:
     * Invalid input too short. Should throw an InvalidInputException.
     *
     * @throws InvalidInputException
     */
    @Test
    void testParseInvalidNameTwoChars() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseName("Jo"));
    }

    /**
     * Test parseName:
     * null-object. Should throw a NullPointerExcption.
     *
     * @throws NullPointerException
     */
    @Test
    void testParseInvalidNameNullObject() throws NullPointerException {
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> parser.parseName(null));
    }
}