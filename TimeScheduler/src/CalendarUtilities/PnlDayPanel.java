/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarUtilities;

import java.awt.Color;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.event.AncestorEvent;

/**
 *
 * @author Benny
 */
public class PnlDayPanel extends javax.swing.JPanel {

    public enum TYPE {
        LAST_MONTH, CURRENT_MONTH, NEXT_MONTH
    }
    
    private java.util.ArrayList<BtnAppointment> btnAppointmentList;
    private javax.swing.JLabel lblDayNumber;
    private java.time.LocalDateTime day;
    private TYPE type;

    public PnlDayPanel(java.util.ArrayList<BtnAppointment> btnAppointmentList, java.time.LocalDateTime day) {
        this.lblDayNumber = new javax.swing.JLabel();
        this.setBtnAppointmentList(btnAppointmentList);
        this.setDay(day);
        this.initializeLblDayNumber();
    }
    
    public PnlDayPanel(){
        this.setLayout(null);
        this.lblDayNumber = new javax.swing.JLabel();
        this.btnAppointmentList = new ArrayList<>();
        this.setVisible(true);
    }

    public JLabel getLblDayNumber() {
        return lblDayNumber;
    }

    public void setLblDayNumber(JLabel lblDayNumber) {
        this.lblDayNumber = lblDayNumber;
    }

    public LocalDateTime getDay() {
        return day;
    }

    public void setDay(LocalDateTime day) {
        this.day = day;
        this.initializeLblDayNumber();
    }

    public void setBtnAppointmentList(ArrayList<BtnAppointment> btnAppointmentList) {
        if (btnAppointmentList == null) {
            return;
        }
        if (btnAppointmentList.size() > 0) {
            this.btnAppointmentList.addAll(btnAppointmentList);
        }
    }

    public ArrayList<BtnAppointment> getBtnAppointmentList() {
        return this.btnAppointmentList;
    }

    private void initializeLblDayNumber() {
        this.lblDayNumber.setText(day.getDayOfMonth() + ".");
        
        //add resize listener
        this.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {      
                scaleLblDayNumber();               
            }
        });
        
        this.add(this.lblDayNumber);
        
        this.scaleLblDayNumber();
    }

    private void scaleLblDayNumber() {
        //calculate position and size of label
        this.lblDayNumber.setBounds((int) Math.ceil((float) (this.getWidth() / 30)),
                (int) Math.ceil((float) (this.getHeight() / 30)), this.getWidth() / 5,
                this.getHeight() / 5);
        //scale the size of label
        this.lblDayNumber.setFont(new java.awt.Font(this.lblDayNumber.getFont().getName(), java.awt.Font.PLAIN, this.getWidth() / 10));
        
        if (this.day.getDayOfWeek() == DayOfWeek.SUNDAY){
            this.lblDayNumber.setForeground(Color.red);
        }
    }

    public void setType (TYPE type){
        if (type == TYPE.CURRENT_MONTH){
            this.setBackground(java.awt.Color.WHITE);
        } else{
            this.setBackground(java.awt.Color.LIGHT_GRAY);
        }
    }
    
}
