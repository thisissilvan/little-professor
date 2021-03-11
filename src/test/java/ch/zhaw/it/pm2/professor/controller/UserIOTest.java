package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.exception.UserConversionException;
import ch.zhaw.it.pm2.professor.exception.UserIOException;
import ch.zhaw.it.pm2.professor.Config;
import static org.junit.jupiter.api.Assertions.*;

import ch.zhaw.it.pm2.professor.model.User;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * UserIOTest.
 * Test-methods are described here.
 */
public class UserIOTest {

    private final UserIO userIo;

    public UserIOTest() {
        this.userIo = new UserIO(Config.USER_TEST_FILE_PATH);
    }

    /**
     * Tests if the the user-file is created if it does not exist and User.load() is called.
     */
    @Test
    public void noFileStoreTest() throws UserConversionException, UserIOException {
        deleteUserFile();
        this.userIo.load("tester");
        assertTrue(getUserFile().exists());
    }

    /**
     * Test if a new user is created, when he isn't found.
     */
    @Test
    public void newUserTest() throws UserConversionException, UserIOException {
        User user = this.userIo.load("dekyi");
        assertNotNull(user);
    }

    /**
     * Tests if the the user-file is created if it does not exist and User.store() is called.
     */
    @Test
    public void noFileLoadTest() throws UserIOException {
        deleteUserFile();
        User user = new User("tester", 0, 1000);
        this.userIo.store(user);
        assertTrue(getUserFile().exists());
    }

    /**
     *  Creates two users stores them, then updates the second one.
     *  After this the stored users are loaded again. Name and highscore should now match with the original objects.
     */
    @Test
    public void storeAndLoadTest() throws UserIOException, UserConversionException {
        User testUser = new User("fratz", 20, 1500);
        User testUserTwo = new User("petee", 33, 1700);
        userIo.store(testUser);
        userIo.store(testUserTwo);
        testUserTwo.setHighscore(2000);
        userIo.store(testUserTwo);
        assertEquals(testUser, userIo.load(testUser.getName()));
        assertEquals(testUser.getHighscore(), userIo.load(testUser.getName()).getHighscore());
        assertEquals(testUserTwo, userIo.load(testUserTwo.getName()));
        assertEquals(testUserTwo.getHighscore(), userIo.load(testUserTwo.getName()).getHighscore());
    }

    private static void deleteUserFile() {
        File userFile = getUserFile();
        //noinspection ResultOfMethodCallIgnored
        userFile.delete();
    }

    private static File getUserFile() {
        return new File(Config.USER_TEST_FILE_PATH);
    }
}
