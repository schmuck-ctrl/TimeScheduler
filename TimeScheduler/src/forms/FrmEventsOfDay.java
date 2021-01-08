/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import EventUtilities.EventsOfDayListModel;
import classes.Event;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author nilss
 */
public class FrmEventsOfDay extends javax.swing.JPanel {

    private EventsOfDayListModel liModelEventsOfDay;

    /**
     * Creates new form FrmEventsOfDay
     * @param eventsOfDay
     */
    public FrmEventsOfDay(ArrayList<Event> eventsOfDay) {
        initComponents();

        pnlHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlContent.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        liModelEventsOfDay = new EventsOfDayListModel();

        liEventsOfDay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setEventds(eventsOfDay);
    }

    public void setEventds(ArrayList<Event> eventsOfDay) {
        if (eventsOfDay.size() > 0) {

            liModelEventsOfDay.addElement(eventsOfDay);

            liEventsOfDay.setModel((ListModel) liModelEventsOfDay);
            
            lblHeadline.setText("Your daily appointments:");
            this.liEventsOfDay.setVisible(true);
            btnOpen.setVisible(true);
        } else {
            lblHeadline.setText("No events for today!");
            this.liEventsOfDay.setVisible(false);
            btnOpen.setVisible(false);
        }
    }

    private void displayEventDetail(Event event) {
        if (event != null) {
            JDialog dialog = new JDialog();

            dialog.setSize(500, 700);
            dialog.setModal(true);
            dialog.add(new FrmEvent(event));
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
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
        lblHeadline = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        liEventsOfDay = new javax.swing.JList<>();
        pnlFooter = new javax.swing.JPanel();
        btnOpen = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        pnlHeader.setPreferredSize(new java.awt.Dimension(313, 45));
        pnlHeader.setLayout(new java.awt.BorderLayout());
        pnlHeader.add(lblHeadline, java.awt.BorderLayout.CENTER);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlContent.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(liEventsOfDay);

        pnlContent.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setPreferredSize(new java.awt.Dimension(313, 45));
        pnlFooter.setLayout(new java.awt.BorderLayout());

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar-2-line.png"))); // NOI18N
        btnOpen.setText("Open");
        btnOpen.setPreferredSize(new java.awt.Dimension(95, 35));
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        pnlFooter.add(btnOpen, java.awt.BorderLayout.CENTER);

        add(pnlFooter, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        Event selctedEvent = liModelEventsOfDay.getElementAt(liEventsOfDay.getSelectedIndex());
        displayEventDetail(selctedEvent);
    }//GEN-LAST:event_btnOpenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOpen;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHeadline;
    private javax.swing.JList<String> liEventsOfDay;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    // End of variables declaration//GEN-END:variables
}
