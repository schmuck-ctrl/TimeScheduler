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
public class BtnAppointment extends javax.swing.JButton {

    private classes.Event event;

    public BtnAppointment(classes.Event event) {
        //set class attributes
        this.setEvent(event);

        //some properties
        this.setLayout(null);
        this.setVisible(true);
        this.setName("btnAppointment");
        this.setText(event.getDate().getHour() + ":" + event.getDate().getMinute() + " : " + event.getName());
        this.setLayout(null);
        this.setFocusPainted(false);
        this.setFocusable(false);
        this.setBorderPainted(false);
        
        //set color of button
        this.setPriority(event.getPriority());
        
        //add listener
       this.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forms.FrmMain.getInstance().displayEventDetails(event.getID());
            }
       });
       
       this.addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2){
                    forms.FrmMain.getInstance().editEvent(event.getID());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
       });
    
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
}
