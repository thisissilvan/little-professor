package ch.zhaw.it.pm2.professor.exception;

/**
 * If Something with the house-file is wrong, the Exception informs this.
 */
public class HouseIOException extends Exception{
    public HouseIOException(Throwable wrappedException) {
        super("Something with the house-file is wrong.", wrappedException);
    }
}
