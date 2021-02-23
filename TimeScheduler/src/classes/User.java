/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 * The class of a user without any priveleges
 * @author Vadym
 */
public class User extends Operator {
    /**
     * Overides the Operator Role to an User
     * @return the role User
     */
    @Override
    public Role getRole() {
        return Role.USER;
    }

    public User() {

    }
    /**
     * Used if a new user tries create a new account because the user does not have at this moment an id.
     * Sets the User values like first name , last name and user email
     * @param firstName The first name of this User.
     * @param lastName The last name of this User.
     * @param userEmail The email of this User.
     */
    public User(String firstName, String lastName, String userEmail) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(userEmail);
    }
    /**
     * Sets the User values like first name , last name, user email and id.
     * @param firstName The first name of this User.
     * @param lastName The last name of this User.
     * @param userEmail The email of this User.
     * @param id The id of this User.
     */
    public User(int id, String firstName, String lastName, String userEmail) {
        setUserId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(userEmail);
    }

}
