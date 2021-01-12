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
import java.awt.event.ActionEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author nilss
 */
public class FrmEvent extends javax.swing.JPanel {

    public enum View {
        READ,
        EDIT,
        NEW
    }

    private ParticipantListModel liModelParticipants = null;
    private AttachmentListModel liModelAttachments = null;
    private int eventID = -1;

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates new form FrmEvent
     */
    public FrmEvent(Event event, View view) {
        initComponents();

        pnlHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlContent.setBorder(new EmptyBorder(10, 10, 10, 10));

        liModelParticipants = new ParticipantListModel();
        liModelAttachments = new AttachmentListModel();

        handleView(view);
        setEvent(event);
    }

    public FrmEvent(View view, LocalDate date) {
        initComponents();

        pnlHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlContent.setBorder(new EmptyBorder(10, 10, 10, 10));

        liModelParticipants = new ParticipantListModel();
        liModelAttachments = new AttachmentListModel();

        clearInput(date);
        handleView(view);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    public void setEvent(Event event) {
        if (event != null) {
            this.eventID = event.getID();

            txtEventName.setText(event.getName());
            dtPicker.datePicker.setDate(LocalDate.of(event.getDate().getYear(), event.getDate().getMonth(), event.getDate().getDayOfMonth()));
            dtPicker.timePicker.setTime(LocalTime.of(event.getDate().getHour(), event.getDate().getMinute()));
            txtEventHost.setText(event.getHost().getFirstName() + " " + event.getHost().getLastName());
            txtEventDuration.setText(String.valueOf(event.getDuration()));
            txtEventLocation.setText(event.getLocation());

            switch (event.getPriority()) {
                case LOW:
                    cbEventPriority.getModel().setSelectedItem("low");
                    break;
                case MEDIUM:
                    cbEventPriority.getModel().setSelectedItem("medium");
                    break;
                case HIGH:
                    cbEventPriority.getModel().setSelectedItem("high");
                    break;
            }

            switch (event.getNotification()) {
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

            if (event.getParticipants() != null) {
                liModelParticipants.addElement(event.getParticipants());
                liEventParticipants.setModel((ListModel) liModelParticipants);
            }

            if (event.getAttachments() != null) {
                liModelAttachments.addElement(event.getAttachments());
                liEventAttachments.setModel((ListModel) liModelAttachments);
            }

        }
    }

    private Event getInput() {

        String name = null;
        Operator host = FrmMain.getInstance().getCurrentUser();
        LocalDateTime date = null;
        int duration = 0;
        String location = null;
        ArrayList<Operator> participants = new ArrayList();
        ArrayList<File> attachments = new ArrayList();
        Event.Priority priority = null;
        Event.Notification notification = Event.Notification.NONE;
        LocalDateTime reminder = null;

        if (!txtEventName.getText().isBlank()) {
            name = txtEventName.getText();
        }

        if (!txtEventDuration.getText().isBlank() && txtEventDuration.getText().matches("[0-9]+")) {
            
            duration = Integer.parseInt(txtEventDuration.getText());
        }

        if (!txtEventLocation.getText().isBlank()) {
            location = txtEventLocation.getText();
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
            Operator participant = liModelParticipants.getElementAt(i);
            participants.add(participant);
        }

        for (int i = 0; i < liEventAttachments.getModel().getSize(); i++) {
            File attachment = liModelAttachments.getElementAt(i);
            attachments.add(attachment);
        }

        Event newEvent = null;
        if (this.eventID == -1) {
            newEvent = new Event(name, host, date, duration, location, participants, attachments, priority, notification);
        } else {
            newEvent = new Event(eventID, name, host, date, duration, location, participants, attachments, priority, notification);
        }

        return newEvent;
    }

    private void clearInput(LocalDate date) {
        String name = FrmMain.getInstance().getCurrentUser().getFirstName() + " " + FrmMain.getInstance().getCurrentUser().getLastName();
        this.txtEventHost.setText(name);

        this.dtPicker.datePicker.setDate(date);
        this.dtPicker.timePicker.setTimeToNow();
        
        this.txtEventName.setText("");
        this.txtEventDuration.setText("");
        this.txtEventLocation.setText("");
        
        cbEventPriority.setSelectedIndex(0);
        cbEventNotification.setSelectedIndex(0);
        
        if(liModelAttachments.getSize() > 0)
            liModelAttachments.removeAll();
        if(liModelParticipants.getSize() > 0)
            liModelParticipants.removeAll();
        
        clearFooter();
    }

    private void clearFooter() {
        this.pnlFooter.removeAll();
        this.pnlFooter.revalidate();
        this.pnlFooter.repaint();
    }

    private void enableControls(boolean isEnabled) {
        txtEventName.setEditable(isEnabled);
        txtEventLocation.setEditable(isEnabled);
        txtEventDuration.setEditable(isEnabled);
        txtEventHost.setEditable(false);

        dtPicker.setEnabled(isEnabled);
        dtPicker.datePicker.getComponentDateTextField().setEditable(false);
        dtPicker.timePicker.getComponentTimeTextField().setEditable(false);

        cbEventPriority.setEnabled(isEnabled);
        cbEventNotification.setEnabled(isEnabled);

        liEventAttachments.setEnabled(isEnabled);
        liEventParticipants.setEnabled(isEnabled);

    }

    private void handleView(View view) {
        clearFooter();

        if (view == View.NEW) {
            enableControls(true);

            JButton btnNew = new JButton("Create new event", new javax.swing.ImageIcon(getClass().getResource("/icons/save-line.png")));

            btnNew.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnNewActionPerformed(e);
                }
            });
            pnlFooter.add(btnNew);
        } else if (view == View.READ) {
            enableControls(false);

            JButton btnDisplayEditView = new JButton("Edit", new javax.swing.ImageIcon(getClass().getResource("/icons/pencil-line.png")));

            btnDisplayEditView.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnDisplayEditViewActoinPerformed(e);
                }
            });

            pnlFooter.add(btnDisplayEditView);
        } else if (view == View.EDIT) {
            enableControls(true);

            JButton btnDelete = new JButton("Delete", new javax.swing.ImageIcon(getClass().getResource("/icons/delete-bin-line.png")));

            btnDelete.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnDeleteActionPerformed(e);
                }

            });

            JButton btnEdit = new JButton("Speichern", new javax.swing.ImageIcon(getClass().getResource("/icons/save-line.png")));

            btnEdit.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnEditActoinPerformed(e);
                }

            });

            pnlFooter.add(btnDelete);
            pnlFooter.add(btnEdit);
        }
    }

    public void setTitle(String title) {
        if (!title.isBlank()) {
            this.lblHeadline.setText(title);
        }
    }

    private void newEvent(Event newEvent) {
        if (newEvent != null) {
            EventHandler eHandler = new EventHandler();
            eHandler.addEvent(newEvent);
        }
    }

    private void editEvent(Event event) {
        if (event != null) {
            EventHandler eHandler = new EventHandler();
            eHandler.editEvent(event);
        }
    }

    private void deleteEvent(int eventID) {
        if (eventID >= 0) {
            EventHandler eHandler = new EventHandler();
            eHandler.deleteEvent(eventID);
        }
    }

    private void btnDisplayEditViewActoinPerformed(ActionEvent e) {
        handleView(View.EDIT);
    }

    private void btnNewActionPerformed(ActionEvent e) {
        Event event = getInput();
        newEvent(event);

        refreshCalendar(dtPicker.datePicker.getDate());
        
        this.setVisible(false);
    }

    private void btnEditActoinPerformed(ActionEvent e) {
        Event event = getInput();
        editEvent(event);

        refreshCalendar(dtPicker.datePicker.getDate());
        
        this.setVisible(false);
    }

    private void btnDeleteActionPerformed(ActionEvent e) {
        int retVal = JOptionPane.showConfirmDialog(FrmMain.getInstance(), "Are you sure you want to delete the event?", "Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (retVal == JOptionPane.YES_OPTION) {
            deleteEvent(this.eventID);

            refreshCalendar(dtPicker.datePicker.getDate());
            
            this.setVisible(false);
        }
    }

    private void refreshCalendar(LocalDate date) {
        EventHandler eHandler = new EventHandler();

        int userId = FrmMain.getInstance().getCurrentUser().getUserId();

        FrmMain.getInstance().getCalendar().setLocalDate(date);

        LocalDate start = FrmMain.getInstance().getCalendar().getFirstDayOfView();
        LocalDate end = FrmMain.getInstance().getCalendar().getLastDayOfView();

        FrmMain.getInstance().getCalendar().addEvents(eHandler.getEventsOfPeriod(userId, start, end));
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
        java.awt.GridBagConstraints gridBagConstraints;

        pnlHeader = new javax.swing.JPanel();
        lblHeadline = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        lblEventName = new javax.swing.JLabel();
        txtEventName = new javax.swing.JTextField();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        lblDateTime = new javax.swing.JLabel();
        dtPicker = new com.github.lgooddatepicker.components.DateTimePicker();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        lblEventHost = new javax.swing.JLabel();
        txtEventHost = new javax.swing.JTextField();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
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

        setMinimumSize(new java.awt.Dimension(200, 300));
        setPreferredSize(new java.awt.Dimension(200, 300));
        setLayout(new java.awt.BorderLayout());

        pnlHeader.setPreferredSize(new java.awt.Dimension(400, 30));
        pnlHeader.setLayout(new java.awt.BorderLayout());

        lblHeadline.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pnlHeader.add(lblHeadline, java.awt.BorderLayout.CENTER);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlContent.setLayout(new javax.swing.BoxLayout(pnlContent, javax.swing.BoxLayout.Y_AXIS));

        lblEventName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventName.setText("Name:");
        lblEventName.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblEventName.setToolTipText("");
        pnlContent.add(lblEventName);

        txtEventName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlContent.add(txtEventName);
        pnlContent.add(filler3);

        lblDateTime.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDateTime.setText("Date:");
        lblDateTime.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        pnlContent.add(lblDateTime);
        pnlContent.add(dtPicker);
        pnlContent.add(filler4);

        lblEventHost.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventHost.setText("Host:");
        lblEventHost.setToolTipText("");
        pnlContent.add(lblEventHost);
        pnlContent.add(txtEventHost);
        pnlContent.add(filler9);

        lblEventDuration.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventDuration.setText("Duration");
        pnlContent.add(lblEventDuration);
        pnlContent.add(txtEventDuration);
        pnlContent.add(filler1);

        lblEventLocation.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventLocation.setText("Location");
        pnlContent.add(lblEventLocation);
        pnlContent.add(txtEventLocation);
        pnlContent.add(filler2);

        lblEventPriority.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventPriority.setText("Priority");
        pnlContent.add(lblEventPriority);

        cbEventPriority.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "low", "medium", "high" }));
        pnlContent.add(cbEventPriority);
        pnlContent.add(filler5);

        lblEventNotification.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventNotification.setText("Notification:");
        pnlContent.add(lblEventNotification);

        cbEventNotification.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "none", "10 minutes", "1 hour", "3 days", "1 week" }));
        pnlContent.add(cbEventNotification);
        pnlContent.add(filler6);

        lblEventParticipants.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventParticipants.setText("Participants:");
        pnlContent.add(lblEventParticipants);

        pnlEventParticipants.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setViewportView(liEventParticipants);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 285;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 0);
        pnlEventParticipants.add(jScrollPane1, gridBagConstraints);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-circle-line.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        pnlEventParticipants.add(jButton1, gridBagConstraints);

        pnlContent.add(pnlEventParticipants);
        pnlContent.add(filler7);

        lblEventAttachments.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventAttachments.setText("Attachments:");
        pnlContent.add(lblEventAttachments);

        pnlEventAttachments.setLayout(new java.awt.GridBagLayout());

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-circle-line.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        pnlEventAttachments.add(jButton2, gridBagConstraints);

        jScrollPane2.setViewportView(liEventAttachments);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 285;
        gridBagConstraints.ipady = 34;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 0);
        pnlEventAttachments.add(jScrollPane2, gridBagConstraints);

        pnlContent.add(pnlEventAttachments);
        pnlContent.add(filler8);

        add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setLayout(new java.awt.GridLayout(1, 0));
        add(pnlFooter, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.Box.Filler filler9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblEventAttachments;
    private javax.swing.JLabel lblEventDuration;
    private javax.swing.JLabel lblEventHost;
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
    private javax.swing.JTextField txtEventHost;
    private javax.swing.JTextField txtEventLocation;
    private javax.swing.JTextField txtEventName;
    // End of variables declaration//GEN-END:variables
}
