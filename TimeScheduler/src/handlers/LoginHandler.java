/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author Vadym
 */
public class LoginHandler {
        // Checks if the user is in the database by the users email and the Encrypted password.
    DatabaseHandler dbHandler = new DatabaseHandler();
    
    public boolean checkUserInput(String userEmail, char[] password) {
        try {
            String passwordEncrypted = EncryptionHandler.toHexString(EncryptionHandler.getShaEncrypt(password));
            if (dbHandler.checkIfUserExists(userEmail, passwordEncrypted) == true) {
                if(dbHandler.checkIfUserExists(userEmail) == true)
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown");
        }
     
        return false;
    }
    public boolean checkIfUserInputExist(String userEmail){
        if(dbHandler.checkIfUserExists(userEmail) == true){
            return true;
        }
        else {
            return false;
        }
    }
}
