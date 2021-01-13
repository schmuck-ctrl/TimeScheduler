/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarUtilities;

/**
 *
 * @author Benny
 */
public class BtnAppointment extends javax.swing.JButton implements Comparable<BtnAppointment>{

    //Event, which the button is going to display
    private classes.Event event;

    /**
     * Initializes a Button with an Event.
     * @param event Receives a classes.Event to represent this event in the button.
     */
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

    /**
     * Sets the event.
     * @param event classes.Event, the event to be setted.
     */
    public void setEvent(classes.Event event) {
        if (event != null) {
            this.event = event;
        }
    }
    
    /**
     * Getter of classes.Event propertie.
     * @return returns a classes.Event.
     */
    public classes.Event getEvent(){
        return this.event;
    }

    /**
     * Sets the Color of the Button based on the Priority of the this.event
     * @param priority Receives a classes.Event.Priority Enum.
     */
    public void setPriority(classes.Event.Priority priority) {
        //Red if HIGH, Yellow if MEDIUM, Green if LOW
        if (priority == classes.Event.Priority.HIGH){
            this.setBackground(java.awt.Color.red);
        } else if (priority == classes.Event.Priority.MEDIUM){
            this.setBackground(java.awt.Color.YELLOW);
        } else{
            this.setBackground(java.awt.Color.GREEN);
        }
    }

    /**
     * returns the Priority of the event
     * @return Returns classes.Priority.Event
     */
    public classes.Event.Priority getPriority(){
        return this.event.getPriority();
    }

    /**
     * 
     * Overriden function which is used to sort an ArrayList
     * 
     * @param o CalendarUtilites.BtnAppointment
     * @return returns an Integer
     */
    @Override
    public int compareTo(BtnAppointment o) {
        if(this.event.getDate() == null || o.getEvent().getDate() == null) 
            return 0;
        return this.getEvent().getDate().compareTo(o.getEvent().getDate());
    }

    
}