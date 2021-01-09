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

    java.util.ArrayList<classes.Event> evtList;

    public ReminderHandler(java.util.ArrayList<classes.Event> evtList) {
        if (evtList != null && evtList.size() > 0) {
            this.evtList = evtList;
        }
    }

    public void run() {
        
        java.util.ArrayList<classes.Event> reminderList = new java.util.ArrayList<>();
        
        while (true) {
            try {
                Thread.sleep(5000);

                for (classes.Event e : evtList) {

                    if (e.getDate().isAfter(java.time.LocalDateTime.now())) {

                        if (e.getReminder().isBefore(java.time.LocalDateTime.now()) || e.getReminder().isEqual(java.time.LocalDateTime.now())) {

                            reminderList.add(e);
                            
                        }

                    }

                }
                
                //call FrmMain function for reminding

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
