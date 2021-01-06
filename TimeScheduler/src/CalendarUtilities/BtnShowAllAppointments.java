/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarUtilities;

import java.awt.event.ActionEvent;
import java.time.LocalDate;

/**
 *
 * @author Benny
 */
public class BtnShowAllAppointments extends javax.swing.JButton {
    
    private java.time.LocalDate ld;

    public LocalDate getLd() {
        return ld;
    }

    public void setLd(LocalDate ld) {
        this.ld = ld;
    }

    public BtnShowAllAppointments(java.time.LocalDate ld) {
        super();
        this.setText("...");
        this.setLd(ld);
        this.setVisible(true);
        this.setFocusPainted(false);
        
        this.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forms.FrmMain.getInstance().displayAllEventsOfDay(ld);
            }
        });
    }

}
