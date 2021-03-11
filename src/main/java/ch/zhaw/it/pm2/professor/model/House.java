package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.exception.HouseIOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Represents the house, with its appearance represented in a string array
 */
public class House {
    private String[] house;
    private State state;
    private final TimeInterface timeSource;

    private static final String USER_FIELD = "%USER________%";
    private static final String TIME_FIELD = "%TIME%";
    private static final String HIGHSCORE_FIELD = "%HIGHSCORE%";
    private static final String SCORE_FIELD = "%S%";
    private static final String TOTAL_SCORE_FIELD = "%T%";
    private static final String LEVEL_FIELD = "%LEVEL%";
    private static final int LINES_EMPTYHOUSE = 21;

    /**
     * House constructor. A TimeInterface is given to the constructor.
     * @param timeSource TimeInterface timeSource
     */
    public House(TimeInterface timeSource) throws HouseIOException, FileNotFoundException {
        this.state = State.ENTRANCE;
        this.timeSource = timeSource;
        init();
    }

    public State getState() {
        return this.state;
    }

    /**
     * Method init. The class init tries to read a State-file. If it can not be found,
     * a FileNotFoundException gets thrown.
     */
    public void init() throws HouseIOException, FileNotFoundException {
        this.house = new String[LINES_EMPTYHOUSE];
        File file = new File(this.state.getFilePath());
        if (!file.canRead() || !file.isFile()) {
            throw new FileNotFoundException("The house-file can not be found in your system." +
                    "Please check if the State-files exists on your computer.");
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(this.state.getFilePath()));
            String row;
            int line = 0;
            while ((row = in.readLine()) != null) {
                this.house[line] = row;
                line++;
            }
        } catch (IOException e) {
            throw new HouseIOException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new HouseIOException(e);
                }
            }
        }
    }

    /**
     * Change the location, where the current state or view of the game is at.
     * Because this place may look not the same, the appearance must be reinitialized into the string-array.
     *
     * @param newState new state
     */
    public void changeState(State newState) throws HouseIOException, FileNotFoundException {
        this.state = newState;
        init();
    }

    /**
     * Same as printLevelAsArray but with line-breaks instead of the array.
     *
     * @param level is relevant for the rooms
     * @return the two dimensional house representation
     */
    public String printLevel(Level level) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : printLevelAsArray(level)) {
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Write time and rooms to the string-array and return the result.
     *
     * @param level is relevant for the rooms
     * @return the two dimensional house representation
     */
    public String[] printLevelAsArray(Level level) {
        if (this.state == State.HALLWAY) {
            setTimeInMatrix(getTime());
            addRoomsToMatrix(level);
        }
        return this.house;
    }

    /**
     * Tells the rooms, to print themselves onto the string[] representation of the house.
     */
    private void addRoomsToMatrix(Level level) {
        for(Room room : level.getRooms()) {
            room.addToHouse(this.house);
        }
    }

    private void setTimeInMatrix(int time) {
        this.replaceField(TIME_FIELD, String.valueOf(time));
    }

    /**
     * Retrieves the time from the time-source (TimeInterface).
     */
    private int getTime() {
        if (this.timeSource == null) {
            throw new NullPointerException("The house has no time-source.");
        }
        try {
            return this.timeSource.getTime();
        } catch (Exception e) {
            throw new RuntimeException("This should not be possible.");
        }
    }

    /**
     * Generic method replacinc values in the house-matrix.
     */
    private void replaceField(String field, String value) {
        for (int i = 0; i < this.house.length; i++) {
            while (value.length() < field.length()) {
                value = value + " ";
            }
            house[i] = house[i].replace(field, value);
        }
    }

    /**
     * This enum is used, for the house. With its Help, it can tell if it should display the entrance or the rooms.
     */
    public enum State {
        ENTRANCE("./src/main/resources/house/entrance.txt"),
        HALLWAY("./src/main/resources/house/empty-house.txt");

        String filePath;

        State(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
    }

    public interface TimeInterface {
        int getTime();
    }

    /**
     * Replace the username in the house-matrix.
     * @param name to fill in
     */
    public void setUsername(String name) {
        replaceField(USER_FIELD, name);
    }

    /**
     * Replace the time in the house-matrix.
     * @param time to fill in
     */
    public void setTime(int time) {
        replaceField(TIME_FIELD, Integer.valueOf(time).toString());
    }

    /**
     * Replace the highscore in the house-matrix.
     * @param highscore to fill in
     */
    public void setHighscore(int highscore) {
        this.replaceField(HIGHSCORE_FIELD, String.valueOf(highscore));
    }

    /**
     * Replace the score in the house-matrix.
     *
     * @param score to fill in
     */
    public void setScore(int score) {
        this.replaceField(SCORE_FIELD, String.valueOf(score));
    }

    /**
     * Replace the total score in the house-matrix.
     *
     * @param totalScore to fill in
     */
    public void setTotalScore(int totalScore) {
        this.replaceField(TOTAL_SCORE_FIELD, String.valueOf(totalScore));
    }

    /**
     * Replace the level in the house-matrix.
     *
     * @param level to fill in
     */
    public void setLevel(Level level) {
        this.replaceField(LEVEL_FIELD, level.getName());
    }
}