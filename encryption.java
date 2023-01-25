import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class AESRSAEncryption {
    public static void main(String[] args) throws Exception {
        // Generate RSA key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // File to encrypt
        File inputFile = new File("example.txt");
        byte[] inputBytes = new byte[(int) inputFile.length()];

        // Encrypt the file
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        fileInputStream.read(inputBytes);
        fileInputStream.close();

        // Generate AES key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();

        // Encrypt the file using AES
        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText = aesCipher.doFinal(inputBytes);

        // Encrypt AES key using RSA
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedAESKey = rsaCipher.doFinal(secretKey.getEncoded());

        // Save the encrypted file and encrypted AES key
        FileOutputStream fileOutputStream = new FileOutputStream("encrypted.bin");
        fileOutputStream.write(cipherText);
        fileOutputStream.close();

        FileOutputStream keyOutputStream = new FileOutputStream("encrypted_key.bin");
        keyOutputStream.write(encryptedAESKey);
        keyOutputStream.close();


        // Decrypt the file
        File encryptedFile = new File("encrypted.bin");
        byte[] encryptedBytes = new byte[(int) encryptedFile.length()];

        FileInputStream encryptedFileInputStream = new FileInputStream(encryptedFile);
        encryptedFileInputStream.read(encryptedBytes);
        encryptedFileInputStream.close();

        // Decrypt the AES key using RSA
        File encryptedAESKeyFile = new File("encrypted_key.bin");
        byte[] encryptedAESKeyBytes = new byte[(int) encryptedAESKeyFile.length()];

        FileInputStream encryptedAESKeyFileInputStream = new FileInputStream(encryptedAESKeyFile);
        encryptedAESKeyFileInputStream.read(encryptedAESKeyBytes);
        encryptedAESKeyFileInputStream.close();

        Cipher rsaDecryptCipher = Cipher.getInstance("RSA");
        rsaDecryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedAESKeyBytes = rsaDecryptCipher.doFinal(encryptedAESKeyBytes);
        SecretKey decryptedAESKey = new SecretKeySpec(decryptedAESKeyBytes, "AES");
// Decrypt the file using AES
        Cipher aesDecryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aesDecryptCipher.init(Cipher.DECRYPT_MODE, decryptedAESKey);
        byte[] decryptedBytes = aesDecryptCipher.doFinal(encryptedBytes);

        // Write the decrypted file
        FileOutputStream decryptedFileOutputStream = new FileOutputStream("decrypted.txt");
        decryptedFileOutputStream.write(decryptedBytes);
        decryptedFileOutputStream.close();

        System.out.println("File has been successfully encrypted and decrypted.");
    }
}
