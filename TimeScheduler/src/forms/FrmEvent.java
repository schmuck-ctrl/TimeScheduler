/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import EventUtilities.AttachmentListModel;
import EventUtilities.ParticipantListModel;
import classes.Event;
import classes.Operator;
import handlers.EventHandler;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author nilss
 */
public class FrmEvent extends javax.swing.JPanel {

    // <editor-fold defaultstate="collapsed" desc="Global Variables">
    private Event event = null;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates new form FrmEvent
     */
    public FrmEvent() {
        initComponents();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    public void setEvent(Event event) {
        if (event != null) {
            this.event = event;

            txtEventName.setText(this.event.getName());
            dtPicker.datePicker.setDate(LocalDate.of(this.event.getDate().getYear(), this.event.getDate().getMonth(), this.event.getDate().getDayOfMonth()));
            dtPicker.timePicker.setTime(LocalTime.of(this.event.getDate().getHour(), this.event.getDate().getMinute()));
            txtEventDuration.setText(String.valueOf(this.event.getDuration()));
            txtEventLocation.setText(this.event.getLocation());

            switch (this.event.getPriority()) {
                case LOW:
                    cbEventNotification.getModel().setSelectedItem("low");
                    break;
                case MEDIUM:
                    cbEventNotification.getModel().setSelectedItem("medium");
                    break;
                case HIGH:
                    cbEventNotification.getModel().setSelectedItem("high");
                    break;
            }

            switch (this.event.getNotification()) {
                case NONE:
                    cbEventNotification.getModel().setSelectedItem("none");
                    break;
                case TEN_MINUTES:
                    cbEventNotification.getModel().setSelectedItem("10 minutes");
                    break;
                case ONE_HOUR:
                    cbEventNotification.getModel().setSelectedItem("1 hour");
                    break;
                case THREE_DAYS:
                    cbEventNotification.getModel().setSelectedItem("3 days");
                    break;
                case ONE_WEEK:
                    cbEventNotification.getModel().setSelectedItem("1 week");
                    break;
            }
            
            /*ParticipantListModel liModelParticipants = new ParticipantListModel();
            for (int i = 0; i < this.event.getParticipants().size(); i++) {
                liModelParticipants.addElement(this.event.getParticipants().get(i));
            }
            
            liEventParticipants.setModel(liModelParticipants);
            
            AttachmentListModel liModelAttachments = new AttachmentListModel();
            for (int i = 0; i < this.event.getAttachments().size(); i++) {
                liModelAttachments.addElement(this.event.getAttachments().get(i));
            }
            
            liEventAttachments.setModel(liModelAttachments);*/

        }
    }

    private Event getInput() {

        int id = -1;
        String name = null;
        Operator organisator = null;
        LocalDateTime date = null;
        int duration = 0;
        String location = null;
        ArrayList<Operator> participants = null;
        ArrayList<File> attachments = null;
        Event.Priority priority = null;
        Event.Notification notification = Event.Notification.NONE;
        LocalDateTime reminder = null;

        if (!txtEventName.getText().isBlank()) {
            name = txtEventName.getText();
        }

        if (!txtEventDuration.getText().isBlank()) {
            duration = Integer.parseInt(txtEventDuration.getText());
        }

        if (!txtEventLocation.getText().isBlank()) {
            location = txtEventDuration.getText();
        }

        int year = dtPicker.datePicker.getDate().getYear();
        int month = dtPicker.datePicker.getDate().getMonthValue();
        int dayOfMonth = dtPicker.datePicker.getDate().getDayOfMonth();
        int hour = dtPicker.timePicker.getTime().getHour();
        int minute = dtPicker.timePicker.getTime().getMinute();

        date = LocalDateTime.of(year, month, dayOfMonth, hour, minute);

        switch (cbEventNotification.getSelectedItem().toString()) {
            case "none":
                notification = Event.Notification.NONE;
                break;
            case "10 minutes":
                notification = Event.Notification.TEN_MINUTES;
                break;
            case "1 hour":
                notification = Event.Notification.ONE_HOUR;
                break;
            case "3 days":
                notification = Event.Notification.THREE_DAYS;
                break;
            case "1 week":
                notification = Event.Notification.ONE_WEEK;
                break;
            default:
                break;
        }

        switch (cbEventPriority.getSelectedItem().toString()) {
            case "low":
                priority = Event.Priority.LOW;
                break;
            case "medium":
                priority = Event.Priority.MEDIUM;
                break;
            case "high":
                priority = Event.Priority.HIGH;
                break;
            default:
                break;
        }

        for (int i = 0; i < liEventParticipants.getModel().getSize(); i++) {
            Operator participant = new handlers.UserHandler().getUser(liEventParticipants.getModel().getElementAt(i));
            participants.add(participant);
        }

        for (int i = 0; i < liEventAttachments.getModel().getSize(); i++) {
            File attachment = new File(liEventParticipants.getModel().getElementAt(i));
            attachments.add(attachment);
        }
        
        return this.event;
    }
    
    private void newEvent(Event newEvent) {
        if(newEvent != null) {
            EventHandler eHandler = new EventHandler();
            eHandler.addEvent(0, newEvent);
        }
    }
    
    private void editEvent(Event event) {
        if(event != null) {
            EventHandler eHandler = new EventHandler();
            eHandler.editEvent(WIDTH, event);
        }
    }

// </editor-fold>
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
        lblEventName = new javax.swing.JLabel();
        txtEventName = new javax.swing.JTextField();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        lblDateTime = new javax.swing.JLabel();
        dtPicker = new com.github.lgooddatepicker.components.DateTimePicker();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        lblEventDuration = new javax.swing.JLabel();
        txtEventDuration = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        lblEventLocation = new javax.swing.JLabel();
        txtEventLocation = new javax.swing.JTextField();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        lblEventPriority = new javax.swing.JLabel();
        cbEventPriority = new javax.swing.JComboBox<>();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        lblEventNotification = new javax.swing.JLabel();
        cbEventNotification = new javax.swing.JComboBox<>();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        lblEventParticipants = new javax.swing.JLabel();
        pnlEventParticipants = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        liEventParticipants = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        lblEventAttachments = new javax.swing.JLabel();
        pnlEventAttachments = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        liEventAttachments = new javax.swing.JList<>();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        pnlFooter = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(200, 300));
        setPreferredSize(new java.awt.Dimension(200, 300));
        setLayout(new java.awt.BorderLayout());

        pnlHeader.setPreferredSize(new java.awt.Dimension(400, 30));
        pnlHeader.setLayout(new java.awt.BorderLayout());
        pnlHeader.add(lblHeadline, java.awt.BorderLayout.CENTER);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlContent.setLayout(new javax.swing.BoxLayout(pnlContent, javax.swing.BoxLayout.Y_AXIS));

        lblEventName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventName.setText("Name:");
        lblEventName.setToolTipText("");
        pnlContent.add(lblEventName);
        pnlContent.add(txtEventName);
        pnlContent.add(filler3);

        lblDateTime.setText("Date:");
        pnlContent.add(lblDateTime);
        pnlContent.add(dtPicker);
        pnlContent.add(filler4);

        lblEventDuration.setText("Duration");
        pnlContent.add(lblEventDuration);
        pnlContent.add(txtEventDuration);
        pnlContent.add(filler1);

        lblEventLocation.setText("Location");
        pnlContent.add(lblEventLocation);
        pnlContent.add(txtEventLocation);
        pnlContent.add(filler2);

        lblEventPriority.setText("Priority");
        pnlContent.add(lblEventPriority);

        cbEventPriority.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "low", "medium", "high" }));
        pnlContent.add(cbEventPriority);
        pnlContent.add(filler5);

        lblEventNotification.setText("Notification:");
        pnlContent.add(lblEventNotification);

        cbEventNotification.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "none", "10 minutes", "1 hour", "3 days", "1 week" }));
        pnlContent.add(cbEventNotification);
        pnlContent.add(filler6);

        lblEventParticipants.setText("Participants:");
        pnlContent.add(lblEventParticipants);

        jScrollPane1.setViewportView(liEventParticipants);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-circle-line.png"))); // NOI18N

        javax.swing.GroupLayout pnlEventParticipantsLayout = new javax.swing.GroupLayout(pnlEventParticipants);
        pnlEventParticipants.setLayout(pnlEventParticipantsLayout);
        pnlEventParticipantsLayout.setHorizontalGroup(
            pnlEventParticipantsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEventParticipantsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        pnlEventParticipantsLayout.setVerticalGroup(
            pnlEventParticipantsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEventParticipantsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEventParticipantsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEventParticipantsLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlContent.add(pnlEventParticipants);
        pnlContent.add(filler7);

        lblEventAttachments.setText("Attachments:");
        pnlContent.add(lblEventAttachments);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-circle-line.png"))); // NOI18N

        jScrollPane2.setViewportView(liEventAttachments);

        javax.swing.GroupLayout pnlEventAttachmentsLayout = new javax.swing.GroupLayout(pnlEventAttachments);
        pnlEventAttachments.setLayout(pnlEventAttachmentsLayout);
        pnlEventAttachmentsLayout.setHorizontalGroup(
            pnlEventAttachmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEventAttachmentsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );
        pnlEventAttachmentsLayout.setVerticalGroup(
            pnlEventAttachmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEventAttachmentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEventAttachmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlContent.add(pnlEventAttachments);
        pnlContent.add(filler8);

        add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setLayout(new java.awt.GridLayout(1, 0));

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete-bin-line.png"))); // NOI18N
        btnDelete.setText("Delete");
        pnlFooter.add(btnDelete);

        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save-line.png"))); // NOI18N
        btnOk.setText("Ok");
        pnlFooter.add(btnOk);

        add(pnlFooter, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<String> cbEventNotification;
    private javax.swing.JComboBox<String> cbEventPriority;
    private com.github.lgooddatepicker.components.DateTimePicker dtPicker;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblEventAttachments;
    private javax.swing.JLabel lblEventDuration;
    private javax.swing.JLabel lblEventLocation;
    private javax.swing.JLabel lblEventName;
    private javax.swing.JLabel lblEventNotification;
    private javax.swing.JLabel lblEventParticipants;
    private javax.swing.JLabel lblEventPriority;
    private javax.swing.JLabel lblHeadline;
    private javax.swing.JList<String> liEventAttachments;
    private javax.swing.JList<String> liEventParticipants;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlEventAttachments;
    private javax.swing.JPanel pnlEventParticipants;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTextField txtEventDuration;
    private javax.swing.JTextField txtEventLocation;
    private javax.swing.JTextField txtEventName;
    // End of variables declaration//GEN-END:variables
}
