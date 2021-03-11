package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.model.Level;
import java.util.List;

/**
 * LevelSource is the game's source for levels, where it can get all existing levels.
 */
public interface LevelSource {

    /**
     * This method gets all existing levels as a list.
     * @return a list containing all levels
     */
    List<Level> getLevels();

}
