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
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
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
    private JDialog isDialog = null;

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates new form FrmEvent
     */
    public FrmEvent(View view, Event event) {
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

    public FrmEvent(View view, Event event, JDialog parent) {
        initComponents();

        this.isDialog = parent;

        pnlHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlContent.setBorder(new EmptyBorder(10, 10, 10, 10));

        liModelParticipants = new ParticipantListModel();
        liModelAttachments = new AttachmentListModel();

        handleView(view);
        setEvent(event);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    public void setParticipants(ArrayList<Operator> participants) {

        if (participants != null) {
            if (this.liModelParticipants.getSize() > 0) {
                this.liModelParticipants.removeAll();
            }

            this.liModelParticipants.addElement(participants);
            this.liEventParticipants.setModel((ListModel) liModelParticipants);
        }
    }

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

        boolean checkInput = true;

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
            txtEventName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        } else {
            txtEventName.setBorder(BorderFactory.createLineBorder(Color.RED));
            checkInput = false;
        }

        if (!txtEventDuration.getText().trim().isBlank()) {
            if (txtEventDuration.getText().trim().matches("[0-9]+")) {
                duration = Integer.parseInt(txtEventDuration.getText());
                txtEventDuration.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            } else {
                txtEventDuration.setBorder(BorderFactory.createLineBorder(Color.RED));
                checkInput = false;
            }
        } else {
            txtEventDuration.setBorder(BorderFactory.createLineBorder(Color.RED));
            checkInput = false;
        }

        if (!txtEventLocation.getText().isBlank()) {
            location = txtEventLocation.getText();
        }

        if (dtPicker.datePicker.getDate() != null && dtPicker.timePicker.getTime() != null) {
            int year = dtPicker.datePicker.getDate().getYear();
            int month = dtPicker.datePicker.getDate().getMonthValue();
            int dayOfMonth = dtPicker.datePicker.getDate().getDayOfMonth();
            int hour = dtPicker.timePicker.getTime().getHour();
            int minute = dtPicker.timePicker.getTime().getMinute();

            date = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
            dtPicker.setBorder(BorderFactory.createEmptyBorder());
        } else {
            dtPicker.setBorder(BorderFactory.createLineBorder(Color.RED));
            checkInput = false;
        }

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
        if (checkInput) {
            if (this.eventID == -1) {
                newEvent = new Event(name, host, date, duration, location, participants, attachments, priority, notification);
            } else {
                newEvent = new Event(eventID, name, host, date, duration, location, participants, attachments, priority, notification);
            }
        } else {

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

        this.cbEventPriority.setSelectedIndex(0);
        this.cbEventNotification.setSelectedIndex(0);

        if (this.liModelAttachments.getSize() > 0) {
            this.liModelAttachments.removeAll();
        }
        if (this.liModelParticipants.getSize() > 0) {
            this.liModelParticipants.removeAll();
        }

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
        dtPicker.timePicker.getComponentTimeTextField().setEditable(isEnabled);

        cbEventPriority.setEnabled(isEnabled);
        cbEventNotification.setEnabled(isEnabled);

        liEventAttachments.setEnabled(isEnabled);
        liEventParticipants.setEnabled(isEnabled);

        btnAddParticipants.setEnabled(isEnabled);
        btnAddAttachments.setEnabled(isEnabled);

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
        if (event != null) {
            newEvent(event);

            refreshCalendar(dtPicker.datePicker.getDate());

            this.setVisible(false);

            if (this.isDialog != null) {
                this.isDialog.dispose();;
            }
        } else {
            JOptionPane.showMessageDialog(FrmMain.getInstance(), "Please check your input.", "Invalid input", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void btnEditActoinPerformed(ActionEvent e) {
        Event event = getInput();
        if (event != null) {
            editEvent(event);

            refreshCalendar(dtPicker.datePicker.getDate());

            this.setVisible(false);

            if (this.isDialog != null) {
                this.isDialog.dispose();;
            }
        } else {
            JOptionPane.showMessageDialog(FrmMain.getInstance(), "Please check your input.", "Invalid input", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void btnDeleteActionPerformed(ActionEvent e) {
        int retVal = JOptionPane.showConfirmDialog(FrmMain.getInstance(), "Are you sure you want to delete the event?", "Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (retVal == JOptionPane.YES_OPTION) {
            deleteEvent(this.eventID);

            refreshCalendar(dtPicker.datePicker.getDate());

            this.setVisible(false);

            if (this.isDialog != null) {
                this.isDialog.dispose();
            }
        }
    }

    private void refreshCalendar(LocalDate date) {
        EventHandler eHandler = new EventHandler();

        int userId = FrmMain.getInstance().getCurrentUser().getUserId();

        LocalDate startPanelMonth = FrmMain.getInstance().getCalendar().getFirstDayOfView();
        LocalDate lastPanelMonth = FrmMain.getInstance().getCalendar().getLastDayOfView();

        if (date.isBefore(startPanelMonth) || (date.isAfter(lastPanelMonth))) {
            FrmMain.getInstance().getCalendar().setLocalDate(date);

            startPanelMonth = FrmMain.getInstance().getCalendar().getFirstDayOfView();
            lastPanelMonth = FrmMain.getInstance().getCalendar().getLastDayOfView();
        }

        FrmMain.getInstance().getCalendar().addEvents(eHandler.getEventsOfPeriod(userId, startPanelMonth, lastPanelMonth));
        FrmMain.getInstance().getCalendar().focusDayByLocalDate(date);

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
        btnAddParticipants = new javax.swing.JButton();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        lblEventAttachments = new javax.swing.JLabel();
        pnlEventAttachments = new javax.swing.JPanel();
        btnAddAttachments = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        liEventAttachments = new javax.swing.JList<>();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        lblInformation = new javax.swing.JLabel();
        pnlFooter = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(200, 300));
        setPreferredSize(new java.awt.Dimension(200, 300));
        setLayout(new java.awt.BorderLayout());

        pnlHeader.setPreferredSize(new java.awt.Dimension(400, 30));
        pnlHeader.setLayout(new java.awt.BorderLayout());

        lblHeadline.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pnlHeader.add(lblHeadline, java.awt.BorderLayout.CENTER);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlContent.setAlignmentX(0.0F);
        pnlContent.setAlignmentY(0.0F);
        pnlContent.setLayout(new javax.swing.BoxLayout(pnlContent, javax.swing.BoxLayout.Y_AXIS));

        lblEventName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventName.setText("Name: *");
        lblEventName.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblEventName.setToolTipText("");
        pnlContent.add(lblEventName);

        txtEventName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlContent.add(txtEventName);
        pnlContent.add(filler3);

        lblDateTime.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDateTime.setText("Date: *");
        lblDateTime.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        pnlContent.add(lblDateTime);
        pnlContent.add(dtPicker);
        pnlContent.add(filler4);

        lblEventHost.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventHost.setText("Host: *");
        lblEventHost.setToolTipText("");
        pnlContent.add(lblEventHost);
        pnlContent.add(txtEventHost);
        pnlContent.add(filler9);

        lblEventDuration.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventDuration.setText("Duration (min): *");
        pnlContent.add(lblEventDuration);
        pnlContent.add(txtEventDuration);
        pnlContent.add(filler1);

        lblEventLocation.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventLocation.setText("Location:");
        pnlContent.add(lblEventLocation);
        pnlContent.add(txtEventLocation);
        pnlContent.add(filler2);

        lblEventPriority.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventPriority.setText("Priority: *");
        pnlContent.add(lblEventPriority);

        cbEventPriority.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "low", "medium", "high" }));
        pnlContent.add(cbEventPriority);
        pnlContent.add(filler5);

        lblEventNotification.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventNotification.setText("Notification: *");
        pnlContent.add(lblEventNotification);

        cbEventNotification.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "none", "10 minutes", "1 hour", "3 days", "1 week" }));
        pnlContent.add(cbEventNotification);
        pnlContent.add(filler6);

        lblEventParticipants.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventParticipants.setText("Participants:");
        pnlContent.add(lblEventParticipants);

        pnlEventParticipants.setLayout(new java.awt.GridBagLayout());

        liEventParticipants.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
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

        btnAddParticipants.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-circle-line.png"))); // NOI18N
        btnAddParticipants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddParticipantsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        pnlEventParticipants.add(btnAddParticipants, gridBagConstraints);

        pnlContent.add(pnlEventParticipants);
        pnlContent.add(filler7);

        lblEventAttachments.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventAttachments.setText("Attachments:");
        pnlContent.add(lblEventAttachments);

        pnlEventAttachments.setLayout(new java.awt.GridBagLayout());

        btnAddAttachments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-circle-line.png"))); // NOI18N
        btnAddAttachments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAttachmentsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        pnlEventAttachments.add(btnAddAttachments, gridBagConstraints);

        liEventAttachments.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
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

        lblInformation.setText("* mandatory fields");
        pnlContent.add(lblInformation);

        add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setLayout(new java.awt.GridLayout(1, 0));
        add(pnlFooter, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddParticipantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddParticipantsActionPerformed
        // TODO add your handling code here:
        ArrayList<Operator> all = new ArrayList();
        if (liModelParticipants.getSize() > 0) {
            for (int i = 0; i < liModelParticipants.getSize(); i++) {
                Operator participant = liModelParticipants.getElementAt(i);
                all.add(participant);
            }
            FrmAddUserToAppointment frmAddUserToAppointment = new FrmAddUserToAppointment(FrmMain.getInstance(), true, all, this);
            frmAddUserToAppointment.setVisible(true);

        } else {
            FrmAddUserToAppointment frmAddUserToAppointment = new FrmAddUserToAppointment(FrmMain.getInstance(), true, this);
            frmAddUserToAppointment.setVisible(true);
        }
    }//GEN-LAST:event_btnAddParticipantsActionPerformed

    private void btnAddAttachmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAttachmentsActionPerformed
        JFileChooser openFileDialog = new JFileChooser();
        File file = null;

        openFileDialog.setDialogType(JFileChooser.SAVE_DIALOG);
        openFileDialog.setDialogTitle("Choose attachment");
        openFileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int state = openFileDialog.showSaveDialog(forms.FrmMain.getInstance());

        if (state == JFileChooser.APPROVE_OPTION) {
            file = openFileDialog.getSelectedFile();

            liModelAttachments.addElement(file);

            liEventAttachments.setCellRenderer(new EventUtilities.AttachmentListCellRenderer());
            liEventAttachments.setModel((ListModel) liModelAttachments);

        }
    }//GEN-LAST:event_btnAddAttachmentsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAttachments;
    private javax.swing.JButton btnAddParticipants;
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
    private javax.swing.JLabel lblInformation;
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
