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
 *
 * @author nilss
 */
public class UserHandler {
    DatabaseHandler dbHandler = null;
    
    public UserHandler() {
        dbHandler = new DatabaseHandler();
    }
    
    public ArrayList<Operator> getAllUser(){
        ArrayList<Operator> listOfUser = dbHandler.getAllUser();
        return listOfUser;
    }
    
    public Operator getUser(String email){
        Operator user = dbHandler.getUserByUsername(email);
        
        return user;
    }
    
    public Operator getUser(int userId){
        Operator user = dbHandler.getUserByID(userId);
        
        return user;
    }
    
    public void addUser(User newUser, String password){
        //dbHandler.addEvent(0, Event);
    }
    
    public void editUser(Operator newUser){
        
    }
    
    public void deleteUser(Operator toBeDeleted){  

    }
    
    public ArrayList<Operator> searchUser(String searchTerm) {
        ArrayList<Operator> searchResult = null;
        
        if(!searchTerm.isBlank())
            searchResult = null;
        
        return searchResult;
    }
    
}
