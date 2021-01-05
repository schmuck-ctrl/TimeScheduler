/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarUtilities;

import java.util.ArrayList;

/**
 *
 * @author Benny
 */
public class PnlWeekdays extends javax.swing.JPanel{
    
    //Weekdays
    private final String [] weekdays = {"Monday", "Tuesday", "Wednsday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final java.util.ArrayList<javax.swing.JLabel> lblList;
    
    public PnlWeekdays(){
        super();
        
        //initialize global variables
        this.lblList = new ArrayList<>();
        
        //some settings
        this.setLayout(null);
        this.setBackground(java.awt.Color.decode("0x0088FF"));
    }
    
    private void createWeekdayLabels(){
        
        for (int i = 0; i < this.weekdays.length; i++){
            
            javax.swing.JLabel label = new javax.swing.JLabel();
            
            label.setText(this.weekdays[i]);
            label.setName("lbl_" + weekdays[i]);
            label.setVisible(true);
            
            this.lblList.add(label);
            
        }
    }
    
    private void refreshLabels(){
        
    }
    
}
