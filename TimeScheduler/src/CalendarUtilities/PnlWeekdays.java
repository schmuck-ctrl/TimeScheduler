/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarUtilities;

import java.awt.event.ComponentAdapter;
import java.util.ArrayList;
import javax.swing.event.AncestorEvent;

/**
 *
 * @author Benny
 */
public class PnlWeekdays extends javax.swing.JPanel{
    
    //Weekdays
    private final String [] weekdays = {"Monday", "Tuesday", "Wednsday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final java.util.ArrayList<javax.swing.JLabel> lblList;
    private final int height;
    
    public PnlWeekdays(){
        super();
        
        //initialize global variables
        this.lblList = new ArrayList<>();
        this.height = 20;
        
        //some settings
        this.setLayout(null);
        this.setBackground(java.awt.Color.decode("0x0088FF"));
        
        //add listener
        this.addComponentListener(new ComponentAdapter() {
            
            public void componentResized(java.awt.event.ComponentEvent evt){
                setLabelPositions();
            }
            
        });
        
        this.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {      
                resize();               
            }
        });
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
    
    private void setLabelPositions(){
        
        for (int i = 0; i < this.lblList.size(); i++){
            
            lblList.get(i).setBounds((int)(Math.ceil(this.getHeight() / 3)), i * (int)(Math.ceil(this.getWidth() / 14) - this.getWidth() / 28), 20, 20);
            
        }
                
        
    }
    
    private void resize(){
        this.setBounds(0, 0, this.getParent().getWidth(), this.height);
    }
}
