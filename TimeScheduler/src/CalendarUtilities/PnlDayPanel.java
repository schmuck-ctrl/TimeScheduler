/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarUtilities;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
    private java.time.LocalDate day;
    private java.awt.Color selectionColor;
    private TYPE type;

    public PnlDayPanel(java.util.ArrayList<BtnAppointment> btnAppointmentList, java.time.LocalDate day) {
        this.lblDayNumber = new javax.swing.JLabel();
        this.setBtnAppointmentList(btnAppointmentList);
        this.setDay(day);
        this.initializeLblDayNumber();
        this.scaleLblDayNumber();
    }

    public PnlDayPanel() {
        this.selectionColor = Color.decode("0x0088FF");
        this.setLayout(null);
        this.lblDayNumber = new javax.swing.JLabel();
        this.lblDayNumber.setVisible(true);
        this.lblDayNumber.setLayout(null);
        this.add(this.lblDayNumber);
        this.btnAppointmentList = new ArrayList<>();
        this.setVisible(true);
        
        //add listener
        this.addComponentListener(new java.awt.event.ComponentAdapter() {

            public void componentResized(java.awt.event.ComponentEvent evt) {
                scaleButtons();
            }

        });
    }

    public JLabel getLblDayNumber() {
        return lblDayNumber;
    }

    public void setLblDayNumber(JLabel lblDayNumber) {
        this.lblDayNumber = lblDayNumber;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
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

        this.remove(this.lblDayNumber);

        this.lblDayNumber = new javax.swing.JLabel();
        this.lblDayNumber.setText(day.getDayOfMonth() + ".");

        //add resize listener
        this.lblDayNumber.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
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

        this.lblDayNumber.setVisible(true);

        //calculate position and size of label
        this.lblDayNumber.setBounds((int) Math.ceil((float) (this.getWidth() / 30)),
                (int) Math.ceil((float) (this.getHeight() / 30)), this.getWidth() / 5,
                this.getHeight() / 5);
        //scale the size of label
        this.lblDayNumber.setFont(new java.awt.Font(this.lblDayNumber.getFont().getName(), java.awt.Font.PLAIN, this.getWidth() / 10));

        int year = this.day.getYear();

//        System.out.println("Date:\t" + this.day.getDayOfMonth() + "/" + this.day.getMonthValue() + "/" + this.day.getYear() + "\tDay:\t" + this.day.getDayOfWeek().toString() + "   \tMonth:\t" + this.day.getMonthValue() + "\tYear:\t" + this.day.getYear());
        if (this.day.getDayOfWeek() == DayOfWeek.SUNDAY) {
            this.lblDayNumber.setForeground(Color.red);
        }
    }

    private void addButtonListenr(BtnAppointment btn){
        btn.addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
                if (e.getClickCount() >= 2){
                    forms.FrmMain.getInstance().editEvent(btn.getEvent().getID());
                }
                
                clearButtonSelection();
                btn.setBackground(java.awt.Color.DARK_GRAY);
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
    
        btn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                forms.FrmMain.getInstance().displayEventDetails(btn.getEvent().getID());
            }
       });
    }
    
    private void clearButton(){
        for (BtnAppointment btn : this.btnAppointmentList)
            btn.setPriority(btn.getEvent().getPriority());
    }
    
    public void setType(TYPE type) {
        this.type = type;
        if (type == TYPE.CURRENT_MONTH) {
            this.setBackground(java.awt.Color.WHITE);
        } else {
            this.setBackground(java.awt.Color.LIGHT_GRAY);
        }
    }

    public TYPE getType() {
        return this.type;
    }

    public void setSelected() {
        this.setBackground(this.selectionColor);
    }

    public void setUnselected() {
        this.setType(this.type);
    }

    public void addAppointment(classes.Event event) {
        BtnAppointment btn = new BtnAppointment(event);

        this.addButtonListenr(btn);

        this.btnAppointmentList.add(btn);
        this.add(btn);
        
        java.util.Collections.sort(this.btnAppointmentList);
    }

    public void scaleButtons() {
        String showMoreBtnText = "showMore";
        //hide all appointments
        for (BtnAppointment btn : this.btnAppointmentList){
            btn.setVisible(false);
        }
        
        //delete all show more buttons
        for (java.awt.Component c : this.getComponents()){
            if(c.getName() != null && c.getName().equals(showMoreBtnText))
                this.remove(c);
        }
        
        
        int count = 0;
        int maxCount = 3;
        int buttonPadding = (int)Math.ceil((float)this.getHeight() / 40);
        int labelOffset = (int) (Math.ceil((float)this.getHeight() / 5));
        
        for (BtnAppointment bt : this.btnAppointmentList){
            if (count < maxCount){
                bt.setBounds(0, count * (int) Math.ceil((float)this.getHeight() / 4) + labelOffset, this.getWidth(), (int) Math.ceil((float)this.getHeight() / 5) - buttonPadding);
                bt.setVisible(true);
                count++;
            } else if (count >= maxCount && this.btnAppointmentList.size() > maxCount){
                
                int width = this.getWidth() / 4;
                int height = this.getHeight() / 10;
                
                javax.swing.JButton button = new BtnShowAllAppointments(day);
                button.setName(showMoreBtnText);
                button.setBounds((this.getWidth() / 2) - (width / 2), this.getHeight() - height, width, height);
                
                
                this.add(button);
                
                this.repaint();
                this.revalidate();
            }
        }
    }

    public void clearButtonSelection(){

        if (this.getParent() instanceof javax.swing.JPanel){
            for (java.awt.Component c : ((javax.swing.JPanel)this.getParent()).getComponents()){
                if (c instanceof PnlDayPanel){
                    ((PnlDayPanel) c).clearButton();
                }
            }
        }
    }
}