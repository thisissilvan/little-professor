package ch.zhaw.it.pm2.professor.exception;

/**
 * If something with the user conversion goes wrong, this exception will get thrown.
 */
public class UserConversionException extends Exception {
    public UserConversionException(String message) {
        super(message);
    }
}