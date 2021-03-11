package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.Config;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Logger;

/**
 * This class uses a synchronous encryption-method to encrypt and decrypt between strings and encrypted base64.
 */
public class EncryptionHandler {

    private static final Logger LOGGER = Logger.getLogger(EncryptionHandler.class.getCanonicalName());

    private static EncryptionHandler instance;

    private final Cipher encryptionCipher;
    private final Cipher decryptionCipher;

    private  EncryptionHandler() {
        try {
            this.encryptionCipher = Cipher.getInstance(Config.ENCRYPTION_TYPE);
            this.decryptionCipher = Cipher.getInstance(Config.ENCRYPTION_TYPE);
            byte[] keyBytes = Base64.getDecoder().decode(Config.SECRET_KEY);
            SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, Config.ENCRYPTION_TYPE);
            this.encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
            this.decryptionCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException("Something with the cipher creation went wrong.", e);
        }
    }

    /**
     * Encrypts the given string with its utf-8 binary-representation.
     * @param string string to encrypt
     * @return encrypted bytes as base64
     */
    public String encryptString(String string) {
        LOGGER.info("String to encrypt: " + string);
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        try {
            byte[] encryptedBytes = this.encryptionCipher.doFinal(bytes);
            String encryptedBase64String = Base64.getEncoder().encodeToString(encryptedBytes);
            LOGGER.info("Encrypted string in base-64: " + encryptedBase64String);
            return encryptedBase64String;
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Something with the encryption went wrong.", e);
        }
    }

    /**
     * Decrypts the given base64 string after it is converted into binary.
     * @param string base64 representation of the data, which should be decrypted
     * @return decrypted string
     */
    public String decryptString(String string) {
        LOGGER.info("String to decrypt in base-64: " + string);
        byte[] bytes = Base64.getDecoder().decode(string);
        try {
            byte[] encryptedBytes = this.decryptionCipher.doFinal(bytes);
            String decryptedString = new String(encryptedBytes, StandardCharsets.UTF_8);
            LOGGER.info("Decrypted string: " + decryptedString);
            return decryptedString;
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Something with the decryption went wrong.", e);
        }
    }

    /**
     * @return The singleton instance of this class.
     */
    public static EncryptionHandler getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new EncryptionHandler();
        return instance;
    }
}
