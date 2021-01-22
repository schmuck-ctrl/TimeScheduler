/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

/**
 * ReminderHandler inherits Thread
 * 
 * @author Benny
 */
public class ReminderHandler extends Thread {

    classes.Operator op;

    /**
     * Creates a new Instance of ReminderHandler
     * 
     * @param op Classes.Operator to get all Appointments from the Operator
     * @throws Exception Throws Exception if op is null
     */
    public ReminderHandler(classes.Operator op) throws Exception {
        if (op != null) {
            this.op = op;
        } else {
            throw new Exception("Operator can't be null.");
        }
    }

    /**
     * run() is called if ReminderHandler.start() is called.
     */
    public void run() {
        
        //create new List for Events, where the user will be reminded
        java.util.ArrayList<classes.Event> toBeRemindedList = new java.util.ArrayList<classes.Event>();

        DatabaseHandler db = new DatabaseHandler();

        while (true) {

            //Get all Events where current User hasn't been reminded from Database
            java.util.ArrayList<classes.Event> reminderList = db.getNotNotifiedEventsFromUser(this.op.getUserId());
            //clear toBeReminded list so user doesn't get reminded twice or more 
            toBeRemindedList.clear();

            try {
                //Wait 1 second until continue
                Thread.sleep(1000);

                for (classes.Event evt : reminderList) {

                    //if event date is after today, otherwise event is already over, so there is no consideration at all
                    if (evt.getDate().isAfter(java.time.LocalDateTime.now())) {
                        //when reminder date is before today, there was no reminder OR reminder date is equal to date from now
                        if (evt.getReminder().isBefore(java.time.LocalDateTime.now()) || evt.getReminder().isEqual(java.time.LocalDateTime.now())) {
                            //add event to be reminded to list
                            toBeRemindedList.add(evt);
                            //sets the Participant.P_notified to 1, so the user won't be reminded more than once
                            db.setUserNotified(evt.getID(), this.op.getUserId());
                        }
                    }
                }

                //build the message string for multiple reminders on same time
                String message = "";
                for (classes.Event evt : toBeRemindedList) {
                    //Send emails to all participants of an event
                    EmailHandler.emailSenderAppointmentReminder(evt);
                    //build message string
                    message += evt.getName() + ": " + evt.getDate().getDayOfMonth() + "/" + evt.getDate().getMonthValue() + 
                            "/" + evt.getDate().getYear() + " | " + (evt.getDate().getHour() > 10 ? evt.getDate().getHour() : ("0" + evt.getDate().getHour())) +
                            ":" + (evt.getDate().getMinute() > 10 ? evt.getDate().getMinute() : ("0" + evt.getDate().getMinute()));
                    message += "\n";
                }

                //when toBeReminded is not empty, the dialog will be set, otherwise there will be no dialog.
                if (!toBeRemindedList.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(forms.FrmMain.getInstance(), message, "Reminder", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (java.lang.NullPointerException e){
                System.out.println(e.getMessage());
            }
        }
    }

}