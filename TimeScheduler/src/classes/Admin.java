/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 * Overrides the Operator to an Admin
 * @author Vadym
 */
public class Admin extends Operator {
    /**
     * Overides the Operator Role to an Admin
     * @return the role admin
     */
    @Override
    public Role getRole() {
        return Role.ADMIN;
    }
//    /**
//     * Sets the Admin values like first name , last name and Admin email
//     * @param firstName The first name of this Admin.
//     * @param lastName The last name of this Admin.
//     * @param userEmail The email of this Admin.
//     */
//    public Admin(String firstName, String lastName, String userEmail) {
//        setFirstName(firstName);
//        setLastName(lastName);
//        setEmail(userEmail);
//    }
    /**
     * Sets the Admin values like first name , last name, Admin email and Id.
     * @param firstName The first name of this Admin.
     * @param lastName The last name of this Admin.
     * @param userEmail The email of this Admin.
     * @param id The id of this Admin.
     */
    public Admin(int id, String firstName, String lastName, String userEmail) {
        setUserId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(userEmail);
    }

}
