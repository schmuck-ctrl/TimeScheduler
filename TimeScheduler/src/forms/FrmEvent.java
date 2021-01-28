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
 * Provides methods to manage an event.
 *
 * @author Nils Schmuck
 */
public class FrmEvent extends javax.swing.JPanel {

    // <editor-fold defaultstate="collapsed" desc="Enums">
    /**
     * Views that can be used for the {@link FrmEvent}
     * @see View#READ
     * @see View#EDIT
     * @see View#NEW
     */
    public enum View {
        /**
         * The user can read only.
         */
        READ,
        /**
         * The user is able to edit the event.
         */
        EDIT,
        /**
         * Create a new event.
         */
        NEW
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Global Variables">
    /**
     * Model of {@link FrmEvent#liEventParticipants}.
     */
    private DefaultListModel<Operator> modelParticipants = new DefaultListModel<>();
    /**
     * Model of {@link FrmEvent#liEventAttachments}.
     */
    private DefaultListModel<Attachment> modelAttachments = new DefaultListModel<>();
    /**
     * The event which is displayed.
     */
    private Event currentEvent = new Event();
    /**
     * The dialog in which the event is displayed, if the event is shown in an
     * external window.
     */
    private JDialog isDialog = null;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructs a new form FrmEvent with the specified view and event. This
     * constructor is used to display an event that already exists.
     *
     * @param view The view that is displayed.
     * @param event The event to display.
     */
    public FrmEvent(View view, Event event) {
        initComponents();

        pnlHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlNorth.setBorder(new EmptyBorder(10, 10, 10, 10));

        prepareLists();

        setEvent(event);
        handleView(view);

    }

    /**
     * Constructs a new form FrmEvent. This constructor is used to create a new
     * event.
     *
     * @param view The view that is displayed.
     * @param date The date of the event.
     */
    public FrmEvent(View view, LocalDate date) {
        initComponents();

        pnlHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlNorth.setBorder(new EmptyBorder(10, 10, 10, 10));

        prepareLists();

        clearInput(date);
        handleView(view);
    }

    /**
     * Constructs a new form FrmEvent with the specified view and event. This
     * constructor is used to display an event that already exists in an
     * external JDialog.
     *
     * @param view The view that is displayed.
     * @param event The event to display.
     * @param parent The dialog in which it should be displayed.
     */
    public FrmEvent(View view, Event event, JDialog parent) {
        initComponents();

        this.isDialog = parent;

        pnlHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlNorth.setBorder(new EmptyBorder(10, 10, 10, 10));

        prepareLists();

        setEvent(event);
        handleView(view);

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Adds the specified model to the list.
     */
    private void prepareLists() {
        this.liEventParticipants.setModel((ListModel) modelParticipants);
        this.liEventAttachments.setModel((ListModel) modelAttachments);
    }

    /**
     * Sets the title of this form.
     *
     * @param title The string that is displayed.
     */
    public void setTitle(String title) {
        if (!title.isBlank()) {
            this.lblHeadline.setText(title);
        }
    }

    /**
     * Sets the information of the event into the specified controls, if the
     * event is not null.
     *
     * @param event The event to display
     */
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

    /**
     * Adds the specified collection of operators to this model for event
     * participants.
     *
     * @param participants The collection to be added.
     */
    public void addParticipants(ArrayList<Operator> participants) {

        if (participants != null) {
            if (this.modelParticipants.getSize() > 0) {
                this.modelParticipants.clear();
            }

            this.modelParticipants.addAll(participants);
        }

    }

    /**
     * Create a new event or change this event with the input of the controls
     * and validate it.
     *
     * @return An event with the specified input if validation was successful.
     * Otherwise return null.
     */
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
            txtEventLocation.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        } else {
            txtEventLocation.setBorder(BorderFactory.createLineBorder(Color.RED));
            checkInput = false;
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

    /**
     * Resets the input of the controls and sets the specified date as default.
     *
     * @param date The date that is set.
     */
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

    /**
     * Delete all controls from this {@link FrmEvent#pnlFooter}
     */
    private void clearFooter() {
        this.pnlFooter.removeAll();
        this.pnlFooter.revalidate();
        this.pnlFooter.repaint();
    }

    /**
     * Sets all controls on this to enabled or disabled and editable or not
     * editable depending on the paramter.
     *
     * @param isEnabled true = enabled, false = disabled.
     */
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

    /**
     * Manages the display of the various views.
     *
     * @param view The view that is displayed.
     */
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

            //Display the edit button only if the event is not in the past.
            if (this.currentEvent.getDate().isAfter(LocalDateTime.now())) {

                JButton btnDisplayEditView = new JButton("Edit", new javax.swing.ImageIcon(getClass().getResource("/icons/pencil-line.png")));

                btnDisplayEditView.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnDisplayEditViewActoinPerformed(e);
                    }
                });

                pnlFooter.add(btnDisplayEditView);
            }
        } else if (view == View.EDIT) {

            //If the event is in the past, display the View.Read settings.
            //Otherwise display a delete and save button.
            if (this.currentEvent.getDate().isBefore(LocalDateTime.now())) {
                handleView(View.READ);
            } else {

                enableControls(true);

                setTitle("Edit event " + this.currentEvent.toString() + ":");

                if ((FrmMain.getInstance().getCurrentUser().getUserId() != this.currentEvent.getHost().getUserId())) {
                    enableControls(false);
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

                JButton btnEdit = new JButton("Save", new javax.swing.ImageIcon(getClass().getResource("/icons/save-line.png")));

                btnEdit.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnEditActoinPerformed(e);
                    }

                });

                pnlFooter.add(btnEdit);
            }
        }
    }

    /**
     * Creates a new event.
     *
     * @param newEvent The new event.
     */
    private void newEvent(Event newEvent) {
        if (newEvent != null) {
            EventHandler eHandler = new EventHandler();
            eHandler.addEvent(newEvent);
        }
    }

    /**
     * Edit the event with the specified changes.
     *
     * @param event The event with the changes.
     */
    private void editEvent(Event event) {
        if (event != null) {
            EventHandler eHandler = new EventHandler();
            eHandler.editEvent(event);
        }
    }

    /**
     * Delete the specified event.
     *
     * @param eventID The id of the event which should be deleted.
     */
    private void deleteEvent(int eventID) {
        if (eventID >= 0) {
            EventHandler eHandler = new EventHandler();
            eHandler.deleteEvent(eventID);
        }
    }

    /**
     * Shows this calendar with the specified date and loads the events.
     *
     * @param date The date on which the calendar is changed.
     */
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
        pnlNorth = new javax.swing.JPanel();
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
        pnlSouth = new javax.swing.JPanel();
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

        pnlNorth.setAlignmentX(0.0F);
        pnlNorth.setAlignmentY(0.0F);
        pnlNorth.setLayout(new java.awt.GridLayout(20, 1));

        lblEventName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventName.setText("Name: *");
        lblEventName.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        pnlNorth.add(lblEventName);

        txtEventName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlNorth.add(txtEventName);
        pnlNorth.add(filler3);

        lblDateTime.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDateTime.setText("Date: *");
        lblDateTime.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        pnlNorth.add(lblDateTime);
        pnlNorth.add(dtPicker);
        pnlNorth.add(filler4);

        lblEventHost.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventHost.setText("Host: *");
        pnlNorth.add(lblEventHost);
        pnlNorth.add(txtEventHost);
        pnlNorth.add(filler9);

        lblEventDuration.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventDuration.setText("Duration (min): *");
        pnlNorth.add(lblEventDuration);
        pnlNorth.add(txtEventDuration);
        pnlNorth.add(filler1);

        lblEventLocation.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventLocation.setText("Location: *");
        pnlNorth.add(lblEventLocation);
        pnlNorth.add(txtEventLocation);
        pnlNorth.add(filler2);

        lblEventPriority.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventPriority.setText("Priority: *");
        pnlNorth.add(lblEventPriority);

        cbEventPriority.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "low", "medium", "high" }));
        pnlNorth.add(cbEventPriority);
        pnlNorth.add(filler5);

        lblEventNotification.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventNotification.setText("Notification: *");
        pnlNorth.add(lblEventNotification);

        cbEventNotification.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "none", "10 minutes", "1 hour", "3 days", "1 week" }));
        pnlNorth.add(cbEventNotification);

        pnlContent.add(pnlNorth, java.awt.BorderLayout.CENTER);

        pnlSouth.setLayout(new java.awt.BorderLayout());

        lblEventParticipants.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEventParticipants.setText("Participants:");

        liEventParticipants.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        liEventParticipants.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        liEventParticipants.setName(""); // NOI18N
        jScrollPane1.setViewportView(liEventParticipants);

        btnAddParticipants.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user-add-line.png"))); // NOI18N
        btnAddParticipants.setFocusPainted(false);
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

        pnlSouth.add(pnlEventParticipants, java.awt.BorderLayout.NORTH);

        liEventAttachments.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        liEventAttachments.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        liEventAttachments.setToolTipText("Select an attachment and press the ENTF key to remove the attachment.");
        liEventAttachments.setValueIsAdjusting(true);
        liEventAttachments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                liEventAttachmentsMousePressed(evt);
            }
        });
        liEventAttachments.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                liEventAttachmentsKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(liEventAttachments);

        btnAddAttachments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/file-add-line.png"))); // NOI18N
        btnAddAttachments.setFocusPainted(false);
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

        pnlSouth.add(pnlEventAttachments, java.awt.BorderLayout.CENTER);

        lblInformation.setText("* mandatory fields");
        pnlSouth.add(lblInformation, java.awt.BorderLayout.SOUTH);

        pnlContent.add(pnlSouth, java.awt.BorderLayout.SOUTH);

        add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setLayout(new java.awt.GridLayout(1, 0));
        add(pnlFooter, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Events">
    /**
     * Changes this view to View.EDIT.
     *
     * @param e The action event.
     */
    private void btnDisplayEditViewActoinPerformed(ActionEvent e) {
        handleView(View.EDIT);
    }

    /**
     * Triggers this newEvent() method. Checks if the date of the event is in
     * the past and if the input is correct. If not, newEvent() will not be
     * triggered.
     *
     * @param e The action event.
     */
    private void btnNewActionPerformed(ActionEvent e) {
        Event event = getInput();
        if (event != null) {

            LocalDateTime dateTime = LocalDateTime.of(dtPicker.datePicker.getDate(), dtPicker.timePicker.getTime());

            if (dateTime.isBefore(LocalDateTime.now())) {

                int retVal = JOptionPane.showConfirmDialog(FrmMain.getInstance(), "The Event is in the past. Do you want to continue?", "Appointment is in past", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (retVal == JOptionPane.YES_OPTION) {

                    newEvent(event);

                    refreshCalendar(dtPicker.datePicker.getDate());

                    this.setVisible(false);

                    if (this.isDialog != null) {
                        this.isDialog.dispose();
                    }
                }
            } else {

                newEvent(event);

                refreshCalendar(dtPicker.datePicker.getDate());

                this.setVisible(false);

                if (this.isDialog != null) {
                    this.isDialog.dispose();
                }
            }

        } else {
            JOptionPane.showMessageDialog(FrmMain.getInstance(), "Please check your input.", "Invalid input", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Triggers this editEvent() method. Checks if the date of the event is in
     * the past and if the input is correct. If not, newEvent() will not be
     * triggered.
     *
     * @param e The action event.
     */
    private void btnEditActoinPerformed(ActionEvent e) {
        Event event = getInput();
        if (event != null) {

            LocalDateTime dateTime = LocalDateTime.of(dtPicker.datePicker.getDate(), dtPicker.timePicker.getTime());

            if (dateTime.isBefore(LocalDateTime.now())) {

                int retVal = JOptionPane.showConfirmDialog(FrmMain.getInstance(), "The Event is in the past. Do you want to continue?", "Appointment is in past", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (retVal == JOptionPane.YES_OPTION) {

                    editEvent(event);

                    refreshCalendar(dtPicker.datePicker.getDate());

                    this.setVisible(false);

                    if (this.isDialog != null) {
                        this.isDialog.dispose();
                    }
                }
            } else {

                editEvent(event);

                refreshCalendar(dtPicker.datePicker.getDate());

                this.setVisible(false);

                if (this.isDialog != null) {
                    this.isDialog.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(FrmMain.getInstance(), "Please check your input.", "Invalid input", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Triggers this deleteEvent() method.
     *
     * @param e The action event.
     */
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

    /**
     * Opens a dialog to choose a file, which is attached to an event.
     *
     * @param evt Tha action event.
     */
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

    /**
     * Deletes an attachment from this event. Triggers when the user press the
     * ENTF (KeyCode: 127) key.
     *
     * @param evt The key event.
     */
    private void liEventAttachmentsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_liEventAttachmentsKeyPressed
        System.out.println(evt.getKeyCode());
        if ((this.liEventAttachments.getSelectedIndex() >= 0) && (evt.getKeyCode() == 127)) { //127 == ENTF Key
            int retVal = JOptionPane.showConfirmDialog(FrmMain.getInstance(), "Are you sure you want to remove the attachment?", "Delete attachment", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (retVal == JOptionPane.YES_OPTION) {
                this.modelAttachments.remove(this.liEventAttachments.getSelectedIndex());
            }
        }
    }//GEN-LAST:event_liEventAttachmentsKeyPressed

    /**
     * Opens a dialog to manage the partisipants of this event. This event host
     * is set as default value and can't be deleted.
     *
     * @param evt The action event.
     */
    private void btnAddParticipantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddParticipantsActionPerformed
        ArrayList<Operator> all = new ArrayList();
        if (modelParticipants.getSize() > 0) {
            for (int i = 0; i < modelParticipants.getSize(); i++) {
                Operator participant = modelParticipants.getElementAt(i);
                all.add(participant);
            }
            Operator host;
            if (this.currentEvent.getHost() == null) {
                host = FrmMain.getInstance().getCurrentUser();
            } else {
                host = this.currentEvent.getHost();
            }
            FrmAddUserToAppointment frmAddUserToAppointment = new FrmAddUserToAppointment(FrmMain.getInstance(), true, all, this, host);
            frmAddUserToAppointment.setLocationRelativeTo(null);
            frmAddUserToAppointment.setVisible(true);

        }
    }//GEN-LAST:event_btnAddParticipantsActionPerformed

    /**
     * Downloads the selected attachment to the user's computer. Opens a dialog
     * to select a directory where the attachmend is saved. Triggers if the
     * number of clicks is greater or equal 2.
     *
     * @param evt The mouse event.
     */
    private void liEventAttachmentsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_liEventAttachmentsMousePressed
        if (liEventAttachments.isEnabled() && evt.getClickCount() >= 2) {
            int selectedIndex = liEventAttachments.getSelectedIndex();

            if (selectedIndex > -1) {

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
        }
    }//GEN-LAST:event_liEventAttachmentsMousePressed

    // </editor-fold>

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
    private javax.swing.JPanel pnlEventAttachments;
    private javax.swing.JPanel pnlEventParticipants;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlNorth;
    private javax.swing.JPanel pnlSouth;
    private javax.swing.JTextField txtEventDuration;
    private javax.swing.JTextField txtEventHost;
    private javax.swing.JTextField txtEventLocation;
    private javax.swing.JTextField txtEventName;
    // End of variables declaration//GEN-END:variables
}
