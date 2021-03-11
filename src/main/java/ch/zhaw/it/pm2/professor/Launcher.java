package ch.zhaw.it.pm2.professor;

import ch.zhaw.it.pm2.professor.exception.HouseIOException;
import ch.zhaw.it.pm2.professor.exception.UserConversionException;
import ch.zhaw.it.pm2.professor.exception.UserIOException;
import ch.zhaw.it.pm2.professor.model.Game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Starts the application and contains the main-method.
 */
public class Launcher {

    private static final Logger LOGGER = Logger.getLogger(Launcher.class.getCanonicalName());

    /**
     * Starts the professor, initializes the logger and starts a java-timer,
     * which triggers the time-updates in the Game class.
     *
     * @param args no parameters are used in this program
     * @throws UserIOException if something goes wrong with the user-file
     * @throws UserConversionException if something goes wrong with the user-name input
     * @throws HouseIOException if something goes wrong with the house or entrance-file
     * @throws FileNotFoundException if the house or entrance-file can't be found
     */
    public static void main(String[] args) throws UserIOException, UserConversionException, HouseIOException, FileNotFoundException {
        initLogger();
        Timer timer = new Timer();
        Game game = new Game();
        timer.scheduleAtFixedRate(game, 0, Config.TIMER_INTERVAL_MILLIS);
        game.start();
    }

    private static void initLogger() {
        try {
            InputStream config = Launcher.class.getClassLoader().getResourceAsStream("./log.properties");
            LogManager.getLogManager().readConfiguration(config);
        } catch (IOException e) {
            LOGGER.log(Level.CONFIG,"No log.properties", e);
        }
    }
}