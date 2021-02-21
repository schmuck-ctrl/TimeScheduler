/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Hashes the entered user password and generates a random number.
 *
 * @author Vadym
 */
public class EncryptionHandler {
//     public static byte[] getShaEncrypt(char[] newPassword) throws NoSuchAlgorithmException {
//        //SHA-256 Encryption
//        String password = String.valueOf(newPassword);
//        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//
//        return messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
//
//        //FUNCTION BODY HAS TO BE IMPLEMENTED
//    }
//
//    public static String toHexString(byte[] hash) {
//        // Convert byte array into signum representation  
//        BigInteger number = new BigInteger(1, hash);
//
//        // Convert message digest into hex value  
//        StringBuilder hexString = new StringBuilder(number.toString(16));
//
//        // Pad with leading zeros 
//        while (hexString.length() < 32) {
//            hexString.insert(0, '0');
//        }
//
//        return hexString.toString();
//    }

    /**
     *
     * @param newPassword The password that the user entered.
     * @return A hashed password.
     */
    public static String simpleEncryption(char[] newPassword) {
        String password = String.valueOf(newPassword);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            messageDigest.update(password.getBytes());

            byte[] resultByteArray = messageDigest.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            LoggerHandler.logger.severe("Encryption failed.");
        }
        return "0";
    }

    /**
     * Generating a radom number with six digits.
     *
     * @return An intiger with six digits.
     */
    public static int randNumberGen() {
        Random randomNumber = new Random();
        int checkRandomeNumber = randomNumber.nextInt(899999) + 100000;

        return checkRandomeNumber;
    }
}
