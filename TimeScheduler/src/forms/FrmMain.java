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

/**
 *
 * @author Nils Schmuck
 */
public class FrmMain extends javax.swing.JFrame {

    /**
     * The operator who is currently logged in.
     */
    private Operator user = null;
    private EventHandler eventHandler = null;
    private ReminderHandler reminderHandler = null;

    private static FrmMain form = null;

    /**
     *
     * @return
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
        this.setExtendedState(Frame.MAXIMIZED_BOTH); //full screen
        eventHandler = new EventHandler();
        this.datePicker.getComponentDateTextField().setEditable(false);
        addDatePickerDateChangedEvent();
    }

    /**
     *
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

    // <editor-fold defaultstate="collapsed" desc="Methods"> 
    /**
     *
     * @param currentUser
     */
    public void setConfigurations(Operator currentUser) {
        if (currentUser != null) {
            this.user = currentUser;

            if (this.user.getRole().equals(Operator.Role.USER)) {
                mnuAdminInterface.setVisible(false);
                mnuAdminInterface.setEnabled(false);
            }

            lblHeadline.setText("Welcome " + this.user.getFirstName() + " " + this.user.getLastName());

            this.frmCalendar.addEvents(eventHandler.getEventsOfPeriod(this.user.getUserId(), this.frmCalendar.getFirstDayOfView(), this.frmCalendar.getLastDayOfView()));

            datePicker.setDate(LocalDate.now());
            displayAllEventsOfDay(LocalDate.now());

            //ReminderHandler
            try {
                this.reminderHandler = new ReminderHandler(this.user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            reminderHandler.start();

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
     * Displays the specified text in this component. If the value of text is a
     * null or empty string, nothing is displayed.
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
        }
    }

    /**
     *
     * @param eventID
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

    }

    /**
     *
     * @param eventID
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

    }

    /**
     *
     * @param today
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
    }

    /**
     *
     * @param date
     */
    public void createNewEvent(LocalDate date) {
        pnlEventRoot.removeAll();
        pnlEventRoot.revalidate();
        pnlEventRoot.repaint();

        this.datePicker.setDate(this.frmCalendar.getSelectedLocalDate());

        FrmEvent frmEvent = new FrmEvent(FrmEvent.View.NEW, date);
        pnlEventRoot.add(frmEvent);
        frmEvent.setVisible(true);
    }

    /**
     *
     * @return
     */
    public FrmCalendar getCalendar() {
        return this.frmCalendar;
    }
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

        lblFeedback.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblFeedback.setText("Feedback");
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

    private void mnuAdminInterfaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdminInterfaceActionPerformed
        FrmAdminInterface frmAdminIterface = new FrmAdminInterface(this, true);
        frmAdminIterface.setVisible(true);
    }//GEN-LAST:event_mnuAdminInterfaceActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        handlers.ExportHandler expHandler = new handlers.ExportHandler();
        String path = expHandler.openSaveDialog();
        if (path != null) {
            expHandler.writeWeeklySchedule(this.eventHandler.getEventsOfWeek(this.user.getUserId()), LocalDate.now().with(DayOfWeek.MONDAY), LocalDate.now().with(DayOfWeek.SUNDAY), path);
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnNewEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewEventActionPerformed
        createNewEvent(this.frmCalendar.getSelectedLocalDate());
    }//GEN-LAST:event_btnNewEventActionPerformed

    private void btnNextMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextMonthActionPerformed
        LocalDate nextMonth = frmCalendar.getLocalDate().plusMonths(1);

        if (this.frmCalendar.getSelectedLocalDate().getMonthValue() != this.frmCalendar.getCurrentMonthValue()) {
            nextMonth = nextMonth.minusMonths(1);
        }

        if (nextMonth.getYear() == LocalDate.now().getYear() && nextMonth.getMonthValue() == LocalDate.now().getMonthValue()) {
            datePicker.setDate(LocalDate.now());
        } else {
            datePicker.setDate(nextMonth.withDayOfMonth(1));
        }

        this.frmCalendar.setLocalDate(datePicker.getDate());
        this.frmCalendar.addEvents(eventHandler.getEventsOfPeriod(this.user.getUserId(), this.frmCalendar.getFirstDayOfView(), this.frmCalendar.getLastDayOfView()));
    }//GEN-LAST:event_btnNextMonthActionPerformed

    private void btnPreviousMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousMonthActionPerformed
        LocalDate previousMonth = this.frmCalendar.getLocalDate().minusMonths(1);

        if (this.frmCalendar.getSelectedLocalDate().getMonthValue() != this.frmCalendar.getCurrentMonthValue()) {
            previousMonth = previousMonth.plusMonths(1);
        }

        if (previousMonth.getYear() == LocalDate.now().getYear() && previousMonth.getMonthValue() == LocalDate.now().getMonthValue()) {
            this.datePicker.setDate(LocalDate.now());
        } else {
            this.datePicker.setDate(previousMonth.withDayOfMonth(1));
        }

        this.frmCalendar.setLocalDate(datePicker.getDate());
        this.frmCalendar.addEvents(eventHandler.getEventsOfPeriod(this.user.getUserId(), this.frmCalendar.getFirstDayOfView(), this.frmCalendar.getLastDayOfView()));

    }//GEN-LAST:event_btnPreviousMonthActionPerformed

    /**
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
