/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

/**
 *
 * @author Benny
 */
public class ReminderHandler extends Thread {

    classes.Operator op;

    public ReminderHandler(classes.Operator op) throws Exception {
        if (op != null) {
            this.op = op;
        } else {
            throw new Exception("Operator can't be null.");
        }
    }

    public void run() {

        java.util.ArrayList<classes.Event> toBeRemindedList = new java.util.ArrayList<classes.Event>();

        DatabaseHandler db = new DatabaseHandler();

        while (true) {

            java.util.ArrayList<classes.Event> reminderList = db.getNotNotifiedEventsFromUser(this.op.getUserId());
            toBeRemindedList.clear();

            try {
                Thread.sleep(1000);

                for (classes.Event evt : reminderList) {

                    if (evt.getDate().isAfter(java.time.LocalDateTime.now())) {

                        if (evt.getReminder().isBefore(java.time.LocalDateTime.now()) || evt.getReminder().isEqual(java.time.LocalDateTime.now())) {

                            toBeRemindedList.add(evt);

                            db.setUserNotified(evt.getID(), this.op.getUserId());
                        }
                    }
                }

                String message = "";
                for (classes.Event evt : toBeRemindedList) {
                    message += evt.getName() + ": " + evt.getDate().getDayOfMonth() + "/" + evt.getDate().getMonthValue() + 
                            "/" + evt.getDate().getYear() + " | " + (evt.getDate().getHour() > 10 ? evt.getDate().getHour() : ("0" + evt.getDate().getHour())) +
                            ":" + (evt.getDate().getMinute() > 10 ? evt.getDate().getMinute() : ("0" + evt.getDate().getMinute()));
                    message += "\n";
                }

                if (!toBeRemindedList.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(forms.FrmMain.getInstance(), message, "Reminder", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }

                //call FrmMain function for reminding
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
