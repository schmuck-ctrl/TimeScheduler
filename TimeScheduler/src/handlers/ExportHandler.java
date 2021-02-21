/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 * Handles the weekly schedule.
 * 
 * @author Benny
 */
public class ExportHandler {

    /**
     * @author Nils Schmuck
     * 
     * Creates a filesave dialog, to choose a directory to save the weekly schedule.
     * 
     * @return The directory path.
     */
    public String openSaveDialog() {
        JFileChooser saveDialog = new JFileChooser();
        String directory = null;
        
        saveDialog.setDialogType(JFileChooser.SAVE_DIALOG);
        saveDialog.setDialogTitle("Save weekly schedule");
        saveDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        
        int state = saveDialog.showSaveDialog(forms.FrmMain.getInstance());
        
        if(state == JFileChooser.APPROVE_OPTION){
            directory = saveDialog.getSelectedFile().toString();
        }
        
        return directory;
    }
    
    /**
     * 
     * This function is writing the weekly schedule into an .txt to the desired save path on the system.
     * 
     * @param eList List of events to be written in schedule txt
     * @param start Startdate of the week
     * @param end Endtime of the week
     * @param path Desired save path
     */
    public void writeWeeklySchedule(java.util.ArrayList<classes.Event> eList, java.time.LocalDate start, java.time.LocalDate end, String path) {

        //check if the path is valid
        if (!this.checkIfPathIsValid(path)) {
            System.out.println(path + " is not valid!");
        }

        //Add file name to the path
        path += "\\Weekly Schedule.txt";

        //Creates the file in the file path
        this.createTxtFile(path);

        try {
            FileWriter myWriter = new FileWriter(path);

            //Write Headline
            myWriter.write("Weekly Schedule from " + start.getDayOfMonth() + "." + start.getMonth() + "." + start.getYear() + " - "
                    + end.getDayOfMonth() + "." + end.getMonth() + "." + end.getYear());
            myWriter.write("\n\n");

            //insert all necessary informations into the schedule for all events in eList
            for (classes.Event e : eList) {

                myWriter.write("Event name:\t\t" + e.getName());
                myWriter.write("\n");
                myWriter.write("Date:\t\t\t" + e.getDate().getDayOfMonth() + "." + e.getDate().getMonth() + "." + e.getDate().getYear() + " " + e.getDate().getHour() + ":" + e.getDate().getMinute());
                myWriter.write("\n");
                myWriter.write("Location:\t\t" + e.getLocation());
                myWriter.write("\n");
                myWriter.write("Duration:\t\t" + e.getDuration() + " Minutes");
                myWriter.write("\n");
                myWriter.write("Organisator:\t\t" + e.getHost().getFirstName() + " " + e.getHost().getLastName());
                myWriter.write("\n");
                myWriter.write("Organisator email:\t" + e.getHost().getEmail());
                myWriter.write("\n");
                myWriter.write("Participants:\t\t" + e.getParticipants().size());
                myWriter.write("\n");
                myWriter.write("Priority:\t\t" + e.getPriority());
                myWriter.write("\n");
                myWriter.write("----------------------------------------");
                myWriter.write("\n");

            }

            System.out.println("Successfully write to Weekly Schedule file");

            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * Checks if the given file path is valid
     * 
     * @param path System path
     * @return Returns true or false
     */
    private boolean checkIfPathIsValid(String path) {
        File theDir = new File(path);
        if (theDir.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * Creates the .txt file in the path
     * 
     * @param path System path
     */
    private void createTxtFile(String path) {
        try {
            File report = new File(path);
            if (report.createNewFile()) {
                System.out.println("Weekly report created: " + report.getName());
            } else {
                System.out.println("Weekly Report already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ExportHandler() {

    }
}
