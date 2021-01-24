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
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableColumn;

/**
 *
 * @author Vadym
 */
public class FrmAddUserToAppointment extends javax.swing.JDialog {

    
    private DefaultTableModel tabSearchForUserModel = null;
    private DefaultTableModel tabAddedUserModel = null;
    private DatabaseHandler dbHandler = null;
    private Operator selectedUser = null;
    private Operator eventHost = null;
    private String userEmail = null;
    private FrmEvent frmEvent = null;

    /**
     * Creates new form FrmAddUserToAppointment
     */


    public FrmAddUserToAppointment(java.awt.Frame parent, boolean modal, ArrayList<Operator> users, FrmEvent source,Operator host) {
        super(parent, modal);
        initComponents();
        eventHost = host;
        dbHandler = new DatabaseHandler();
        this.frmEvent = source;
        tabSearchForUserModel = (DefaultTableModel) tabSearchUser.getModel();
        tabAddedUserModel = (DefaultTableModel) tabAddedUser.getModel();

        bindDataToTableSearchUsers(getUsers());
        bindExistingDataToTableAddedUser(users);

        TableColumn col = tabSearchUser.getColumn("UserID");
        col.setMaxWidth(0);
        col.setMinWidth(0);
        col.setPreferredWidth(0);

        TableColumn colAddedUser = tabAddedUser.getColumn("UserID");
        colAddedUser.setMaxWidth(0);
        colAddedUser.setMinWidth(0);
        colAddedUser.setPreferredWidth(0);

    }

    private void bindDataToTableSearchUsers(ArrayList<Operator> users) {
        if (users != null) {

            for (int i = 0; i < users.size(); i++) {

                this.tabSearchForUserModel.addRow(new Object[]{
                    users.get(i).getFirstName().toString(),
                    users.get(i).getLastName().toString(),
                    users.get(i).getEmail().toString(),
                    users.get(i).getUserId()
                });

            }

        }
    }

    private void bindDataToTableSearchUser(Operator user) {
        if (user != null) {

            this.tabSearchForUserModel.addRow(new Object[]{
                user.getFirstName().toString(),
                user.getLastName().toString(),
                user.getEmail().toString(),
                user.getUserId()
            });
        }
    }

    private void deleteDataFromRow() {

        for (int i = 0; i < this.tabAddedUserModel.getRowCount(); i++) {
            for (int a = 0; a < this.tabSearchForUserModel.getRowCount(); a++) {
                if ((Integer) this.tabAddedUserModel.getValueAt(i, 3) == (Integer) this.tabSearchForUserModel.getValueAt(a, 3)) {
                    this.tabSearchForUserModel.removeRow(a);
                }
            }
        }
    }

    private void bindDataToTableAddedUser(Operator users) {
        this.tabAddedUserModel.addRow(new Object[]{
            users.getFirstName().toString(),
            users.getLastName().toString(),
            users.getEmail().toString(),
            users.getUserId()
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
                    users.get(i).getEmail().toString(),
                    users.get(i).getUserId()
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

    private Operator getUserByUserID(int userID) {
        UserHandler uHandler = new UserHandler();

        return uHandler.getUser(userID);
    }

    private Operator getSelectedUserFromSearchUserTab(int rowIndex) {
        Operator selectedUser = null;
        UserHandler uHandler = new UserHandler();

        if (rowIndex >= 0) {
            int userID = (Integer) tabSearchUser.getValueAt(rowIndex, 3);
            selectedUser = uHandler.getUser(userID);
        }
        return selectedUser;
    }

    private Operator getSelectedUserFromAddUserTable(int rowIndex) {
        Operator selectedUser = null;
        UserHandler uHandler = new UserHandler();

        if (rowIndex >= 0) {

            int userID = (Integer) tabAddedUserModel.getValueAt(rowIndex, 3);
            selectedUser = uHandler.getUser(userID);

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
        lblAddUserToAppointment = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabAddedUser = new javax.swing.JTable();
        btnAddToAppointment = new javax.swing.JButton();
        btnDeleteUserFromAppointment = new javax.swing.JButton();
        txtErrorWarning = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabSearchUser = new javax.swing.JTable();
        txtErrorAdd = new javax.swing.JLabel();
        btnAddUser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        lblAddUserToAppointment.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAddUserToAppointment.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAddUserToAppointment.setText("Add user to Appointment");
        lblAddUserToAppointment.setName(""); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search-line.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddUserToAppointment, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAddUserToAppointment, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tabAddedUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "First name", "Last name", "Email", "UserID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabAddedUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabAddedUser.getTableHeader().setReorderingAllowed(false);
        tabAddedUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabAddedUserFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(tabAddedUser);
        if (tabAddedUser.getColumnModel().getColumnCount() > 0) {
            tabAddedUser.getColumnModel().getColumn(0).setResizable(false);
            tabAddedUser.getColumnModel().getColumn(1).setResizable(false);
            tabAddedUser.getColumnModel().getColumn(2).setResizable(false);
            tabAddedUser.getColumnModel().getColumn(3).setResizable(false);
        }

        btnAddToAppointment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save-line.png"))); // NOI18N
        btnAddToAppointment.setText("Save");
        btnAddToAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToAppointmentActionPerformed(evt);
            }
        });

        btnDeleteUserFromAppointment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete-bin-line.png"))); // NOI18N
        btnDeleteUserFromAppointment.setText("Remove");
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtErrorWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteUserFromAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddToAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(txtErrorWarning)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteUserFromAppointment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddToAppointment))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        tabSearchUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "First name", "Last name", "Email", "UserID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabSearchUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabSearchUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabSearchUser.getTableHeader().setReorderingAllowed(false);
        tabSearchUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabSearchUserFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(tabSearchUser);
        if (tabSearchUser.getColumnModel().getColumnCount() > 0) {
            tabSearchUser.getColumnModel().getColumn(0).setResizable(false);
            tabSearchUser.getColumnModel().getColumn(1).setResizable(false);
            tabSearchUser.getColumnModel().getColumn(2).setResizable(false);
            tabSearchUser.getColumnModel().getColumn(3).setResizable(false);
        }

        btnAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-circle-line.png"))); // NOI18N
        btnAddUser.setText("Add user");
        btnAddUser.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txtErrorAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtErrorAdd)
                        .addGap(69, 69, 69)
                        .addComponent(btnAddUser))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        txtErrorAdd.setText("");
        int selectedRowIndex = tabSearchUser.getSelectedRow();
        if (getSelectedUserFromSearchUserTab(selectedRowIndex) != null) {
            this.selectedUser = getSelectedUserFromSearchUserTab(selectedRowIndex);
            userEmail = getSelectedUserFromSearchUserTab(selectedRowIndex).getEmail();
            System.out.println(userEmail);
            if (this.selectedUser != null) {
                bindDataToTableAddedUser(this.selectedUser);
                this.revalidate();
                this.repaint();
            }
        } else {
            txtErrorAdd.setText("Select user before adding to list");
        }

    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnAddToAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToAppointmentActionPerformed
        // TODO add your handling code here:
        int count = tabAddedUser.getModel().getRowCount();

        if (count > 0) {
            ArrayList<Operator> participantsToAdd = new ArrayList();

            for (int i = 0; i < tabAddedUser.getModel().getRowCount(); i++) {

                int userID = (Integer) tabAddedUserModel.getValueAt(i, 3);
                Operator user = getUserByUserID(userID);
                participantsToAdd.add(user);
            }
            frmEvent.setParticipants(participantsToAdd);

        }
        this.setVisible(false);
        frmEvent.revalidate();
        frmEvent.repaint();
    }//GEN-LAST:event_btnAddToAppointmentActionPerformed

    private void btnDeleteUserFromAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserFromAppointmentActionPerformed
        // TODO add your handling code here:
        txtErrorWarning.setText("");
        int selectedRowIndex = tabAddedUser.getSelectedRow();
        if (getSelectedUserFromAddUserTable(selectedRowIndex) != null) {
            this.selectedUser = getSelectedUserFromAddUserTable(selectedRowIndex);
            if (this.eventHost.getUserId() == (Integer) this.selectedUser.getUserId()) {
            JOptionPane.showMessageDialog(null, "You can not delete the Host of the event ");
            } else {
                bindDataToTableSearchUser(this.selectedUser);
                tabAddedUserModel.removeRow(selectedRowIndex);
                this.repaint();
                frmEvent.revalidate();
                frmEvent.repaint();

            }
        } else {
            txtErrorWarning.setText("Please select an user");
        }
        frmEvent.revalidate();
        frmEvent.repaint();

    }//GEN-LAST:event_btnDeleteUserFromAppointmentActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tabSearchUser.getModel();
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(model);
        tabSearchUser.setRowSorter(tableRowSorter);
        tableRowSorter.setRowFilter(RowFilter.regexFilter(txtSearch.getText().trim()));
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tabSearchUserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabSearchUserFocusGained
        // TODO add your handling code here:
        tabAddedUser.clearSelection();
    }//GEN-LAST:event_tabSearchUserFocusGained

    private void tabAddedUserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabAddedUserFocusGained
        // TODO add your handling code here:
        tabSearchUser.clearSelection();
    }//GEN-LAST:event_tabAddedUserFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToAppointment;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnDeleteUserFromAppointment;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAddUserToAppointment;
    private javax.swing.JTable tabAddedUser;
    private javax.swing.JTable tabSearchUser;
    private javax.swing.JLabel txtErrorAdd;
    private javax.swing.JLabel txtErrorWarning;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}