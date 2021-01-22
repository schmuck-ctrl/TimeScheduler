/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import classes.Attachment;
import classes.Event;
import classes.Operator;
import handlers.EventHandler;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Nils Schmuck
 */
public class FrmEvent extends javax.swing.JPanel {

    public enum View {
        READ,
        EDIT,
        NEW,
    }

    private DefaultListModel<Operator> modelParticipants = new DefaultListModel<>();
    private DefaultListModel<Attachment> modelAttachments = new DefaultListModel<>();
    private Event currentEvent = null;
    private JDialog isDialog = null;

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates new form FrmEvent
     */
    public FrmEvent(View view, Event event) {
        initComponents();

        pnlHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlContent1.setBorder(new EmptyBorder(10, 10, 10, 10));

        prepareLists();

        setEvent(event);
        handleView(view);
        
    }

    public FrmEvent(View view, LocalDate date) {
        initComponents();

        pnlHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlContent1.setBorder(new EmptyBorder(10, 10, 10, 10));

        prepareLists();

        clearInput(date);
        handleView(view);
    }

    public FrmEvent(View view, Event event, JDialog parent) {
        initComponents();

        this.isDialog = parent;

        pnlHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlContent1.setBorder(new EmptyBorder(10, 10, 10, 10));

        prepareLists();

        setEvent(event);
        handleView(view);
        
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    private void prepareLists() {
        this.liEventParticipants.setModel((ListModel) modelParticipants);
        this.liEventAttachments.setModel((ListModel) modelAttachments);
    }

    public void setTitle(String title) {
        if (!title.isBlank()) {
            this.lblHeadline.setText(title);
        }
    }

    public void setEvent(Event event) {
        if (event != null) {
            this.currentEvent = event;

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
                modelParticipants.addAll(event.getParticipants());
            }

            if (event.getAttachments() != null) {
                modelAttachments.addAll(event.getAttachments());
            }

        }
    }

    public void setParticipants(ArrayList<Operator> participants) {

        if (participants != null) {
            if (this.modelParticipants.getSize() > 0) {
                this.modelParticipants.clear();
            }

            this.modelParticipants.addAll(participants);
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
        ArrayList<Attachment> attachments = new ArrayList();
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

        for (int i = 0; i < modelParticipants.getSize(); i++) {
            Operator participant = modelParticipants.getElementAt(i);
            participants.add(participant);
        }

        for (int i = 0; i < modelAttachments.getSize(); i++) {
            Attachment file = modelAttachments.getElementAt(i);
            attachments.add(file);
        }

        Event newEvent = null;
        if (checkInput) {
            if (this.currentEvent.getID() == -1) {
                newEvent = new Event(name, host, date, duration, location, participants, attachments, priority, notification);
            } else {
                newEvent = new Event(this.currentEvent.getID(), name, host, date, duration, location, participants, attachments, priority, notification);
            }
        }

        return newEvent;
    }

    private void clearInput(LocalDate date) {
        Operator host = FrmMain.getInstance().getCurrentUser();//.getFirstName() + " " + FrmMain.getInstance().getCurrentUser().getLastName();
        this.txtEventHost.setText(host.getFirstName() + " " + host.getLastName());        
        
        this.dtPicker.datePicker.setDate(date);
        this.dtPicker.timePicker.setTimeToNow();

        this.txtEventName.setText("");
        this.txtEventDuration.setText("");
        this.txtEventLocation.setText("");

        this.cbEventPriority.setSelectedIndex(0);
        this.cbEventNotification.setSelectedIndex(0);

        this.modelAttachments.clear();
        this.modelParticipants.clear();
        this.modelParticipants.addElement(host);

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

            setTitle("Create new event: ");

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

            setTitle("Deatils of " + this.currentEvent.toString() + ":");

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

            setTitle("Edit event " + this.currentEvent.toString() + ":");

            if ((FrmMain.getInstance().getCurrentUser().getUserId() != this.currentEvent.getHost().getUserId())) {
                setEnabled(false);
                liEventAttachments.setEnabled(true);
                btnAddAttachments.setEnabled(true);

            } else {

                JButton btnDelete = new JButton("Delete", new javax.swing.ImageIcon(getClass().getResource("/icons/delete-bin-line.png")));

                btnDelete.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnDeleteActionPerformed(e);
                    }

                });

                pnlFooter.add(btnDelete);
            }
            JButton btnEdit = new JButton("Speichern", new javax.swing.ImageIcon(getClass().getResource("/icons/save-line.png")));

            btnEdit.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnEditActoinPerformed(e);
                }

            });

            pnlFooter.add(btnEdit);
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
            deleteEvent(this.currentEvent.getID());

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

        pnlHeader = new javax.swing.JPanel();
        lblHeadline = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        pnlContent1 = new javax.swing.JPanel();
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
        pnlContent2 = new javax.swing.JPanel();
        pnlEventParticipants = new javax.swing.JPanel();
        lblEventParticipants = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        liEventParticipants = new javax.swing.JList<>();
        btnAddParticipants = new javax.swing.JButton();
        pnlEventAttachments = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        liEventAttachments = new javax.swing.JList<>();
        btnAddAttachments = new javax.swing.JButton();
        lblEventAttachments = new javax.swing.JLabel();
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

        pnlContent.setLayout(new java.awt.BorderLayout());

        pnlContent1.setAlignmentX(0.0F);
        pnlContent1.setAlignmentY(0.0F);
        pnlContent1.setLayout(new java.awt.GridLayout(20, 1));

        lblEventName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventName.setText("Name: *");
        lblEventName.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblEventName.setToolTipText("");
        pnlContent1.add(lblEventName);

        txtEventName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlContent1.add(txtEventName);
        pnlContent1.add(filler3);

        lblDateTime.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDateTime.setText("Date: *");
        lblDateTime.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        pnlContent1.add(lblDateTime);
        pnlContent1.add(dtPicker);
        pnlContent1.add(filler4);

        lblEventHost.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventHost.setText("Host: *");
        lblEventHost.setToolTipText("");
        pnlContent1.add(lblEventHost);
        pnlContent1.add(txtEventHost);
        pnlContent1.add(filler9);

        lblEventDuration.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventDuration.setText("Duration (min): *");
        pnlContent1.add(lblEventDuration);
        pnlContent1.add(txtEventDuration);
        pnlContent1.add(filler1);

        lblEventLocation.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventLocation.setText("Location:");
        pnlContent1.add(lblEventLocation);
        pnlContent1.add(txtEventLocation);
        pnlContent1.add(filler2);

        lblEventPriority.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventPriority.setText("Priority: *");
        pnlContent1.add(lblEventPriority);

        cbEventPriority.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "low", "medium", "high" }));
        pnlContent1.add(cbEventPriority);
        pnlContent1.add(filler5);

        lblEventNotification.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventNotification.setText("Notification: *");
        pnlContent1.add(lblEventNotification);

        cbEventNotification.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "none", "10 minutes", "1 hour", "3 days", "1 week" }));
        pnlContent1.add(cbEventNotification);

        pnlContent.add(pnlContent1, java.awt.BorderLayout.CENTER);

        pnlContent2.setLayout(new java.awt.BorderLayout());

        lblEventParticipants.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventParticipants.setText("Participants:");

        liEventParticipants.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        liEventParticipants.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        liEventParticipants.setName(""); // NOI18N
        jScrollPane1.setViewportView(liEventParticipants);

        btnAddParticipants.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-circle-line.png"))); // NOI18N
        btnAddParticipants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddParticipantsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEventParticipantsLayout = new javax.swing.GroupLayout(pnlEventParticipants);
        pnlEventParticipants.setLayout(pnlEventParticipantsLayout);
        pnlEventParticipantsLayout.setHorizontalGroup(
            pnlEventParticipantsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEventParticipantsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEventParticipantsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEventParticipants, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlEventParticipantsLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddParticipants)))
                .addContainerGap())
        );
        pnlEventParticipantsLayout.setVerticalGroup(
            pnlEventParticipantsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEventParticipantsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEventParticipants)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEventParticipantsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEventParticipantsLayout.createSequentialGroup()
                        .addComponent(btnAddParticipants)
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlContent2.add(pnlEventParticipants, java.awt.BorderLayout.NORTH);

        liEventAttachments.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        liEventAttachments.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        liEventAttachments.setToolTipText("Select an attachment and press the ENTF key to remove the attachment.");
        liEventAttachments.setValueIsAdjusting(true);
        liEventAttachments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                liEventAttachmentsMouseClicked(evt);
            }
        });
        liEventAttachments.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                liEventAttachmentsKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(liEventAttachments);

        btnAddAttachments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-circle-line.png"))); // NOI18N
        btnAddAttachments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAttachmentsActionPerformed(evt);
            }
        });

        lblEventAttachments.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventAttachments.setText("Attachments:");

        javax.swing.GroupLayout pnlEventAttachmentsLayout = new javax.swing.GroupLayout(pnlEventAttachments);
        pnlEventAttachments.setLayout(pnlEventAttachmentsLayout);
        pnlEventAttachmentsLayout.setHorizontalGroup(
            pnlEventAttachmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEventAttachmentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEventAttachmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEventAttachments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlEventAttachmentsLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddAttachments)))
                .addContainerGap())
        );
        pnlEventAttachmentsLayout.setVerticalGroup(
            pnlEventAttachmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEventAttachmentsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEventAttachments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEventAttachmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddAttachments)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pnlContent2.add(pnlEventAttachments, java.awt.BorderLayout.CENTER);

        lblInformation.setText("* mandatory fields");
        pnlContent2.add(lblInformation, java.awt.BorderLayout.SOUTH);

        pnlContent.add(pnlContent2, java.awt.BorderLayout.SOUTH);

        add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setLayout(new java.awt.GridLayout(1, 0));
        add(pnlFooter, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddAttachmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAttachmentsActionPerformed
        JFileChooser openFileDialog = new JFileChooser();
        File file = null;

        openFileDialog.setDialogType(JFileChooser.SAVE_DIALOG);
        openFileDialog.setDialogTitle("Choose attachment");
        openFileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int state = openFileDialog.showSaveDialog(forms.FrmMain.getInstance());

        if (state == JFileChooser.APPROVE_OPTION) {
            file = openFileDialog.getSelectedFile();

            this.modelAttachments.addElement(new Attachment(file));

            this.liEventAttachments.revalidate();
            this.liEventAttachments.repaint();

            this.liEventAttachments.setModel((ListModel) modelAttachments);
        }
    }//GEN-LAST:event_btnAddAttachmentsActionPerformed

    private void liEventAttachmentsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_liEventAttachmentsKeyPressed
        System.out.println(evt.getKeyCode());
        if ((this.liEventAttachments.getSelectedIndex() >= 0) && (evt.getKeyCode() == 127)) { //127 == ENTF Key
            int retVal = JOptionPane.showConfirmDialog(FrmMain.getInstance(), "Are you sure you want to remove the attachment?", "Delete attachment", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (retVal == JOptionPane.YES_OPTION) {
                this.modelAttachments.remove(this.liEventAttachments.getSelectedIndex());
            }
        }
    }//GEN-LAST:event_liEventAttachmentsKeyPressed

    private void btnAddParticipantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddParticipantsActionPerformed
        ArrayList<Operator> all = new ArrayList();
        if (modelParticipants.getSize() > 0) {
            for (int i = 0; i < modelParticipants.getSize(); i++) {
                Operator participant = modelParticipants.getElementAt(i);
                all.add(participant);
            }
            FrmAddUserToAppointment frmAddUserToAppointment = new FrmAddUserToAppointment(FrmMain.getInstance(), true, all, this);
            frmAddUserToAppointment.setVisible(true);

        } else {
            FrmAddUserToAppointment frmAddUserToAppointment = new FrmAddUserToAppointment(FrmMain.getInstance(), true, this);
            frmAddUserToAppointment.setVisible(true);
        }
    }//GEN-LAST:event_btnAddParticipantsActionPerformed

    private void liEventAttachmentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_liEventAttachmentsMouseClicked
        if (evt.getClickCount() >= 2) {
            int selectedIndex = liEventAttachments.getSelectedIndex();

            Attachment file = modelAttachments.get(selectedIndex);

            JFileChooser saveDialog = new JFileChooser();
            String path = null;

            saveDialog.setDialogType(JFileChooser.SAVE_DIALOG);
            saveDialog.setDialogTitle("Save attachment");
            saveDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int state = saveDialog.showSaveDialog(forms.FrmMain.getInstance());

            if (state == JFileChooser.APPROVE_OPTION) {
                FileOutputStream fos = null;

                try {
                    path = saveDialog.getSelectedFile().toString() + "\\" + file.getFileName();

                    if (java.nio.file.Files.exists(java.nio.file.Paths.get(path), java.nio.file.LinkOption.NOFOLLOW_LINKS)) {
                        int retVal = JOptionPane.showConfirmDialog(FrmMain.getInstance(), "The file already exists. Do you want to overwrite the file?", "File already exists", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                        if (retVal == JOptionPane.YES_OPTION) {
                            fos = new FileOutputStream(path);
                            fos.write(file.getByteStream());
                        }
                    } else {
                        fos = new FileOutputStream(path);
                        fos.write(file.getByteStream());
                    }
                } catch (FileNotFoundException ex) {
                    handlers.LoggerHandler.logger.severe(ex.getMessage());
                } catch (IOException ex) {
                    handlers.LoggerHandler.logger.severe(ex.getMessage());
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException ex) {
                        handlers.LoggerHandler.logger.severe(ex.getMessage());
                    }
                }

            }

        }
    }//GEN-LAST:event_liEventAttachmentsMouseClicked

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
    private javax.swing.JPanel pnlContent1;
    private javax.swing.JPanel pnlContent2;
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
