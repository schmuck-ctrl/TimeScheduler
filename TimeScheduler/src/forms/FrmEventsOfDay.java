/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import EventUtilities.EventsOfDayListModel;
import classes.Event;
import java.util.ArrayList;

/**
 *
 * @author nilss
 */
public class FrmEventsOfDay extends javax.swing.JPanel {

    /**
     * Creates new form FrmEventsOfDay
     */
    public FrmEventsOfDay() {
        initComponents();
    }

    public void setEventds(ArrayList<Event> eventsOfDay) {
        EventUtilities.EventsOfDayListModel liModelEventsOfDay = new EventsOfDayListModel();
        for(int i=0; i < eventsOfDay.size(); i++) {
            liModelEventsOfDay.addElement(eventsOfDay.get(i));
        }
        
        liEventsOfDay.setModel(liModelEventsOfDay);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        pnlContent = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        liEventsOfDay = new javax.swing.JList<>();
        pnlFooter = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlContent.setLayout(new java.awt.BorderLayout());

        liEventsOfDay.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        liEventsOfDay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                liEventsOfDayMouseClicked(evt);
            }
        });
        liEventsOfDay.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                liEventsOfDayValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(liEventsOfDay);

        pnlContent.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(pnlContent, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout pnlFooterLayout = new javax.swing.GroupLayout(pnlFooter);
        pnlFooter.setLayout(pnlFooterLayout);
        pnlFooterLayout.setHorizontalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
        );
        pnlFooterLayout.setVerticalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        add(pnlFooter, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void liEventsOfDayValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_liEventsOfDayValueChanged
        System.out.println(liEventsOfDay.getSelectedValue().toString());
    }//GEN-LAST:event_liEventsOfDayValueChanged

    private void liEventsOfDayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_liEventsOfDayMouseClicked
        System.out.println(liEventsOfDay.getSelectedValue().toString());
    }//GEN-LAST:event_liEventsOfDayMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> liEventsOfDay;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    // End of variables declaration//GEN-END:variables
}
