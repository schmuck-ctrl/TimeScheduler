/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.util.regex.Pattern;
import java.security.NoSuchAlgorithmException;
import classes.User;
import handlers.UserHandler;
import handlers.EncryptionHandler;
import java.util.regex.Matcher;
import javax.swing.JOptionPane;

/**
 * Manages the Registration logic.
 *
 * @author Vadym Kuzmenko.
 */
public class RegistrationHandler {

    /**
     * Object for the Encryption access.
     */
    EncryptionHandler encrypHandler = new EncryptionHandler();
    
    DatabaseHandler dbHandler = new DatabaseHandler();
    

    /**
     * Checks if the user first and last name input is allowed.
     *
     * @param name The name of the user when he tries to create an account.
     * @return A boolean if user name is valid.
     */
    public static boolean checkInputUserName(String name) {
        //checks if first/lastname input from user is longer than 2 and has no numbers and is not null. []+ one or more
        if (name.matches("^[a-zA-Z]{2,}$") && name != null && !name.isEmpty()) {

        } else {

            return false;
        }
        return true;
    }

    /**
     * Checks if the user email input is allowed.
     *
     * @param email The email of the user when he tries to create an account.
     * @return A boolean if the user email is allowed.
     */
    public static boolean checkInputUserEmail(String email) {
        //check what the regex expresion does:
        //String regexEmail = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        // ^ expression start
        // first [] can contain 0 to * caracters in this brackets.
        // second [] should contain at least one of this caracters.
        // third [] can contain 0 - * caracters in this brackets.
        // (\\.)must be followed by an dot.
        // if a dot was used next [] should contain at least one of those caracters.
        // next an @ should come
        // after the @ comes at least one of the caracters in the [].
        // (\\.) must be followed by a dot.
        // the last [] should contain at least 2 {2,} of those characters. 
        String regexEmail = "^[@#$%^&+=^!§/()?_A-Za-z0-9-]*[_A-Za-z0-9-]+[@#$%^&+=^!§/()?_A-Za-z0-9-]*(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]{2,})$";
        Pattern emailPattern = Pattern.compile(regexEmail);
        if (email == null || emailPattern.matcher(email).matches() == false ) {
            return false;

        }
        
        return true;
    }

    //Checks if users password follows all rules like:
    /**
     *Checks if user password input matches all requirements.
     * 
     * @param inpassword The password that the user writes into the registration window.
     * @return A boolean if the user password is allowed or not.
     */
    public static boolean checkInputUserPassword(char[] inpassword) {
        String password = String.valueOf(inpassword);
        inpassword = null;
        // .* means any character any amount is allowed except line breaks.
        // ?=.*[pattern] means that we are looking for a patten that follows after any amount of characters {0,*} but not an line break.
        //a digit must occur at least once (?=.*[0-9])
        //a lower case letter must occur at least once (?=.*[a-z])
        //an upper case letter must occur at least once (?=.*[A-Z])
        //a special character must occur at least once (?=.*[@#$%^&+=^!§/()?])
        //no whitespace allowed in the entire string (?=\\S+$)
        //at least 8 character {8,}
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=^!§/()?])(?=\\S+$).{8,}";
        if (!password.matches(pattern)) {
            return false;
        }
        return true;
    }

    /**
     * Check if the repeated password matches the first password.
     * @param inputPassword First password that the user writes into the registration window.
     * @param repeatPassword Second password that the user writes into the registration window.
     * @return A boolewn if the two password are addentical or not.
     */
    public static boolean checkIfPasswordTheSame(char[] inputPassword, char[] repeatPassword) {
        String password = String.valueOf(inputPassword);
        String repassword = String.valueOf(repeatPassword);

        if (String.valueOf(password).equals(String.valueOf(repassword)) && String.valueOf(password) != null) {

            return true;
        }
        return false;
    }
    /**
     * Checks if the user email already exists in the database.
     * 
     * @param email The email of the user when he tries to create an account.
     * @return A boolean if the email already exists or not.
     */
    public static boolean checkIfNewUserExist(String email) {
        //check if email exists.

        DatabaseHandler dbHandler;
        dbHandler = new DatabaseHandler();
        if (dbHandler.checkIfUserExists(email) == true) {
            LoggerHandler.logger.info("User does not exist.");
            return false;
        }
        return true;
    }

    /**
     * Creates a new user object and stores him in the Database
     * Password gets encrypted and written into a string.
     * 
     * @param newUser The new user object. 
     * @param password The char array password.
     */
    public static void createNewUser(User newUser, char[] password) {
        UserHandler uaHandler = new UserHandler();
//        try {
            //Stores the encrypted char array password into a String.
//            String passwordEncrypted = EncryptionHandler.toHexString(EncryptionHandler.getShaEncrypt(password));
            String passwordEncrypted = EncryptionHandler.simpleEncryption(password);
            uaHandler.addUser(newUser, passwordEncrypted);
            
//        } catch (NoSuchAlgorithmException e) {
//            System.out.println("Exception thrown");
//        }

    }
    
    /**
     * Gets a random number from the Encryption handler.
     * 
     * @return A random integer
     */
    public int getRandomEmailVerificationNumber() {
        int ran = encrypHandler.randNumberGen();
        return ran;
    }
    /**
     * Sends an email with a random to the user who tries to register 
     * 
     * @param email The email of the user when he tries to create an account.
     * @param rand The generated random number 
     */
    public void sendEmailVerificationCode(String email, int rand) {
        EmailHandler eHandler = new EmailHandler(email, rand);
        new Thread(eHandler).start();
    }
    /**
     * Checks if the random number which the user writes is correct.
     * 
     * @param rand The generated random number.
     * @param userInput The user input.
     * @return A boolean if the input of the user is correct or not.
     */
    public boolean verifyEmailSendedCode(int rand, String userInput) {
        if (rand == Integer.valueOf(userInput)) {
            return true;
        }
        return false;
    }
    /**
     * Shows the user input windows, messages and checks if the verification code is correct.
     * 
     * @param email The email that the user writes into the registration window.
     * @return A boolean if user verification code was correct or not.
     */
    public boolean checkVerificationCode(String email) {
        int rand = getRandomEmailVerificationNumber();
        sendEmailVerificationCode(email, rand);
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
                    JOptionPane.showMessageDialog(null, "Last attempt ");
                } else {
                    JOptionPane.showMessageDialog(null, "Your second attempt was incorrect please check your if your email is correct.", "Login error", JOptionPane.INFORMATION_MESSAGE);

                }
            } else {
                if (Integer.valueOf(userInputRand) == rand) {
                    return true;
                }
            }
        }
        return false;

    }

}
