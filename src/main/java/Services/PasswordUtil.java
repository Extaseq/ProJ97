package Services;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    private static final int SALT_LENGTH = 16;

    private static final int HASH_LENGTH = 64;

    private static final int ITERATIONS = 10000;

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = Base64.getDecoder().decode(salt);

        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, ITERATIONS, HASH_LENGTH);
        byte[] hash = null;
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            System.err.println("Error while hashing password: " + e.getMessage());
        }
        return Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verifyPassword(String enteredPassword, String storedHash, String storedSalt) {
        String hashOfEnteredPassword = hashPassword(enteredPassword, storedSalt);
        return hashOfEnteredPassword.equals(storedHash);
    }
}
