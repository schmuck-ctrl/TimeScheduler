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
 * Provides functions to log bugs or certain other important information
 * @author Benny / Vadym
 */
public class LoggerHandler {

    //Static variable of the logger class.
    public static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    //Formatter to formatting the file handler 
    private static Formatter logFormatter = null;
    //FileHandler 
    private static FileHandler fileHandler = null;

    /**
     * Setups logger allocates the static variables.
     *
     */
    public static void setupLogger() {
        //sets the log level of the logger into (info, severe and warning) 
        logger.setLevel(Level.INFO);
        try {
            //Fileshandler to create the logfile.
            fileHandler = new FileHandler("Logfile.txt", true);
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
