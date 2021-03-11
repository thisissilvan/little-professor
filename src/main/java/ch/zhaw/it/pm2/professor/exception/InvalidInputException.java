package ch.zhaw.it.pm2.professor.exception;

/**
 * An invalid Input from the user leads to this exception.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}