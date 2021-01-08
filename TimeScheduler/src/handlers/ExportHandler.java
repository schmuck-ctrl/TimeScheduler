/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Benny
 */
public class ExportHandler {

    public ExportHandler() {
        
        classes.Event evt = new classes.Event();
        
        
        try {
            File myObj = new File("C:\\Users\\Benny\\Desktop\\test.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("C:\\Users\\Benny\\Desktop\\test.txt");
            
            //Write
            
            //Write
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
