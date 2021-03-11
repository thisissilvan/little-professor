package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.Config;
import ch.zhaw.it.pm2.professor.controller.LevelFactory;
import ch.zhaw.it.pm2.professor.controller.QuestionGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * The level class implements the game's level. It consists of an ID which is its name here, a difficulty degree that is the domain for the question sets.
 * Rooms, as every level contains other kind and amount of rooms.
 */
public class Level {

    private String name;
    private LevelFactory.Difficulty difficulty;
    private Room[] rooms;
    private QuestionGenerator generator;

    /**
     * Constructor of class Level.
     * @param name  the name of the level
     * @param difficulty    the difficulty of the level
     * @param rooms the rooms as Room[] which are inside this level
     */
    public Level(String name, LevelFactory.Difficulty difficulty, Room[] rooms) {
        this.name = name;
        this.difficulty = difficulty;
        this.rooms = rooms;
        this.generator = new QuestionGenerator(difficulty.hasDoubleNumbers(), difficulty);
    }

    public String getName() {
        return name;
    }

    public Room[] getRooms() {
        return rooms;
    }

    /**
     * This list contains all valid commands to enter the existing rooms.
     * @return a list of valid commands.
     */
    public List<Config.Command> getValidCommandsList() {
        List<Config.Command> validCommandsList = new ArrayList<>();
        for (int i = 1; i < rooms.length; i++) {
            validCommandsList.add(rooms[i].getCommand());
        }
        validCommandsList.add(Config.Command.HELP);
        validCommandsList.add(Config.Command.QUIT);
        return validCommandsList;
    }

    /**
     * Gets the Question.
     * @param room  the room in which the user is
     * @return  the generated question from the generator
     */
    public String getQuestion(Room room) {
        return generator.getQuestion(room.getOperation().toString(), difficulty.getLowerbound(), difficulty.getUpperbound());
    }

    /**
     * Gets the answer to a question.
     * @return  the answer to a question
     */
    public String getAnswer() {
        return generator.getAnswer();
    }

    /**
     * Minimum amount of points that have to be collected to finish the current level successfully.
     * Its calculated by all rooms without Hallway times 9, as maximal to achieved points are 5 per room.
     * @return int minimum point to be reached
     */
    public int getMinPoints(){
        return (rooms.length - 1) * 4;
    }

}