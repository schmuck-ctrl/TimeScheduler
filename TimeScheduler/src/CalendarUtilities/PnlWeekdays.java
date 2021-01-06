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
public class PnlWeekdays extends javax.swing.JPanel {

    //Weekdays
    private final String[] weekdays = {"Monday", "Tuesday", "Wednsday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final java.util.ArrayList<javax.swing.JLabel> lblList;
    private final int height;
    private int padding;

    public PnlWeekdays(int height) {
        super();

        //initialize global variables
        this.lblList = new ArrayList<>();
        this.padding = 1;
        this.height = height - this.padding;

        //some settings
        this.setLayout(null);
        this.setBackground(java.awt.Color.decode("0x0088FF"));
        this.setVisible(true);
        
        //init labels
        this.createWeekdayLabels();

        //add listener
        this.addComponentListener(new ComponentAdapter() {

            public void componentResized(java.awt.event.ComponentEvent evt) {
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

    private void createWeekdayLabels() {

        for (int i = 0; i < this.weekdays.length; i++) {

            javax.swing.JLabel label = new javax.swing.JLabel();

            label.setText(this.weekdays[i]);
            label.setVisible(true);
            label.setFont(new java.awt.Font(label.getFont().getName(), java.awt.Font.PLAIN, 20));

            this.lblList.add(label);
            this.add(label);
        }
        this.setLabelPositions();
    }

    private void setLabelPositions() {

        for (int i = 0; i < this.lblList.size(); i++) {
            int xLocation = i * (int) (Math.ceil((float)this.getWidth() / 7));
            int yLocation = (int) (Math.ceil(this.getHeight() / 10));
            int width = (int)Math.ceil(this.getWidth() / 14), height = 25;
            lblList.get(i).setBounds(xLocation, yLocation, width, height);
        }
    }

    public void resize() {
        if (this.getParent() != null) {
            this.setBounds(0, 0, this.getParent().getWidth(), this.height);
        }
    }
}
