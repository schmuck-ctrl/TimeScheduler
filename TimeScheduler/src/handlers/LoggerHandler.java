/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.io.IOException;

import java.util.logging.SimpleFormatter;
import java.util.logging.Formatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Benny / Vadym
 */
public class LoggerHandler {

    public static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public Formatter logFormatter = null;
    public static FileHandler fileHandler = null;

    public static void setupLogger(){
        
        logger.setLevel(Level.INFO);
        try {
            fileHandler = new FileHandler("Logfile.txt",true);
        } catch (IOException ex) {
            Logger.getLogger(LoggerHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LoggerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        Formatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);

    }

}
