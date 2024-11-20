package com.nichga.proj97.Services;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for password-related operations such as generating salts,
 * hashing passwords, and verifying hashed passwords.
 */
public class PasswordUtil {
    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 64;
    private static final int ITERATIONS = 10000;

    /**
     * Generates a random salt for password hashing.
     *
     * @return A base64-encoded random salt.
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hashes a password using PBKDF2 with the given salt.
     *
     * @param password The plain-text password to hash.
     * @param salt     The base64-encoded salt to use for hashing.
     * @return A base64-encoded hashed password.
     */
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

    /**
     * Verifies if the entered password matches the stored hashed password using the stored salt.
     *
     * @param enteredPassword The plain-text password entered by the user.
     * @param storedHash      The base64-encoded hashed password stored in the database.
     * @param storedSalt      The base64-encoded salt stored in the database.
     * @return {@code true} if the entered password matches the stored hash, {@code false} otherwise.
     */
    public static boolean verifyPassword(String enteredPassword, String storedHash, String storedSalt) {
        String hashOfEnteredPassword = hashPassword(enteredPassword, storedSalt);
        return hashOfEnteredPassword.equals(storedHash);
    }
}