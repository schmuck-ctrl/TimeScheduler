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
        if (op != null){
            this.op = op;
        } else{
            throw new Exception("Operator can't be null.");
        }
    }

    public void run() {
        
        DatabaseHandler db = new DatabaseHandler();
        
        //java.util.ArrayList<classes.Event> reminderList = db.
        
        while (true) {
            try {
                Thread.sleep(5000);

                
                
                //call FrmMain function for reminding

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
