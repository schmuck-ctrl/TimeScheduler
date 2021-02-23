/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarUtilities;

import handlers.LoggerHandler;
import java.awt.event.ComponentAdapter;
import java.util.ArrayList;

/**
 * Headline providing the layout of the days of a week
 * @author Benny
 */
public class PnlWeekdays extends javax.swing.JPanel {

    //Weekdays
    private final String[] weekdays = {"Monday", "Tuesday", "Wednsday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final java.util.ArrayList<javax.swing.JLabel> lblList;
    //height of this class
    private final int height;
    //padding is used to shorten width and height to show the grid lines
    private int padding;

    /**
     * Constructor
     * @param height Integer, used to display the height.
     */
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

        //add ComponentListener with resize, so labels will be resized if calendar gets resized
        this.addListener();
        
        LoggerHandler.logger.info("PnlWeekdays was created.");
    }
    
    /**
     * 
     * Adds fomponentlistener for resize and HierachyBoundsListener to resize if anchestor is resized.
     * 
     */
    private void addListener(){
        this.addComponentListener(new ComponentAdapter() {

            public void componentResized(java.awt.event.ComponentEvent evt) {
                setLabelPositions();
            }

        });

        //add HierarchyBoundsListener to resize this component if parent panel gets resized
        this.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }

            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                resize();
            }
        });
    }

    /**
     * Creates Labels for the number of the weekdays panels.
     */
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

    /**
     * Sets the label positions in the WeekdaysPanel
     */
    private void setLabelPositions() {

        for (int i = 0; i < this.lblList.size(); i++) {
            int xLocation = i * (int) (Math.ceil((float)this.getWidth() / 7));
            int yLocation = (int) (Math.ceil(this.getHeight() / 10));
            int width = (int)Math.ceil(this.getWidth() / 14), height = 25;
            lblList.get(i).setBounds(xLocation, yLocation, width, height);
        }
    }

    /**
     * resizes itself used to its parent
     */
    public void resize() {
        if (this.getParent() != null) {
            this.setBounds(0, 0, this.getParent().getWidth(), this.height);
        }
    }
}