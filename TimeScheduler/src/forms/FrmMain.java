/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import classes.Event;
import classes.Operator;
import handlers.DatabaseHandler;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

/**
 *
 * @author nilss
 */
public class FrmMain extends javax.swing.JFrame {

    private Operator user = null;
    
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
    }

    // <editor-fold defaultstate="collapsed" desc="Methods"> 
    
    public void setCurrentUser(Operator currentUser) {
        if(currentUser != null)
            this.user = currentUser;
    }
    
    public Operator getCurrentUser() {
        return this.user;
    }
    
    //FUNKTIONEN DIE VOM CALENDAR AUFGERUFEN WERDEN
    public void displayEventDetails(int eventID) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        frmEvent.setEvent(dbHandler.getEventById(eventID));
    }
    
    public void editEvent(int eventID) {
        
    }
    
    public void displayAllEventsOfDay(LocalDate today) {
        
    }
    
    public void createNewEvent(LocalDate date){
        
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
        jButton2 = new javax.swing.JButton();
        pnlHeadline = new javax.swing.JPanel();
        lblHeadline = new javax.swing.JLabel();
        splitPnlContent = new javax.swing.JSplitPane();
        pnlEventRoot = new javax.swing.JPanel();
        frmEvent = new forms.FrmEvent();
        pnlCalendarRoot = new javax.swing.JPanel();
        frmCalendar = new forms.FrmCalendar();
        pnlFooter = new javax.swing.JPanel();
        btnTest = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlHeader.setMinimumSize(new java.awt.Dimension(145, 88));
        pnlHeader.setPreferredSize(new java.awt.Dimension(736, 88));
        pnlHeader.setLayout(new java.awt.GridLayout(2, 1));

        pnlMenuBar.setPreferredSize(new java.awt.Dimension(736, 33));
        pnlMenuBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mnuAdminInterface.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user-settings-line.png"))); // NOI18N
        mnuAdminInterface.setText("Admin Interface");
        mnuAdminInterface.setToolTipText("");
        mnuAdminInterface.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdminInterfaceActionPerformed(evt);
            }
        });
        pnlMenuBar.add(mnuAdminInterface, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar-2-line.png"))); // NOI18N
        pnlMenuBar.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
        pnlEventRoot.add(frmEvent, java.awt.BorderLayout.CENTER);

        splitPnlContent.setLeftComponent(pnlEventRoot);

        pnlCalendarRoot.setLayout(new java.awt.BorderLayout());
        pnlCalendarRoot.add(frmCalendar, java.awt.BorderLayout.CENTER);

        splitPnlContent.setRightComponent(pnlCalendarRoot);

        getContentPane().add(splitPnlContent, java.awt.BorderLayout.CENTER);

        btnTest.setText("Test Button");
        btnTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFooterLayout = new javax.swing.GroupLayout(pnlFooter);
        pnlFooter.setLayout(pnlFooterLayout);
        pnlFooterLayout.setHorizontalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addContainerGap(739, Short.MAX_VALUE)
                .addComponent(btnTest)
                .addContainerGap())
        );
        pnlFooterLayout.setVerticalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTest, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(pnlFooter, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestActionPerformed

        this.frmCalendar.setLocalDate(java.time.LocalDate.of(2020, 12, 15));
        
        java.util.ArrayList<classes.Event> eventList = new ArrayList<>();
        
        classes.Event e1 = new classes.Event();
        classes.Event e2 = new classes.Event();
        classes.Event e3 = new classes.Event();
        classes.Event e4 = new classes.Event();
        classes.Event e5 = new classes.Event();
        classes.Event e6 = new classes.Event();
        classes.Event e7 = new classes.Event();
        classes.Event e8 = new classes.Event();
        
        e1.setDate(LocalDateTime.of(2020, Month.DECEMBER, 30, 8, 30));
        e2.setDate(LocalDateTime.of(2021, Month.JANUARY, 1, 8, 30));
        e3.setDate(LocalDateTime.of(2021, Month.JANUARY, 1, 8, 50));
        e4.setDate(LocalDateTime.of(2021, Month.JANUARY, 1, 13, 30));
        e5.setDate(LocalDateTime.of(2021, Month.JANUARY, 2, 17, 10));
        e6.setDate(LocalDateTime.of(2021, Month.JANUARY, 20, 13, 30));
        e7.setDate(LocalDateTime.of(2021, Month.JANUARY, 25, 10, 30));
        e8.setDate(LocalDateTime.of(2021, Month.JANUARY, 31, 8, 30));
        
        e1.setName("Business Meeting 0");
        e2.setName("Business Meeting 1");
        e3.setName("Business Meeting 2");
        e4.setName("Business Meeting 3");
        e5.setName("Business Meeting 4");
        e6.setName("Business Meeting 5");
        e7.setName("Business Meeting 6");
        e8.setName("Business Meeting 7");
        
        e1.setPriority(Event.Priority.LOW);
        e2.setPriority(Event.Priority.HIGH);
        e3.setPriority(Event.Priority.MEDIUM);
        e4.setPriority(Event.Priority.MEDIUM);
        e5.setPriority(Event.Priority.HIGH);
        e6.setPriority(Event.Priority.LOW);
        e7.setPriority(Event.Priority.HIGH);
        e8.setPriority(Event.Priority.LOW);
        
        eventList.add(e1);
        eventList.add(e2);
        eventList.add(e3);
        eventList.add(e4);
        eventList.add(e5);
        eventList.add(e6);
        eventList.add(e7);
        eventList.add(e8);
        
        this.frmCalendar.addEvents(eventList);
        
        displayEventDetails(1);
    }//GEN-LAST:event_btnTestActionPerformed

    private void mnuAdminInterfaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdminInterfaceActionPerformed
        FrmAdminInterface frmAdminIterface = new FrmAdminInterface(this, true);
        frmAdminIterface.setVisible(true);
    }//GEN-LAST:event_mnuAdminInterfaceActionPerformed

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
                new FrmMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTest;
    private forms.FrmCalendar frmCalendar;
    private forms.FrmEvent frmEvent;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel lblHeadline;
    private javax.swing.JButton mnuAdminInterface;
    private javax.swing.JPanel pnlCalendarRoot;
    private javax.swing.JPanel pnlEventRoot;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlHeadline;
    private javax.swing.JPanel pnlMenuBar;
    private javax.swing.JSplitPane splitPnlContent;
    // End of variables declaration//GEN-END:variables
}
