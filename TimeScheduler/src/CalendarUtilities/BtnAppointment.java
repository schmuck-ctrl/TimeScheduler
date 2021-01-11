/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarUtilities;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Benny
 */
public class BtnAppointment extends javax.swing.JButton implements Comparable<BtnAppointment>{

    private classes.Event event;

    public BtnAppointment(classes.Event event) {
        //set class attributes
        this.setEvent(event);

        //some properties
        this.setLayout(null);
        this.setVisible(true);
        this.setName("btnAppointment");
        this.setText((event.getDate().getHour() < 10 ? "0" + event.getDate().getHour() : event.getDate().getHour()) + 
                ":" + (event.getDate().getMinute() < 10 ? "0" + event.getDate().getMinute() : event.getDate().getMinute()) + " : " + event.getName());
        this.setLayout(null);
        this.setFocusPainted(false);
        this.setFocusable(false);
        this.setBorderPainted(false);
        
        //set color of button
        this.setPriority(event.getPriority());
    }

    public void setEvent(classes.Event event) {
        if (event != null) {
            this.event = event;
        }
    }
    
    public classes.Event getEvent(){
        return this.event;
    }

    public void setPriority(classes.Event.Priority priority) {
        if (priority == classes.Event.Priority.HIGH){
            this.setBackground(Color.red);
        } else if (priority == classes.Event.Priority.MEDIUM){
            this.setBackground(Color.YELLOW);
        } else{
            this.setBackground(Color.GREEN);
        }
    }

    public classes.Event.Priority getPriority(){
        return this.event.getPriority();
    }

    @Override
    public int compareTo(BtnAppointment o) {
        if(this.event.getDate() == null || o.getEvent().getDate() == null) 
            return 0;
        return this.getEvent().getDate().compareTo(o.getEvent().getDate());
    }

    
}