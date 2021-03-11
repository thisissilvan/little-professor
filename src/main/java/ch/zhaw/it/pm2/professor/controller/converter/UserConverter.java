package ch.zhaw.it.pm2.professor.controller.converter;

import ch.zhaw.it.pm2.professor.exception.UserConversionException;
import ch.zhaw.it.pm2.professor.model.User;

/**
 * This class is used to convert user-objects to a string and strings to user-objects.
 * The usage of this class is to serialize user-objects. Persisting user-highscores can be achieved with this.
 */
public class UserConverter {

    /**
     * The method toString takes a User-Object as parameter and cenverts it to
     * a String with name and highscore.
     * @param user  a user object
     * @return      a String in the form "player+highscoreAsInt"
     * @throws UserConversionException if the user object is null, this Exception gets thrown
     */
    public static String toString(User user) throws UserConversionException {
        if (user == null) {
            throw new UserConversionException("User mustn't be null.");
        }
        return user.getName() + '+' + user.getHighscore();
    }

    /**
     * The method toObject takes a String-object representing a user with a highscore and
     * makes an User Object with the data into this String.
     * @param user  a String in the form "player+highscoreAsInt"
     * @return      a new User object
     * @throws UserConversionException  if the String is null, this Exception gets thrown
     */
    public static User toObject(String user) throws UserConversionException {
        if (user == null) {
            throw new UserConversionException("User mustn't be null.");
        }

        String[] userArray = user.split("\\+");
        if (userArray.length != 2) {
            throw new UserConversionException("To many user-attributes in user-string: " + user);
        }
        return new User(userArray[0], 0, Integer.parseInt(userArray[1]));
    }


}
