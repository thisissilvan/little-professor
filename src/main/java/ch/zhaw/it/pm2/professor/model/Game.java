package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.Config;
import ch.zhaw.it.pm2.professor.controller.LevelFactory;
import ch.zhaw.it.pm2.professor.controller.LevelSource;
import ch.zhaw.it.pm2.professor.controller.UserIO;
import ch.zhaw.it.pm2.professor.exception.HouseIOException;
import ch.zhaw.it.pm2.professor.exception.UserConversionException;
import ch.zhaw.it.pm2.professor.exception.UserIOException;
import ch.zhaw.it.pm2.professor.view.CliDisplay;
import ch.zhaw.it.pm2.professor.view.Display;

import java.io.FileNotFoundException;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * The Game class implements the logic of the game. It initializes the game and leads the user through the steps.
 */
public class Game extends TimerTask implements House.TimeInterface, Display.GameEndListener {

    private static final Logger LOGGER = Logger.getLogger(Game.class.getCanonicalName());

    private final House house;
    private final Display display;
    private User user;
    private final UserIO userIo;
    private int time;
    private Level currentLevel;
    private final LevelSource levelSource;
    private int levelCount = 0;
    private int totalScore;
    private boolean gameEnded = false;
    private boolean gameSuccess = false;
    private int oldScore;

    /**
     * Constructor of class House.
     * @throws HouseIOException In case House is not loading the correct file.
     * @throws FileNotFoundException In case the file can not be found.
     */
    public Game() throws HouseIOException, FileNotFoundException {
        this.house = new House(this);
        this.display = new CliDisplay(this);
        this.userIo = new UserIO();
        levelSource = new LevelFactory();
        currentLevel = levelSource.getLevels().get(levelCount); //first level of the list
        resetTimer();
    }

    @Override
    public void run() {
        update();
    }

    /**
     * Updates the timer.
     */
    private void update() {
        this.time--;
        this.house.setTime(this.time);
    }

    /**
     * Starts the game. Initializes the game by setting it up.
     * User is asked to enter a command.
     * @throws UserIOException User will be notified if their input is invalid.
     * @throws UserConversionException Will be thrown if UserConverterException is thrown.
     * @throws HouseIOException In case House is not loading the correct file.
     * @throws FileNotFoundException In case the file can not be found.
     */
    public void start() throws UserIOException, UserConversionException, HouseIOException, FileNotFoundException {
        this.display.showHouse(this.house, currentLevel);
        this.display.welcomeMessage();
        totalScore = oldScore + currentLevel.getMinPoints();
        this.user = userIo.load(display.requestUsername());
        while (true) {
            resetTimer();
            this.user.setScore(0);
            doUserCommand();
            end();
            this.display.playAgainMessage();
            resetGame();
        }
    }

    /**
     * Notifies the user about the game-end and updates his highscore.
     */
    private void end() {
        this.display.gameEndNotification(this.gameSuccess, this.user.getScore());
        highscoreCheck();
    }

    /**
     * Sets the timer to the initial value.
     */
    private void resetTimer() {
        time = (currentLevel.getRooms().length - 1) * Config.NUMBER_OF_QUESTIONS_PER_ROOM * 10;
    }

    private void resetGame() {
        user.setScore(0);
        oldScore = 0;
        resetTimer();
        resetRooms();
        levelCount = 0;
        currentLevel = levelSource.getLevels().get(levelCount);
        gameEnded = false;
        gameSuccess = false;
    }

    /**
     * Checks if the user hit their last high score.
     */
    public void highscoreCheck() {
        highscoreCheck(false);
    }

    public void highscoreCheck(boolean disableNotification) {
        int score = this.user.getScore();
        int highscore = this.user.getHighscore();
        LOGGER.fine(String.format("highscore check (previous-highscore: %s, score: %s)", highscore, score));
        if (score > this.user.getHighscore()) {
            this.user.setHighscore(score);
            if (!disableNotification) {
                this.display.newPersonalHighscoreNotification(score);
            }
        }
    }

    /**
     * Updates the house. The house display is set to its current values like state, username, high score, score, remaining time and level.
     * @throws HouseIOException In case House is not loading the correct file.
     * @throws FileNotFoundException In case the file cann not be found.
     */
    private void updateHouse() throws HouseIOException, FileNotFoundException {
        this.house.changeState(House.State.HALLWAY);
        this.house.setUsername(this.user.getName());
        this.house.setHighscore(user.getHighscore());
        this.house.setScore(user.getScore());
        this.house.setTotalScore(totalScore);
        this.house.setTime(this.time);
        this.house.setLevel(currentLevel);
    }

    /**
     * User has chosen a command and the command's validation will be checked.
     * Only existing rooms can be entered.
     * @throws HouseIOException In case House is not loading the correct file.
     * @throws FileNotFoundException In case the file cann not be found.
     */
    private void doUserCommand() throws HouseIOException, FileNotFoundException {
        if(time <= 0) {
            this.display.timeIsUp();
            gameEnded = true;
        }
        if (!this.gameEnded) {
            updateHouse();
            this.display.showHouse(this.house, currentLevel);

            Config.Command command = this.display.navigate(currentLevel);
            if (command == null) {
                doUserCommand();
            }
            if(time > 0) {
                switch (command) {
                    case HELP:
                        this.display.helpMessage();
                        doUserCommand();
                        break;
                    default:
                        moveIntoRoom(command);
                        break;
                }
            }
        }
    }

    /**
     * User moves into a room and a set of questions will appear.
     * After finishing the question set the room will be marked as completed.
     * If all rooms in this level are completed, following cases will be checked:
     * - whether the user is in the last level
     * - whether the user meets the conditions to level up
     * If the user has not completed all rooms in the current level yet, they can choose a new command.
     * @param command command that the user has chosen.
     * @throws HouseIOException In case House is not loading the correct file.
     * @throws FileNotFoundException In case the file cann not be found.
     */
    private void moveIntoRoom(Config.Command command) throws HouseIOException, FileNotFoundException {
        Room room = null;
        for (Room r : currentLevel.getRooms()) {
            if (r.getCommand() == command) {
                room = r;
            }
        }
        assert false;
        this.display.selectedRoomMessage(room);
        this.display.showRoom(room);
        startQuestionSet(room);
        room.setCompleted(true);
        if (allRoomsCompleted()) {
            if (levelCount == levelSource.getLevels().size() - 1) {//final level check
                gameSuccess = levelSuccessful();
                gameEnded = true;
            } else {
                if (levelSuccessful()) {
                    updateLevel();
                } else {
                    this.display.levelNotSuccessfulMessage();
                    gameEnded = true;
                }
            }
        }
        if (!this.gameEnded) {
            doUserCommand();
        }
    }

    /**
     * Updates the level. Timer and rooms state are reset. The level will be incremented by 1.
     * User will be notified by a message.
     */
    private void updateLevel() {
        oldScore = user.getScore();
        levelCount++;
        currentLevel = levelSource.getLevels().get(levelCount);
        totalScore = oldScore + currentLevel.getMinPoints();
        resetTimer();
        resetRooms();
        this.display.updateLevelMessage(currentLevel);
    }

    /**
     * All rooms are set to not be completed yet.
     */
    private void resetRooms() {
        for(Level level : levelSource.getLevels()) {
            for (int i = 1; i < level.getRooms().length; i++) {
                level.getRooms()[i].setCompleted(false);
            }
        }
    }

    /**
     * Checks if the conditions meet that let the user level up.
     * The conditions are:
     * - User collected 80% of max amount of points in the current level.
     * - Time is not up yet.
     * - All rooms are completed.
     * @return true if all conditions meet. Current level is passes successfully.
     */
    private boolean levelSuccessful() {
        return (user.getScore()-oldScore >= currentLevel.getMinPoints() && time >= 0 && allRoomsCompleted());
    }

    /**
     * Checks if all rooms of the current level has been completed.
     * @return true if there exist no room that is not completed.
     */
    private boolean allRoomsCompleted() {
        for (int i = 1; i < currentLevel.getRooms().length; i++) {
            if(!currentLevel.getRooms()[i].isCompleted()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Starts the question set and checks the correctness of the users answer inputs.
     * According points will be contributed.
     * @param room current room where the user is located at the moment.
     */
    private void startQuestionSet(Room room) {
        for (int i = 0; i < Config.NUMBER_OF_QUESTIONS_PER_ROOM; i++) {
            if (this.display.askQuestionsMessage(room, currentLevel).equals(currentLevel.getAnswer())) {
                user.setScore(user.getScore() + 1);
            }
            this.display.showAnswer(room, currentLevel);
        }
    }

    public int getTime() {
        return this.time;
    }

    /**
     * Ends the game.
     */
    public void onGameEnd() throws UserIOException {
        highscoreCheck(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (this.user != null) {
            this.userIo.store(this.user);
        }
        System.exit(0);
    }
}