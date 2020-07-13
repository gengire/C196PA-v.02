package edu.wgu.grimes.c868pa.utilities;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashingUtil {

    private static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";


    public static String generateStrongPassword(String password) {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        try {
            byte[] salt = getSalt();
            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return iterations + ":" + toHex(salt) + ":" + toHex(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) {
        BigInteger bigInt = new BigInteger(1, array);
        String hex = bigInt.toString(16);
        int pLength = (array.length * 2) - hex.length();
        if (pLength > 0) {
            return String.format("%0" + pLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public static boolean validatePassword(String passwordToValidate, String actualPasswordHash) {
        String[] parts = actualPasswordHash.split(":");
        if (parts.length > 1) {
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt;
            try {
                salt = fromHex(parts[1]);
                byte[] hash = fromHex(parts[2]);
                PBEKeySpec spec = new PBEKeySpec(passwordToValidate.toCharArray(), salt, iterations, hash.length * 8);
                SecretKeyFactory skf;
                byte[] testHash;
                skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                testHash = skf.generateSecret(spec).getEncoded();
                int diff = hash.length ^ testHash.length;
                for(int i = 0; i < hash.length && i < testHash.length; i++)
                {
                    diff |= hash[i] ^ testHash[i];
                }
                return diff == 0;
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static byte[] fromHex(String hex)
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
