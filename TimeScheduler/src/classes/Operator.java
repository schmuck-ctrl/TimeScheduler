/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 * Represent an Operator.
 * 
 * @author Vadym
 */
public abstract class Operator {
/**
 * The diffrent roles that an operator can assume.
 */
    public enum Role {
        /**
         * User role
         */
        USER,
        /**
         * Admin role
         */
        ADMIN
    }
    /**
     * The id of the Operator.
     */
    private int id = -1;
    /**
     * The email of the Operator.
     */
    private String email = null;
    /**
     * The first name of the Operator.
     */
    private String firstName = null;
    /**
     * The last name of the Operator.
     */
    private String lastName = null;
    /**
     * Gets the {@link Operator#email} of this Operator.
     * 
     * @return the email of Operator.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the value to this {@link Operator#email}.
     * 
     * @param newEmail The newEmail of this Operator.
     */
    public void setEmail(String newEmail) {
        
        if (newEmail != null && !newEmail.trim().isEmpty()) {
            this.email = newEmail;
            
        } else {
            System.out.println("The user input is not correct1");
        }
    }
    /**
     * Gets the {@link Operator#firstName} of this Operator.
     * @return The first name of the Operator.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Sets the value of {@link Operator#firstName} if the value is not empty or null.
     * 
     * @param newFirstName The newFirstName of the Operator.
     */
    public void setFirstName(String newFirstName) {
        if (newFirstName != null && !newFirstName.trim().isEmpty()) {
            this.firstName = newFirstName;
        } else {

            System.out.println("The user input is not correct2");
            
        }
    }
    /**
     * Gets the {@link Operator#lastName} of this Operator.
     * @return The lastName of the Operator.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Sets the value of {@link Operator#lastName} if the value is not empty or null
     * 
     * @param newLastName The new last name of the Operator.
     */
    public void setLastName(String newLastName) {
        if (newLastName != null && !newLastName.trim().isEmpty()) {
            this.lastName = newLastName;
        } else {

            System.out.println("The user input is not correct3");
        }
    }
    /**
     * Sets the Value of this {@link Operator#id} if the id is not smaller than zero.
     * @param id The new id of the Operator.
     */
    protected void setUserId(int id) {
        if (id >= 0) {
            this.id = id;
        }
    }
    /**
     * Gets the {@link Operator#id} of this Operator.
     * @return The id of the Operator.
     */
    public int getUserId() {
        return this.id;
    }
    /**
     * BENNY ?
     * 
     */
    public abstract Role getRole();
    
    @Override
    public String toString() {
        return email;
    }
    
    
    @Override
    public boolean equals(Object anObject) {
        if (!(anObject instanceof Operator)) {
            return false;
        }
        Operator otherMember = (Operator)anObject;        
        return otherMember.getUserId() == (getUserId());
    }
    
}
