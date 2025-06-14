
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class CryptoUtil {
     private static final String ALGORITHM = "AES";
    private static SecretKey secretKey; // Una clave para el cifrado
        public CryptoUtil() {
        try {
            // Genera una clave AES de 128 bits (o puedes cargar una persistente)
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(128); // 128, 192 o 256 bits
            secretKey = keyGen.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }
        public static void setSecretKey(String keyString) {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    // Puedes obtener la clave en formato String para persistirla
    public static String getSecretKeyString() {
        if (secretKey != null) {
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        }
        return null;
    }
    public static String encrypt(String data) throws Exception {
        if (secretKey == null) {
            throw new IllegalStateException("Secret key not initialized. Call CryptoUtil constructor or setSecretKey first.");
        }
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData) throws Exception {
        if (secretKey == null) {
            throw new IllegalStateException("Secret key not initialized. Call CryptoUtil constructor or setSecretKey first.");
        }
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
}
