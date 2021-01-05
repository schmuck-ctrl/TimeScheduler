/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Vadym
 */
public class EncryptionHandler {
     public static byte[] getShaEncrypt(char[] newPassword) throws NoSuchAlgorithmException {
        //SHA-256 Encryption
        String password = String.valueOf(newPassword);
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(password.getBytes(StandardCharsets.UTF_8));

        //FUNCTION BODY HAS TO BE IMPLEMENTED
    }

    public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros 
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}
