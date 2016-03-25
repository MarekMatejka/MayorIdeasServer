package mm.mayorideas.security;

import org.junit.Test;

public class AESEncryptorTest {

    @Test
    public void testEncryption() {
        String username = "marek@web.gov.uk";
        String name = "Marek Web Gov";
        String password = "test";

        AESEncryptor aes = new AESEncryptor();
        SHA256Encryptor sha = new SHA256Encryptor();

        System.out.println(aes.encrypt(username));
        System.out.println(aes.encrypt(name));
        System.out.println(sha.encrypt(password));
    }

    @Test
    public void testDecryption() {
        String text = "VsI+GaWUEaIQ0tpY3mcSBxj4mVJ2GiFLWLN2YcreJyM=";

        AESEncryptor aes = new AESEncryptor();
        System.out.println(aes.decrypt(text));
    }
}