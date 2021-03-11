package ch.zhaw.it.pm2.professor.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * EncryptionTest.
 * Test-methods are described here.
 */
public class EncryptionTest {

    private final static String[] TEST_STRINGS = {
            "Gqh7f4ZSjM4IqTTJpma5",
            "CAe9emGmqnptetXt",
            "Qq7xXZKJs1LT",
            "VnN3ioDs",
            "7yvA",
            "XBf3LhSIM5SiZxAlhd",
            "Vc686Zrd5SYL1Z86",
            "PTnl9icYiX",
            "0jkC5R",
            "9L"
    };

    private final EncryptionHandler encryptionHandler;

    public EncryptionTest() {
        this.encryptionHandler = EncryptionHandler.getInstance();
    }

    /**
     * Chains encryption and decryption and checks if input = output.
     */
    @Test
    public void decryptionTest() {
        for (String testString : TEST_STRINGS) {
            String encryptedString = this.encryptionHandler.encryptString(testString);
            String decryptedString = this.encryptionHandler.decryptString(encryptedString);
            assertEquals(testString, decryptedString);
        }
    }

    /**
     * Tests if the strings are encrypted.
     */
    @Test
    public void minimalSecurityTest() {
        for (String testString : TEST_STRINGS) {
            String encryptedString = this.encryptionHandler.encryptString(testString);
            assertNotEquals(testString, encryptedString);
        }
    }
}
