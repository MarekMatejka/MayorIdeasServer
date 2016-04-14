package mm.mayorideas.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AESEncryptorTest {

    private static final String DECRYPTED = "marek";
    private static final String ENCRYPTED = "7MjMZdGhB/O7D1P7hb9zag==";

    @Test
    public void testEncryption() {
        AESEncryptor aes = new AESEncryptor();
        assertEquals(ENCRYPTED, aes.encrypt(DECRYPTED));
    }

    @Test
    public void testDecryption() {
        AESEncryptor aes = new AESEncryptor();
        assertEquals(DECRYPTED, aes.decrypt(ENCRYPTED));
    }
}