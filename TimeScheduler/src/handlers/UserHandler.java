/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import classes.Operator;
import classes.User;
import java.util.ArrayList;

/**
 * Manages the database access especially for the operators.
 *
 * @author Nils Schmuck
 */
public class UserHandler {

    // <editor-fold defaultstate="collapsed" desc="Global Variables">
    /**
     * The object for the database access
     */
    DatabaseHandler dbHandler = null;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructs a new event handler and initialized the object for the
     * database access.
     */
    public UserHandler() {
        dbHandler = new DatabaseHandler();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Gets all operators from the database.
     *
     * @return A collection with all operators.
     */
    public ArrayList<Operator> getAllUser() {
        ArrayList<Operator> listOfUser = dbHandler.getAllUser();
        return listOfUser;
    }

    /**
     * Gets a specified operator by the email.
     *
     * @param email The email of the operator.
     * @return The operator with this email.
     */
    public Operator getUser(String email) {
        Operator user = dbHandler.getUserByUsername(email);

        return user;
    }

    /**
     * Gets a specified operator by the id.
     *
     * @param userId The id of the operator.
     * @return The operator with this id.
     */
    public Operator getUser(int userId) {
        Operator user = dbHandler.getUserByID(userId);

        return user;
    }

    /**
     * Creates a new operator in the database.
     *
     * @param newUser The user that is created
     * @param password The hashed password of this operator.
     */
    public void addUser(User newUser, String password) {
        dbHandler.insertNewUser(newUser, password);
    }

    /**
     * Changes the configuration of a oparator.
     * @param newUser The operator that is changed
     */
    public void editUser(Operator newUser) {
        dbHandler.editUser(newUser);
    }

    /**
     * Delete the specified operator.
     * @param user The operator that is deleted.
     */
    public void deleteUser(Operator user) {
        dbHandler.deleteUser(user);
    }

    /* Wird vermutlich nicht verwendet.
    public ArrayList<Operator> searchUser(String searchTerm) {
        ArrayList<Operator> searchResult = null;

        if (!searchTerm.isBlank()) {
            searchResult = dbHandler.getAllUsersLike(searchTerm);
        }

        return searchResult;
    }*/
    // </editor-fold>
}
