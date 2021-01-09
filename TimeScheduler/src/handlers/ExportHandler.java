/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import classes.Event;
import classes.User;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Benny
 */
public class ExportHandler {

    public void writeWeeklySchedule(java.util.ArrayList<classes.Event> eList, java.time.LocalDate start, java.time.LocalDate end, String path) {

        if (!this.checkIfPathIsValid(path)) {
            System.out.println(path + " is not valid!");
        }

        path += "\\Weekly Schedule.txt";

        this.createTxtFile(path);

        try {
            FileWriter myWriter = new FileWriter(path);

            myWriter.write("Weekly Schedule from " + start.getDayOfMonth() + "." + start.getMonth() + "." + start.getYear() + " - "
                    + end.getDayOfMonth() + "." + end.getMonth() + "." + end.getYear());
            myWriter.write("\n\n");

            for (classes.Event e : eList) {

                myWriter.write("Event name:\t\t" + e.getName());
                myWriter.write("\n");
                myWriter.write("Date:\t\t\t" + e.getDate().getDayOfMonth() + "." + e.getDate().getMonth() + "." + e.getDate().getYear() + " " + e.getDate().getHour() + ":" + e.getDate().getMinute());
                myWriter.write("\n");
                myWriter.write("Location:\t\t" + e.getLocation());
                myWriter.write("\n");
                myWriter.write("Duration:\t\t" + e.getDuration() + " Minutes");
                myWriter.write("\n");
                myWriter.write("Organisator:\t\t" + e.getOrganisator().getFirstName() + " " + e.getOrganisator().getLastName());
                myWriter.write("\n");
                myWriter.write("Organisator email:\t" + e.getOrganisator().getEmail());
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

    private boolean checkIfPathIsValid(String path) {
        File theDir = new File(path);
        if (theDir.exists()) {
            return true;
        } else {
            return false;
        }
    }

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
