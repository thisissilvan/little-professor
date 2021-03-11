package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.exception.UserIOException;
import ch.zhaw.it.pm2.professor.Config;
import ch.zhaw.it.pm2.professor.model.House;
import ch.zhaw.it.pm2.professor.model.Level;
import ch.zhaw.it.pm2.professor.model.Room;

/**
 * We use an Interface for our prototype.
 * At later stages it would be easier to replace the DisplayIO, which in the prototype is with berxyTextIO,
 * to a JavaFX-View (or another View).
 */
public interface Display {

    /**
     * Method prints out the house to the terminal.
     * @param house a house object
     * @param level a level object
     */
    void showHouse(House house, Level level);

    /**
     * Prints the welcome message to the terminal.
     */
    void welcomeMessage();

    /**
     * Message the user to ask for the username. The input is then passed over to the parser which checks
     * if the input is valid or not.
     * @return if the input is valid, the username as String gets returned
     */
    String requestUsername();

    /**
     * Method that returns a Config.Command from a specific level, the user can navigate with the given
     * commands.
     * @param level the actual level-object
     * @return the command where the user wants to go as a Config.Command object
     */
    Config.Command navigate(Level level);

    /**
     * Informs the user, when an input is invalid.
     */
    void invalidInputMessage();

    /**
     * Informs the user when the time is up.
     */
    void timeIsUp();

    /**
     * Method produces a String to ask for the next user input.
     * @return  the user input which the user gives in
     */
    String getNextUserInput();

    /**
     * The method checks if the user types in quit. He can do this after every turn.
     * Calls exitApplication-method if the user wants to quit the game.
     * @param userInput the userInput to quit the game
     */
    void checkForQuitCommand(String userInput);

    /**
     * Room selecting method. Parameters are Room and Level-objects.
     * The user can then choose a valid Room from this Level.
     * @param room  a room inside this level
     */
    void selectedRoomMessage(Room room);

    /**
     * Producing a help-message to give some information to the user.
     */
    void helpMessage();

    /**
     * Ask the user a question and read the answer/input from the terminal.
     * @param room the room in which the user is
     * @param level the level in which the user is
     * @return the answer the user types in
     */
    String askQuestionsMessage(Room room, Level level);

    /**
     * Show the answer of the question.
     *
     * @param room  the room in which the user is
     * @param level the level in which the user is
     */
    void showAnswer(Room room, Level level);

    /**
     * Prints the room.
     * @param room a valid room inside the level
     *
     */
    void showRoom(Room room);

    /**
     * Update the level. If the user finished a level this message gets printed.
     * @param level the next level
     */
    void updateLevelMessage(Level level);

    /**
     * Notify the user if he finished the game or didn't complete the game and show the score to the user.
     * @param success if completed, this will be true
     * @param score   the score the player reached during playing
     */
    void gameEndNotification(boolean success, int score);

    /**
     * If a user reaches a new personal highscore, this message gets prompted out.
     * @param highscore the int value of the personal highscore
     */
    void newPersonalHighscoreNotification(int highscore);

    /**
     * A message asking the user if he wants to play again after finishing.
     */
    void playAgainMessage();

    /**
     * Message the user if he/she doesn't reaches the end of the level because of not reached
     * the passmark.
     */
    void levelNotSuccessfulMessage();

    interface GameEndListener {
        void onGameEnd() throws UserIOException;
    }
}
