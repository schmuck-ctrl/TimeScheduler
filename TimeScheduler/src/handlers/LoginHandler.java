/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import classes.Operator;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * Manages the Frm login logic
 *
 * @author Vadym Kuzmenko
 */
public class LoginHandler {

    // Checks if the user is in the database by the users email and the Encrypted password.
    /**
     * Creates a new database handler object and initialized the object for the
     * database acces.
     */
    DatabaseHandler dbHandler = new DatabaseHandler();
    /**
     * Creates a new enctryption handler object and initialized the object.
     */
    EncryptionHandler encrypHandler = new EncryptionHandler();

    /**
     * Checks the user inut in the Login window.
     *
     * @param userEmail The email of the user which tries to login.
     * @param password The password of the user which tries to login.
     * @return A boolean if user input is correct or not.
     */
    public boolean checkUserInput(String userEmail, char[] password) {
//        try {
//            String passwordEncrypted = EncryptionHandler.toHexString(EncryptionHandler.getShaEncrypt(password));
            String passwordEncrypted = EncryptionHandler.simpleEncryption(password);
            if (dbHandler.checkIfUserExists(userEmail, passwordEncrypted) == true) {
                if (dbHandler.checkIfUserExists(userEmail) == true) {
                    return true;
                }
            }
//        } catch (NoSuchAlgorithmException e) {
//            System.out.println("Exception thrown");
//        }

        return false;
    }

    /**
     * Check if user exists.
     * @param userEmail The email of the user.
     * @return A boolean if user exists or not.
     */
    public boolean checkIfUserInputExist(String userEmail) {
        if (dbHandler.checkIfUserExists(userEmail) == true) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Sends a verification email to the user withe a random number.
     * @param user The user object.
     * @param rand The generated random number.
     */
    public void sendVerificationCode(Operator user, int rand) {
        EmailHandler eHandler = new EmailHandler(user, rand);
        new Thread(eHandler).start();
    }
    /**
     * Checks the verification code.
     * @param rand The random number that was generated.
     * @param userInput The random number that the user writes into the Login window.
     * @return true if both random number are correct and false if not.
     */
    public boolean verifySendedCode(int rand, String userInput) {
        if (rand == Integer.valueOf(userInput)) {
            return true;
        }
        return false;
    }
    /**
     * Generates a random number from the Encryption handler.
     * @return random number.
     */
    public int getRandomNumber() {
        int ran = encrypHandler.randNumberGen();
        return ran;
    }
    /** 
     * Check the verification code by giving the user to attempts.
     * @param email The email of the user.
     * @return If the verification code if correct and false if not.
     */
    public boolean checkVerificationCode(String email) {
        int rand = getRandomNumber();
        
        sendVerificationCode(dbHandler.getUserByUsername(email), rand);
        JOptionPane.showMessageDialog(null, "An verifiacation Email was send to you ");
        for (int i = 0; i < 2; i++) {

            String userInputRand = JOptionPane.showInputDialog("Enter the Verification number:");
            if (userInputRand == null) {
                return false;
            }
            Pattern userInputPattern = Pattern.compile("[0-9]");
            Matcher match = userInputPattern.matcher(userInputRand);
            if (!match.find()) {
                if (i == 0) {
                    JOptionPane.showMessageDialog(null, "last attempt ");
                } else {
                    JOptionPane.showMessageDialog(null, "Your second attempt was incorrect please check your if your email is correct.", "Login error", JOptionPane.INFORMATION_MESSAGE);

                }
            } else {
                if (Integer.valueOf(userInputRand) == rand) {
                    return true;
                }
            }
        }
        LoggerHandler.logger.info("Log in verification number input was incorrect.");
        return false;
    }

}
