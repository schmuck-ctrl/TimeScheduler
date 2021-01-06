/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.util.regex.Pattern;
import handlers.EncryptionHandler;
import java.security.NoSuchAlgorithmException;
import classes.User;
import handlers.UserHandler;

/**
 *
 * @author Vadym
 */
public class RegistrationHandler {

    //checks if first/lastname input from user is longer than 2 and has no nmbers and is not null.
    public static boolean checkInputUserName(String name) {
        if (name.matches("[a-zA-Z]+") && name != null && !name.isEmpty() && name.length() > 1f) {

        } else {

            return false;
        }
        return true;
    }

    //Checks if the users email is allowed
    public static boolean checkInputUserEmail(String email) {
        //check what the regex expresion does:
        String regexEmail = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(regexEmail);
        if (email == null) {
            return false;

        }
        return emailPattern.matcher(email).matches();
    }

    //Checks if users password follows all rules like:
    //a digit must occur at least once (?=.*[0-9])
    //a lower case letter must occur at least once (?=.*[a-z])
    //an upper case letter must occur at least once (?=.*[A-Z])
    //a special character must occur at least once (?=.*[@$%^&+=])
    //no whitespace allowed in the entire string (?=\\S+$)
    //at least 8 character {8,}
    public static boolean checkInputUserPassword(char[] inpassword) {
        String password = String.valueOf(inpassword);
        inpassword = null;

        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (!password.matches(pattern)) {
            return false;
        }
        return true;
    }

    //Checks if Passwords are the same.
    public static boolean checkIfPasswordTheSame(char[] inputPassword, char[] repeatPassword) {
        String password = String.valueOf(inputPassword);
        String repassword = String.valueOf(repeatPassword);

        if (String.valueOf(password).equals(String.valueOf(repassword)) && String.valueOf(password) != null) {

            return true;
        }
        return false;
    }

    public static boolean checkIfNewUserExist(String email) {
        //check if email exists.
        
        DatabaseHandler dbHandler;
        dbHandler = new DatabaseHandler();
        if (dbHandler.checkIfUserExists(email) == true) {
            return false;
        }
        return true;
    }

    //Creates new user if every input was correct
    public static void createNewUser(User newUser, char[] password) {
        UserHandler uaHandler = new UserHandler();
        try {
            String passwordEncrypted = EncryptionHandler.toHexString(EncryptionHandler.getShaEncrypt(password));
            uaHandler.addUser(newUser, passwordEncrypted);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown");
        }

    }
}
