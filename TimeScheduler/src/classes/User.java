/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author Vadym
 */
public class User extends Operator {

    @Override
    public Role getRole() {
        return Role.USER;
    }

    public User() {

    }

    public User(String firstName, String lastName, String userEmail) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(userEmail);
    }

    public User(int id, String firstName, String lastName, String userEmail) {
        setUserId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(userEmail);
    }

}
