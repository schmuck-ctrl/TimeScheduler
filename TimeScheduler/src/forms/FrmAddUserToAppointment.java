/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import classes.Operator;
import handlers.UserHandler;
import handlers.DatabaseHandler;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Vadym
 */
public class FrmAddUserToAppointment extends javax.swing.JDialog {

    private DatabaseHandler dbHandler;
    private DefaultTableModel tabSearchForUserModel = null;
    private DefaultTableModel tabAddedUserModel = null;
    private Operator selectedUser = null;
    private String userEmail = null;
    private FrmEvent frmEvent = null;

    /**
     * Creates new form FrmAddUserToAppointment
     */
    public FrmAddUserToAppointment(java.awt.Frame parent, boolean modal, FrmEvent source) {
        super(parent, modal);
        initComponents();
        this.frmEvent = source;
        tabSearchForUserModel = (DefaultTableModel) tabSearchUser.getModel();
        tabAddedUserModel = (DefaultTableModel) tabAddedUser.getModel();

        bindDataToTableSearchUsers(getUsers());

    }

    public FrmAddUserToAppointment(java.awt.Frame parent, boolean modal, ArrayList<Operator> users, FrmEvent source) {
        super(parent, modal);
        initComponents();
        this.frmEvent = source;
        tabSearchForUserModel = (DefaultTableModel) tabSearchUser.getModel();
        tabAddedUserModel = (DefaultTableModel) tabAddedUser.getModel();

        bindDataToTableSearchUsers(getUsers());
        bindExistingDataToTableAddedUser(users);

    }

    private void bindDataToTableSearchUsers(ArrayList<Operator> users) {
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {

                this.tabSearchForUserModel.addRow(new Object[]{
                    users.get(i).getFirstName().toString(),
                    users.get(i).getLastName().toString(),
                    users.get(i).getEmail().toString()
                });
            }
        }
    }

    private void bindDataToTableSearchUser(Operator user) {
        if (user != null) {

            this.tabSearchForUserModel.addRow(new Object[]{
                user.getFirstName().toString(),
                user.getLastName().toString(),
                user.getEmail().toString()
            });
        }
    }

    private void deleteDataFromRow() {

        for (int i = 0; i < this.tabAddedUserModel.getRowCount(); i++) {
            for (int a = 0; a < this.tabSearchForUserModel.getRowCount(); a++) {

                if (this.tabAddedUserModel.getValueAt(i, 2).toString().equalsIgnoreCase(this.tabSearchForUserModel.getValueAt(a, 2).toString())) { //change to Email !!!!!!

                    this.tabSearchForUserModel.removeRow(a);
                }
            }
        }
    }

    private void bindDataToTableAddedUser(Operator users) {
        this.tabAddedUserModel.addRow(new Object[]{
            users.getFirstName().toString(),
            users.getLastName().toString(),
            users.getEmail().toString()
        });
        deleteDataFromRow();
        this.repaint();
    }

    private void bindExistingDataToTableAddedUser(ArrayList<Operator> users) {
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {

                this.tabAddedUserModel.addRow(new Object[]{
                    users.get(i).getFirstName().toString(),
                    users.get(i).getLastName().toString(),
                    users.get(i).getEmail().toString()
                });

            }
            deleteDataFromRow();

            this.repaint();

        }
    }

    private ArrayList<Operator> getUsers() {
        UserHandler uHandler = new UserHandler();

        return uHandler.getAllUser();
    }

    private Operator getUserByEmail(String email) {
        UserHandler uHandler = new UserHandler();

        return uHandler.getUser(email);
    }

    private Operator getSelectedUserFromSearchUserTab(int rowIndex) {
        Operator selectedUser = null;
        UserHandler uHandler = new UserHandler();

        if (rowIndex >= 0) {
            String userEmail = (String) tabSearchForUserModel.getValueAt(rowIndex, 2);
            selectedUser = uHandler.getUser(userEmail);
        }
        return selectedUser;
    }

    private Operator getSelectedUserFromAddUserTable(int rowIndex) {
        Operator selectedUser = null;
        UserHandler uHandler = new UserHandler();

        if (rowIndex >= 0) {
            String userEmail = (String) tabAddedUserModel.getValueAt(rowIndex, 2);
            selectedUser = uHandler.getUser(userEmail);
        }
        return selectedUser;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabAddedUser = new javax.swing.JTable();
        btnAddToAppointment = new javax.swing.JButton();
        btnDeleteUserFromAppointment = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnAddUser = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabSearchUser = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(174, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tabAddedUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "First name", "Last name", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabAddedUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tabAddedUser);

        btnAddToAppointment.setText("Add to Appointment");
        btnAddToAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToAppointmentActionPerformed(evt);
            }
        });

        btnDeleteUserFromAppointment.setText("Delete Participants");
        btnDeleteUserFromAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserFromAppointmentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDeleteUserFromAppointment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddToAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(31, 31, 31))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAddToAppointment)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteUserFromAppointment))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        btnAddUser.setText("Add");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        tabSearchUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "First name", "Last name", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabSearchUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tabSearchUser);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAddUser)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(136, 136, 136))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tabSearchUser.getModel();
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(tabSearchForUserModel);
        tableRowSorter.setRowFilter(RowFilter.regexFilter(txtSearch.getText().trim()));
        tabSearchUser.setRowSorter(tableRowSorter);

        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_txtSearchKeyTyped


    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        int selectedRowIndex = tabSearchUser.getSelectedRow();
        this.selectedUser = getSelectedUserFromSearchUserTab(selectedRowIndex);
        if (this.selectedUser != null) {
            bindDataToTableAddedUser(this.selectedUser);
            this.revalidate();
            this.repaint();
        }
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnAddToAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToAppointmentActionPerformed
        // TODO add your handling code here:
        int count = tabAddedUser.getModel().getRowCount();

        if (count > 0) {
            ArrayList<Operator> participantsToAdd = new ArrayList();

            for (int i = 0; i < tabAddedUser.getModel().getRowCount(); i++) {
                int b = tabAddedUser.getModel().getRowCount();

                String userEmail = (String) tabAddedUserModel.getValueAt(i, 2);
                Operator user = getUserByEmail(userEmail);
                participantsToAdd.add(user);
            }
            frmEvent.setParticipants(participantsToAdd);

        } else {

        }
        this.setVisible(false);
        frmEvent.repaint();
    }//GEN-LAST:event_btnAddToAppointmentActionPerformed

    private void btnDeleteUserFromAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserFromAppointmentActionPerformed
        // TODO add your handling code here:
        int selectedRowIndex = tabAddedUser.getSelectedRow();
        this.selectedUser = getSelectedUserFromAddUserTable(selectedRowIndex);
        bindDataToTableSearchUser(this.selectedUser);
        tabAddedUserModel.removeRow(selectedRowIndex);
        this.repaint();
    }//GEN-LAST:event_btnDeleteUserFromAppointmentActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToAppointment;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnDeleteUserFromAppointment;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabAddedUser;
    private javax.swing.JTable tabSearchUser;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
