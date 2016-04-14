package mm.mayorideas.security;

import mm.mayorideas.security.exception.EncryptionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SHA256EncryptorTest {

    private static final String DECRYPTED = "marek";
    private static final String ENCRYPTED = "e60727961ca945cf89ea577b9dc0cb5fad60651ad7e05f4973fa014b46dfb34c";

    @Test
    public void testEncryption() {
        SHA256Encryptor sha = new SHA256Encryptor();
        assertEquals(ENCRYPTED, sha.encrypt(DECRYPTED));
    }

    @Test(expected = EncryptionException.class)
    public void testDecryption() {
        SHA256Encryptor sha = new SHA256Encryptor();
        sha.decrypt(ENCRYPTED);
    }
}
