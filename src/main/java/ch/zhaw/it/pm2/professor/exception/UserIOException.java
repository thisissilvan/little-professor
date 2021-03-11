package ch.zhaw.it.pm2.professor.exception;

/**
 * Something with the user-file is wrong when this exception gets thrown.
 */
public class UserIOException extends Exception{
    public UserIOException(Throwable wrappedException) {
        super("Something with the user-file is wrong.", wrappedException);
    }
}
