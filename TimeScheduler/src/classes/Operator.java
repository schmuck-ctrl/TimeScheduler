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
public abstract class Operator {

    public enum Role {
        USER,
        ADMIN
    }

    private int id = -1;
    private String email = null;
    private String firstName = null;
    private String lastName = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        
        if (newEmail != null && !newEmail.trim().isEmpty()) {
            this.email = newEmail;
            
        } else {
            System.out.println("The user input is not correct1");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newFirstName) {
        if (newFirstName != null && !newFirstName.trim().isEmpty()) {
            this.firstName = newFirstName;
        } else {

            System.out.println("The user input is not correct2");
            
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newLastName) {
        if (newLastName != null && !newLastName.trim().isEmpty()) {
            this.lastName = newLastName;
        } else {

            System.out.println("The user input is not correct3");
        }
    }

    protected void setUserId(int id) {
        if (id >= 0) {
            this.id = id;
        }
    }

    public int getUserId() {
        return this.id;
    }

    public abstract Role getRole();
}
