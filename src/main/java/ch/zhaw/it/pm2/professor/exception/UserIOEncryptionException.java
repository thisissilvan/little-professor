package ch.zhaw.it.pm2.professor.exception;

/**
 * Something went wrong with the user-encryption or decryption when this Exception gets thrown.
 */
public class UserIOEncryptionException extends RuntimeException {
    public UserIOEncryptionException(Throwable e) {
        super("Something went wrong with the user-encryption or decryption.", e);
    }
}
