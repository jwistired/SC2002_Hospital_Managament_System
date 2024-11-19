package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Utility class for password hashing and salt generation.
 * This class provides methods to generate a random salt, hash a password
 * using SHA-256 with the provided salt, and to convert between byte arrays
 * and hexadecimal strings for secure password storage.
 */
public class PasswordUtil {

    /**
     * Generates a random salt using SecureRandom.
     * The salt is 16 bytes long and returned as a hexadecimal string.
     *
     * @return A hexadecimal string representing the salt.
     */
    public static String getSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return bytesToHex(salt);
    }

    /**
     * Hashes a password using the SHA-256 algorithm with the provided salt.
     * The password is hashed by first updating the MessageDigest with the salt 
     * and then digesting the password.
     *
     * @param password The password to hash.
     * @param salt     The salt to use in the hashing process.
     * @return The hashed password as a hexadecimal string.
     *         Returns null if an exception occurs during hashing.
     */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(hexStringToByteArray(salt));
            byte[] hashedPassword = md.digest(password.getBytes());
            return bytesToHex(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes The byte array to convert.
     * @return A hexadecimal string representation of the byte array.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte temp : bytes) {
            result.append(String.format("%02x", temp));
        }
        return result.toString();
    }

    /**
     * Converts a hexadecimal string to a byte array.
     *
     * @param s The hexadecimal string.
     * @return The corresponding byte array.
     */
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] =
                    (byte) ((Character.digit(s.charAt(i), 16) << 4)
                            + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
