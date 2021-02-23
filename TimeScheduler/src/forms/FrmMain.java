/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import classes.Event;
import classes.Operator;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import handlers.*;
import java.awt.Color;
import java.awt.Frame;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main window of the application.
 *
 * @author Nils Schmuck
 */
public class FrmMain extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Global Variables">
    /**
     * The operator who is currently logged in.
     */
    private Operator user = null;
    private EventHandler eventHandler = null;
    private ReminderHandler reminderHandler = null;
    /**
     * The current FrmMain
     */
    private static FrmMain form = null;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Gets the instance of this FrmMain. Create a new instance if the current
     * is null.
     *
     * @return The FrmMain
     */
    public static FrmMain getInstance() {
        if (form != null) {
            return form;
        } else {
            form = new FrmMain();
            return form;
        }
    }

    /**
     * Creates new form FrmMain
     */
    private FrmMain() {
        initComponents();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Entry point if the program.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Methods"> 
    /**
     * Sets the basic settings for the application.
     *
     * @param currentUser The operator that is logged in.
     */
    public void setConfigurations(Operator currentUser) {
        //Full screen
        this.setExtendedState(Frame.MAXIMIZED_BOTH);

        //Change date in this datePicker only via the controls.
        this.datePicker.getComponentDateTextField().setEditable(false);
        //set date in datePicker to today.
        datePicker.setDate(LocalDate.now());
        addDatePickerDateChangedEvent();
        //Set a new icon.
        setIcon();

        if (currentUser != null) {
            this.user = currentUser;

            eventHandler = new EventHandler();

            if (this.user.getRole().equals(Operator.Role.USER)) {
                mnuAdminInterface.setVisible(false);
                mnuAdminInterface.setEnabled(false);
            }

            lblHeadline.setText("Welcome " + this.user.getFirstName() + " " + this.user.getLastName());

            this.frmCalendar.addEvents(eventHandler.getEventsOfPeriod(this.user.getUserId(), this.frmCalendar.getFirstDayOfView(), this.frmCalendar.getLastDayOfView()));

            displayAllEventsOfDay(LocalDate.now());

            //ReminderHandler
            try {
                this.reminderHandler = new ReminderHandler(this.user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            reminderHandler.start();

            LoggerHandler.logger.info("Basic settings set successfully.");
        } else {

            LoggerHandler.logger.severe("Current user is NULL!");
        }
    }

    /**
     * Gets the {@link FrmMain#user} who is currently logged in.
     *
     * @return The current operator.
     */
    public Operator getCurrentUser() {
        return this.user;
    }

    /**
     * Overrides the {@link FrmMain#datePicker} change event. If the date in
     * this DatePicker changed, the {@link FrmMain#frmCalendar} focus the
     * selected date and if neccessary changes the view and reloads the events.
     */
    private void addDatePickerDateChangedEvent() {
        datePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dce) {

                LocalDate startPanelMonth = frmCalendar.getFirstDayOfView();
                LocalDate lastPanelMonth = frmCalendar.getLastDayOfView();

                if ((dce.getOldDate() != null) && (dce.getNewDate() != null) && ((dce.getNewDate().isBefore(startPanelMonth) || (dce.getNewDate().isAfter(lastPanelMonth))))) {
                    frmCalendar.setLocalDate(datePicker.getDate());
                    frmCalendar.addEvents(eventHandler.getEventsOfPeriod(user.getUserId(), frmCalendar.getFirstDayOfView(), frmCalendar.getLastDayOfView()));
                } else if ((dce.getOldDate() != null) && (dce.getNewDate() != null) && ((dce.getNewDate().isAfter(startPanelMonth) || (dce.getNewDate().isBefore(lastPanelMonth))))) {
                    frmCalendar.focusDayByLocalDate(datePicker.getDate());
                }
            }
        });
    }

    /**
     * Displays the specified text in this component for 5 seconds. If the value
     * of text is a null or empty string, nothing is displayed.
     *
     * @param text The text this component will display.
     * @param error true = if this text is an error message, false = if this
     * text is an success message.
     */
    public void setFeedback(String text, boolean error) {
        if (!text.isBlank()) {
            this.lblFeedback.setText("");

            if (error) {
                this.lblFeedback.setForeground(Color.RED);
            } else {
                this.lblFeedback.setForeground(Color.GREEN);
            }

            this.lblFeedback.setText(text);

            Timer timer = new Timer();
            timer.schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    lblFeedback.setText("");
                    timer.cancel();
                    timer.purge();
                }
            }, 5000);

        }
    }

    /**
     * Loads the {@link FrmEvent} with view "READ" in this
     * {@link FrmMain#pnlEventRoot} and displays the specified event. In the
     * previous step all controls from {@link FrmMain#pnlEventRoot} are removed.
     * Sets also the date of {@link FrmMain#datePicker} to the selected date in
     * {@link FrmMain#frmCalendar}.
     *
     * @param eventID The id of the event that is displayed.
     * @see FrmEvent.View
     */
    public void displayEventDetails(int eventID) {
        pnlEventRoot.removeAll();
        pnlEventRoot.revalidate();
        pnlEventRoot.repaint();

        this.datePicker.setDate(this.frmCalendar.getSelectedLocalDate());

        EventHandler eHandler = new EventHandler();
        Event event = eHandler.getEvent(eventID);
        FrmEvent frmEvent = new FrmEvent(FrmEvent.View.READ, event);

        pnlEventRoot.add(frmEvent);
        frmEvent.setVisible(true);

        LoggerHandler.logger.info("Display event with id " + eventID + "in view READ.");

    }

    /**
     * Loads the {@link FrmEvent} with view "EDIT" in this
     * {@link FrmMain#pnlEventRoot} and displays the specified event. In the
     * previous step all controls from {@link FrmMain#pnlEventRoot} are removed.
     * Sets also the date of {@link FrmMain#datePicker} to the selected date in
     * {@link FrmMain#frmCalendar}.
     *
     * @param eventID The id of the event that is displayed.
     * @see FrmEvent.View
     */
    public void editEvent(int eventID) {
        pnlEventRoot.removeAll();
        pnlEventRoot.revalidate();
        pnlEventRoot.repaint();

        this.datePicker.setDate(this.frmCalendar.getSelectedLocalDate());

        EventHandler eHandler = new EventHandler();
        Event event = eHandler.getEventByUser(this.user.getUserId(), eventID);

        FrmEvent frmEvent = new FrmEvent(FrmEvent.View.EDIT, event);
        pnlEventRoot.add(frmEvent);
        frmEvent.setVisible(true);
        this.revalidate();
        this.repaint();

        LoggerHandler.logger.info("Display event with id " + eventID + "in view EDIT.");
    }

    /**
     * Loads the {@link FrmEventsOfDay} in this {@link FrmMain#pnlEventRoot} and
     * lists all events on the specified date. In the previous step all controls
     * from {@link FrmMain#pnlEventRoot} are removed. Sets also the date of
     * {@link FrmMain#datePicker} to the selected date in
     * {@link FrmMain#frmCalendar}.
     *
     * @param today The date from which the events should be displayed.
     */
    public void displayAllEventsOfDay(LocalDate today) {
        pnlEventRoot.removeAll();
        pnlEventRoot.revalidate();
        pnlEventRoot.repaint();

        this.datePicker.setDate(this.frmCalendar.getSelectedLocalDate());

        EventHandler eHandler = new EventHandler();
        ArrayList<Event> events = eHandler.getEventsOfDay(user.getUserId(), today);

        FrmEventsOfDay frmEventsOfDay = new FrmEventsOfDay(events);
        pnlEventRoot.add(frmEventsOfDay);
        frmEventsOfDay.setVisible(true);

        LoggerHandler.logger.info("Display all events of day with date" + today + "");
    }

    /**
     * Loads the {@link FrmEvent} with view "NEW" in this
     * {@link FrmMain#pnlEventRoot}. In the previous step all controls from
     * {@link FrmMain#pnlEventRoot} are removed. Sets also the date of
     * {@link FrmMain#datePicker} to the selected date in
     * {@link FrmMain#frmCalendar}.
     *
     * @param date
     * @see FrmEvent.View
     */
    public void createNewEvent(LocalDate date) {
        pnlEventRoot.removeAll();
        pnlEventRoot.revalidate();
        pnlEventRoot.repaint();

        this.datePicker.setDate(this.frmCalendar.getSelectedLocalDate());

        FrmEvent frmEvent = new FrmEvent(FrmEvent.View.NEW, date);
        pnlEventRoot.add(frmEvent);
        frmEvent.setVisible(true);
        
        LoggerHandler.logger.info("Display create new event.");
    }

    /**
     * Gets the current {@link FrmMain#frmCalendar}.
     *
     * @return The current calendar.
     */
    public FrmCalendar getCalendar() {
        return this.frmCalendar;
    }

    /**
     * Sets the icon of this window.
     */
    private void setIcon() {
        setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/calendar-todo-fill.png")));
    }

    // </editor-fold>
//FUNKTIONEN DIE VOM CALENDAR AUFGERUFEN WERDEN
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
        pnlMenuBar = new javax.swing.JPanel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        mnuAdminInterface = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnExport = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        pnlHeadline = new javax.swing.JPanel();
        lblHeadline = new javax.swing.JLabel();
        splitPnlContent = new javax.swing.JSplitPane();
        pnlEventRoot = new javax.swing.JPanel();
        pnlCalendarRoot = new javax.swing.JPanel();
        frmCalendar = new forms.FrmCalendar();
        pnlCalendarControl = new javax.swing.JPanel();
        btnPreviousMonth = new javax.swing.JButton();
        datePicker = new com.github.lgooddatepicker.components.DatePicker();
        btnNextMonth = new javax.swing.JButton();
        pnlFooter = new javax.swing.JPanel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        lblFeedback = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnNewEvent = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TimeScheduler");
        setFocusCycleRoot(false);

        pnlHeader.setMinimumSize(new java.awt.Dimension(145, 88));
        pnlHeader.setPreferredSize(new java.awt.Dimension(736, 88));
        pnlHeader.setLayout(new java.awt.GridLayout(2, 1));

        pnlMenuBar.setPreferredSize(new java.awt.Dimension(736, 50));
        pnlMenuBar.setLayout(new javax.swing.BoxLayout(pnlMenuBar, javax.swing.BoxLayout.LINE_AXIS));
        pnlMenuBar.add(filler5);

        mnuAdminInterface.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user-settings-line.png"))); // NOI18N
        mnuAdminInterface.setText("Admin Interface");
        mnuAdminInterface.setFocusPainted(false);
        mnuAdminInterface.setFocusable(false);
        mnuAdminInterface.setPreferredSize(new java.awt.Dimension(145, 35));
        mnuAdminInterface.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdminInterfaceActionPerformed(evt);
            }
        });
        pnlMenuBar.add(mnuAdminInterface);
        pnlMenuBar.add(filler1);

        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/share-line.png"))); // NOI18N
        btnExport.setText("Export weekly schedule");
        btnExport.setFocusPainted(false);
        btnExport.setFocusable(false);
        btnExport.setPreferredSize(new java.awt.Dimension(190, 35));
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        pnlMenuBar.add(btnExport);
        pnlMenuBar.add(filler4);

        pnlHeader.add(pnlMenuBar);

        pnlHeadline.setMinimumSize(new java.awt.Dimension(100, 50));
        pnlHeadline.setPreferredSize(new java.awt.Dimension(736, 50));
        pnlHeadline.setLayout(new java.awt.BorderLayout());

        lblHeadline.setText("Welcome message!");
        lblHeadline.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        lblHeadline.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pnlHeadline.add(lblHeadline, java.awt.BorderLayout.CENTER);

        pnlHeader.add(pnlHeadline);

        getContentPane().add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlEventRoot.setMinimumSize(new java.awt.Dimension(300, 0));
        pnlEventRoot.setPreferredSize(new java.awt.Dimension(300, 264));
        pnlEventRoot.setLayout(new java.awt.BorderLayout());
        splitPnlContent.setLeftComponent(pnlEventRoot);

        pnlCalendarRoot.setLayout(new java.awt.BorderLayout());

        frmCalendar.setMinimumSize(new java.awt.Dimension(1000, 0));
        pnlCalendarRoot.add(frmCalendar, java.awt.BorderLayout.CENTER);

        pnlCalendarControl.setPreferredSize(new java.awt.Dimension(694, 50));

        btnPreviousMonth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/arrow-left-s-fill.png"))); // NOI18N
        btnPreviousMonth.setFocusPainted(false);
        btnPreviousMonth.setFocusable(false);
        btnPreviousMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousMonthActionPerformed(evt);
            }
        });
        pnlCalendarControl.add(btnPreviousMonth);

        datePicker.setPreferredSize(new java.awt.Dimension(143, 30));
        pnlCalendarControl.add(datePicker);

        btnNextMonth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/arrow-right-s-fill.png"))); // NOI18N
        btnNextMonth.setFocusPainted(false);
        btnNextMonth.setFocusable(false);
        btnNextMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextMonthActionPerformed(evt);
            }
        });
        pnlCalendarControl.add(btnNextMonth);

        pnlCalendarRoot.add(pnlCalendarControl, java.awt.BorderLayout.PAGE_START);

        splitPnlContent.setRightComponent(pnlCalendarRoot);

        getContentPane().add(splitPnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setPreferredSize(new java.awt.Dimension(999, 50));
        pnlFooter.setLayout(new javax.swing.BoxLayout(pnlFooter, javax.swing.BoxLayout.LINE_AXIS));
        pnlFooter.add(filler6);

        lblFeedback.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnlFooter.add(lblFeedback);
        pnlFooter.add(filler2);

        btnNewEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-circle-line.png"))); // NOI18N
        btnNewEvent.setText("Create new event");
        btnNewEvent.setFocusPainted(false);
        btnNewEvent.setFocusable(false);
        btnNewEvent.setPreferredSize(new java.awt.Dimension(150, 35));
        btnNewEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewEventActionPerformed(evt);
            }
        });
        pnlFooter.add(btnNewEvent);
        pnlFooter.add(filler3);

        getContentPane().add(pnlFooter, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Events">
    /**
     * Displays the admin interface.
     *
     * @param evt The action event.
     * @see FrmAdminInterface
     */
    private void mnuAdminInterfaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdminInterfaceActionPerformed
        FrmAdminInterface frmAdminIterface = new FrmAdminInterface(this, true);
        frmAdminIterface.setVisible(true);
    }//GEN-LAST:event_mnuAdminInterfaceActionPerformed

    /**
     * Triggers the weekly schedule process. Opens a save dialog in which the
     * operator can select a directory to save the report. The report is then
     * created and saved there.
     *
     * @param evt The action event.
     * @see ExportHandler
     */
    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        handlers.ExportHandler expHandler = new handlers.ExportHandler();
        String path = expHandler.openSaveDialog();
        if (path != null) {
            try {
                expHandler.writeWeeklySchedule(this.eventHandler.getEventsOfWeek(this.user.getUserId()), LocalDate.now().with(DayOfWeek.MONDAY), LocalDate.now().with(DayOfWeek.SUNDAY), path);
            } catch (Exception ex) {
                LoggerHandler.logger.severe("Path is not valid.");
            }
        }
    }//GEN-LAST:event_btnExportActionPerformed

    /**
     * Triggers the create new event method.
     *
     * @param evt The action event.
     * @see FrmMain#createNewEvent(java.time.LocalDate)
     */
    private void btnNewEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewEventActionPerformed
        createNewEvent(this.frmCalendar.getSelectedLocalDate());
    }//GEN-LAST:event_btnNewEventActionPerformed

    /**
     * Calculates the next month based on the month that is currently displayed
     * and displays the next month in this {@link FrmMain#frmCalendar}.
     *
     * @param evt The action event.
     */
    private void btnNextMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextMonthActionPerformed
        //Get next month: selected month plus one.
        LocalDate nextMonth = frmCalendar.getLocalDate().plusMonths(1);

        //If carry is selected we are in the next month, so subtract one month.
        if (this.frmCalendar.getSelectedLocalDate().getMonthValue() != this.frmCalendar.getCurrentMonthValue()) {
            nextMonth = nextMonth.minusMonths(1);
        }

        //If next month is the current month, set today in focus. 
        //Else set first day of month in focus.
        if (nextMonth.getYear() == LocalDate.now().getYear() && nextMonth.getMonthValue() == LocalDate.now().getMonthValue()) {
            datePicker.setDate(LocalDate.now());
        } else {
            datePicker.setDate(nextMonth.withDayOfMonth(1));
        }

        //Display new month based on datepicker and load the specified events
        this.frmCalendar.setLocalDate(datePicker.getDate());
        this.frmCalendar.addEvents(eventHandler.getEventsOfPeriod(this.user.getUserId(), this.frmCalendar.getFirstDayOfView(), this.frmCalendar.getLastDayOfView()));
    }//GEN-LAST:event_btnNextMonthActionPerformed

    /**
     * Calculates the previous month based on the month that is currently
     * displayed and displays the previous month in this
     * {@link FrmMain#frmCalendar}.
     *
     * @param evt The action event.
     */
    private void btnPreviousMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousMonthActionPerformed
        //Get previous month: selected month minus one.
        LocalDate previousMonth = this.frmCalendar.getLocalDate().minusMonths(1);

        //If carry is selected we are in the next month, so add one month.
        if (this.frmCalendar.getSelectedLocalDate().getMonthValue() != this.frmCalendar.getCurrentMonthValue()) {
            previousMonth = previousMonth.plusMonths(1);
        }

        //If next month is the current month, set today in focus. 
        //Else set first day of month in focus.
        if (previousMonth.getYear() == LocalDate.now().getYear() && previousMonth.getMonthValue() == LocalDate.now().getMonthValue()) {
            this.datePicker.setDate(LocalDate.now());
        } else {
            this.datePicker.setDate(previousMonth.withDayOfMonth(1));
        }

        //Display new month based on datepicker and load the specified events
        this.frmCalendar.setLocalDate(datePicker.getDate());
        this.frmCalendar.addEvents(eventHandler.getEventsOfPeriod(this.user.getUserId(), this.frmCalendar.getFirstDayOfView(), this.frmCalendar.getLastDayOfView()));

    }//GEN-LAST:event_btnPreviousMonthActionPerformed

    //</editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnNewEvent;
    private javax.swing.JButton btnNextMonth;
    private javax.swing.JButton btnPreviousMonth;
    private com.github.lgooddatepicker.components.DatePicker datePicker;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private forms.FrmCalendar frmCalendar;
    private javax.swing.JLabel lblFeedback;
    private javax.swing.JLabel lblHeadline;
    private javax.swing.JButton mnuAdminInterface;
    private javax.swing.JPanel pnlCalendarControl;
    private javax.swing.JPanel pnlCalendarRoot;
    private javax.swing.JPanel pnlEventRoot;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlHeadline;
    private javax.swing.JPanel pnlMenuBar;
    private javax.swing.JSplitPane splitPnlContent;
    // End of variables declaration//GEN-END:variables

}
