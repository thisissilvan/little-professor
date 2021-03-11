package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.controller.Parser;
import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.exception.UserIOException;
import ch.zhaw.it.pm2.professor.Config;
import ch.zhaw.it.pm2.professor.model.House;
import ch.zhaw.it.pm2.professor.model.Level;
import ch.zhaw.it.pm2.professor.model.Room;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/**
 * Prints the output to the terminal.
 * The class Display could be replaced by a GUI if wanted.
 * <p>
 * All the methods in this class are getting called from another class, so this class only represents the IO.
 */
public class CliDisplay implements Display {
    private TextIO textIO;
    private TextTerminal<?> terminal;
    private Parser parser;
    private GameEndListener gameEndListener;

    /**
     * Constructor of the class DisplayIO. It initializes the Terminal, TextIO and a Config-Object.
     * @param gameEndListener used by the display, to end the game
     */
    public CliDisplay(GameEndListener gameEndListener) {
        this.textIO = TextIoFactory.getTextIO();
        this.terminal = textIO.getTextTerminal();
        this.parser = new Parser();
        this.gameEndListener = gameEndListener;
    }

    @Override
    public void showHouse(House house, Level level) {
        this.terminal.println(house.printLevel(level));
    }

    @Override
    public void welcomeMessage() {
        this.terminal.println("The little Professor will help you to train your math skills while playing.");
    }


    @Override
    public String requestUsername() {
        this.terminal.println("Please enter your username.\nBetween " + Config.MIN_CHARS_USERNAME + " - " + Config.MAX_CHARS_USERNAME + " characters");
        boolean validName;
        String username;
        do {
            username = getNextUserInput();
            try {
                this.parser.parseName(username);
                validName = true;
            } catch (InvalidInputException e) {
                terminal.println(e.getMessage());
                validName = false;
            }
        } while (!validName);
        return username;
    }

    @Override
    public Config.Command navigate(Level level) {
        Config.Command command = null;
        this.terminal.println("You are in the Hallway right now. Type any of the following commands to enter a room.\n");
        this.terminal.println("LEFT: left\nUP: up\nRIGHT: right\nDOWN: down\nHELP: help\nQUIT: quit\n");
        String input = getNextUserInput();
        try {
            command = this.parser.parseInput(level.getValidCommandsList(), input.toLowerCase());
        } catch (InvalidInputException e) {
            invalidInputMessage();
        }
        return command;
    }

    @Override
    public void invalidInputMessage() {
        this.terminal.print("The given input is invalid. Please enter one of the proposed commands.\n");
    }

    @Override
    public void timeIsUp() {
        this.terminal.print("\nThe time is up.\nYour score will be written to the highscore file and the game " +
                "will end here.\n");
    }

    @Override
    public String getNextUserInput() {
        this.terminal.print("\nEnter \"quit\" to quit.\n");
        String userInput = this.textIO.newStringInputReader().read();
        checkForQuitCommand(userInput);
        return userInput;
    }

    @Override
    public void checkForQuitCommand(String userInput) {
        Config.Command[] quitCommandList = {Config.Command.QUIT};
        try {
            this.parser.parseInput(quitCommandList, userInput);
            exitApplication();
        } catch (InvalidInputException e) {
            // so we don't quit
        }
    }

    @Override
    public void selectedRoomMessage(Room room) {
        this.terminal.println("\nYou entered the room with the mission to solve questions of the operation " + room.getOperation().toString() +
                ".\nFinish before the time runs out!");
    }

    @Override
    public void helpMessage() {
        this.terminal.println("\nMove into a room to start the question set and gain enough points to win this level.\nWatch out for the timer!");
        this.terminal.println("For further information check out the game instruction in our Wiki by the following link:");
        this.terminal.println("https://github.zhaw.ch/pm2-it19azh-ehri-fame-muon/gruppe05-einhoerner-little-professor/wiki\n");
    }

    @Override
    public String askQuestionsMessage(Room room, Level level) {
        this.terminal.println("Solve: " + level.getQuestion(room));
        this.terminal.print("Your answer:");
        return this.textIO.newStringInputReader().read();
    }

    @Override
    public void showAnswer(Room room, Level level) {
        this.terminal.println("Solution: " + level.getAnswer());
    }

    @Override
    public void showRoom(Room room) {
        this.terminal.println(room.toString());
    }

    @Override
    public void updateLevelMessage(Level level) {
        this.terminal.println("_______________________________________________________________________________________________\n\n\n\n");
        this.terminal.println("You finished this level successfully. Welcome to level " + level.getName());
        this.terminal.println("The timer is reset. Try to gain " + (level.getRooms().length-1)*4 + " additional points to get to the next level.");
    }

    @Override
    public void gameEndNotification(boolean success, int score) {
        this.terminal.println("_______________________________________________________________________________________________\n");
        if (success) {
            this.terminal.println("Congratulations! You finished the game successfully with the following score: " + score);
        } else {
            this.terminal.println("Unfortunately you could not successfully complete the game with a score of " + score + ".");
        }
    }

    @Override
    public void levelNotSuccessfulMessage() {
        this.terminal.println("\nYou did not collect enough points to pass this level.\n");
    }

    @Override
    public void newPersonalHighscoreNotification(int highscore) {
        this.terminal.println("_______________________________________________________________________________________________\n");
        this.terminal.println("YOU ACHIEVED A NEW PERSONAL HIGHSCORE: " + highscore);
    }

    @Override
    public void playAgainMessage() {
        Config.Command command = null;
        Config.Command[] yesCommandList = {Config.Command.YES};
        while (command == null) {
            terminal.println("_______________________________________________________________________________________________\n");
            terminal.println("Would you like to play again? ('y' for yes)");
            String input = getNextUserInput();
            try {
                command = this.parser.parseInput(yesCommandList, input);
            } catch (InvalidInputException e) {
                // ask again
            }
        }
    }

    /**
     * If this method gets called, the user gets informed that the Application gets closed after 5 seconds.
     */
    private void exitApplication() {
        this.terminal.println("\nThank you for playing little-professor today. The Application closes in 5 seconds and your highscore will be saved. Goodbye.");
        try {
            this.gameEndListener.onGameEnd();
        } catch (UserIOException e) {
            this.terminal.println("Game could not be ended, because user could not be saved. Check if everything is right with the user-files");
            e.printStackTrace();
        }
    }
}
