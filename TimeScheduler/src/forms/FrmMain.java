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
import java.awt.Frame;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author nilss
 */
public class FrmMain extends javax.swing.JFrame {

    private Operator user = null;
    private EventHandler eventHandler = null;
    private ReminderHandler reminderHandler = null;

    private static FrmMain form = null;

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
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        eventHandler = new EventHandler();
        this.datePicker.getComponentDateTextField().setEditable(false);
        addDatePickerDateChangedEvent();
    }

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
    public void setCurrentUser(Operator currentUser) {
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

    public Operator getCurrentUser() {
        return this.user;
    }

    //FUNKTIONEN DIE VOM CALENDAR AUFGERUFEN WERDEN
    public void displayEventDetails(int eventID) {
        pnlEventRoot.removeAll();
        pnlEventRoot.revalidate();
        pnlEventRoot.repaint();

        this.datePicker.setDate(this.frmCalendar.getSelectedLocalDate());

        EventHandler eHandler = new EventHandler();
        Event event = eHandler.getEvent(eventID);
        FrmEvent frmEvent = new FrmEvent(FrmEvent.View.READ, event);

        pnlEventRoot.add(frmEvent);
        frmEvent.setTitle("Details of " + event.toString() + ":");
        frmEvent.setVisible(true);

    }

    public void editEvent(int eventID) {
        pnlEventRoot.removeAll();
        pnlEventRoot.revalidate();
        pnlEventRoot.repaint();

        this.datePicker.setDate(this.frmCalendar.getSelectedLocalDate());

        EventHandler eHandler = new EventHandler();
        Event event = eHandler.getEventByUser(this.user.getUserId(), eventID);

        FrmEvent frmEvent = new FrmEvent(FrmEvent.View.EDIT, event);
        pnlEventRoot.add(frmEvent);
        frmEvent.setTitle("Edit event " + event.toString() + ":");
        frmEvent.setVisible(true);

    }

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

    public void createNewEvent(LocalDate date) {
        pnlEventRoot.removeAll();
        pnlEventRoot.revalidate();
        pnlEventRoot.repaint();

        this.datePicker.setDate(this.frmCalendar.getSelectedLocalDate());

        FrmEvent frmEvent = new FrmEvent(FrmEvent.View.NEW, date);
        pnlEventRoot.add(frmEvent);
        frmEvent.setTitle("Create new Event: ");
        frmEvent.setVisible(true);
    }

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
        mnuAdminInterface = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
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
        btnNewEvent = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusCycleRoot(false);

        pnlHeader.setMinimumSize(new java.awt.Dimension(145, 88));
        pnlHeader.setPreferredSize(new java.awt.Dimension(736, 88));
        pnlHeader.setLayout(new java.awt.GridLayout(2, 1));

        pnlMenuBar.setPreferredSize(new java.awt.Dimension(736, 45));

        mnuAdminInterface.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user-settings-line.png"))); // NOI18N
        mnuAdminInterface.setText("Admin Interface");
        mnuAdminInterface.setPreferredSize(new java.awt.Dimension(145, 35));
        mnuAdminInterface.setToolTipText("");
        mnuAdminInterface.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdminInterfaceActionPerformed(evt);
            }
        });

        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/share-line.png"))); // NOI18N
        btnExport.setText("Export to PDF");
        btnExport.setPreferredSize(new java.awt.Dimension(130, 35));
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMenuBarLayout = new javax.swing.GroupLayout(pnlMenuBar);
        pnlMenuBar.setLayout(pnlMenuBarLayout);
        pnlMenuBarLayout.setHorizontalGroup(
            pnlMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mnuAdminInterface, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlMenuBarLayout.setVerticalGroup(
            pnlMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mnuAdminInterface, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        pnlHeader.add(pnlMenuBar);

        pnlHeadline.setMinimumSize(new java.awt.Dimension(100, 50));
        pnlHeadline.setPreferredSize(new java.awt.Dimension(736, 50));
        pnlHeadline.setLayout(new java.awt.BorderLayout());

        lblHeadline.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblHeadline.setText("Welcome message!");
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
        btnPreviousMonth.setToolTipText("");
        btnPreviousMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousMonthActionPerformed(evt);
            }
        });
        pnlCalendarControl.add(btnPreviousMonth);

        datePicker.setPreferredSize(new java.awt.Dimension(143, 30));
        pnlCalendarControl.add(datePicker);

        btnNextMonth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/arrow-right-s-fill.png"))); // NOI18N
        btnNextMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextMonthActionPerformed(evt);
            }
        });
        pnlCalendarControl.add(btnNextMonth);

        pnlCalendarRoot.add(pnlCalendarControl, java.awt.BorderLayout.PAGE_START);

        splitPnlContent.setRightComponent(pnlCalendarRoot);

        getContentPane().add(splitPnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setPreferredSize(new java.awt.Dimension(999, 45));

        btnNewEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-circle-line.png"))); // NOI18N
        btnNewEvent.setText("Create new event");
        btnNewEvent.setPreferredSize(new java.awt.Dimension(150, 35));
        btnNewEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewEventActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFooterLayout = new javax.swing.GroupLayout(pnlFooter);
        pnlFooter.setLayout(pnlFooterLayout);
        pnlFooterLayout.setHorizontalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addContainerGap(1149, Short.MAX_VALUE)
                .addComponent(btnNewEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlFooterLayout.setVerticalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNewEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

        if (nextMonth.getYear() == LocalDate.now().getYear() && nextMonth.getMonthValue() == LocalDate.now().getMonthValue()) {
            datePicker.setDate(LocalDate.now());
        } else {
            datePicker.setDate(nextMonth.withDayOfMonth(1));
        }

        this.frmCalendar.setLocalDate(datePicker.getDate());
        this.frmCalendar.addEvents(eventHandler.getEventsOfPeriod(this.user.getUserId(), this.frmCalendar.getFirstDayOfView(), this.frmCalendar.getLastDayOfView()));
    }//GEN-LAST:event_btnNextMonthActionPerformed

    private void btnPreviousMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousMonthActionPerformed
        LocalDate previousMonth = frmCalendar.getLocalDate().minusMonths(1);

        if (previousMonth.getYear() == LocalDate.now().getYear() && previousMonth.getMonthValue() == LocalDate.now().getMonthValue()) {
            datePicker.setDate(LocalDate.now());
        } else {
            datePicker.setDate(previousMonth.withDayOfMonth(1));
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
    private forms.FrmCalendar frmCalendar;
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
