/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import classes.Operator;
import handlers.UserHandler;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * Displays all operators from the database and provides methods for editing.
 * Only visible for operators whose role is "Admin".
 * 
 * @author Nils Schmuck
 */
public class FrmAdminInterface extends javax.swing.JDialog {

    // <editor-fold defaultstate="collapsed" desc="Global Variables">
    private DefaultTableModel tabUserModel = null;
    private Operator selectedUser = null;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new dialog with the specified modality and Frame as its owner.
     * In addition the specified user will be set.
     * 
     * @param parentThe Frame from which the dialog is displayed.
     * @param modal Specifies if the dialog blocks user input to other windows when shown.
     */
    public FrmAdminInterface(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabUserModel = (DefaultTableModel) tabUser.getModel();

        bindDataToTable(getUsers());

        

    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Binds the Collection to this {@link FrmAdminInterface#tabUser}.
     * @param users The collection whose elements are to be placed into this table.
     */
    private void bindDataToTable(ArrayList<Operator> users) {
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                this.tabUserModel.addRow(new Object[]{
                    users.get(i).getUserId(),
                    users.get(i).getFirstName().toString(),
                    users.get(i).getLastName().toString(),
                    users.get(i).getEmail().toString()
                });
            }
        }
        
        TableColumn col = tabUser.getColumn("UserID");
        col.setMaxWidth(0);
        col.setMinWidth(0);
        col.setPreferredWidth(0);
    }

    /**
     * Returns a list with all operators in the database.
     * 
     * @return The list with all operators.
     */
    private ArrayList<Operator> getUsers() {
        UserHandler uHandler = new UserHandler();

        return uHandler.getAllUser();
    }

    /**
     * Returns the Operator at the specified position in this table.
     * @param rowIndex Index of the operator to return.
     * @return The operator at the specified position in this table.
     */
    private Operator getSelectedUserFromTabUser(int rowIndex) {
        Operator selectedUser = null;
        UserHandler uHandler = new UserHandler();

        if (rowIndex >= 0) {
            int userId = (int) tabUserModel.getValueAt(rowIndex, 0); //0 = ID spalte.
            selectedUser = uHandler.getUser(userId);
        }

        return selectedUser;
    }

    /**
     * Reloads the data in this list.
     */
    private void refreshTable() {
        this.tabUserModel.setRowCount(0);
        this.tabUserModel = (DefaultTableModel) tabUser.getModel();
        bindDataToTable(getUsers());
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
        pnlContent = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabUser = new javax.swing.JTable();
        pnlFooter = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Admin interface");
        setPreferredSize(new java.awt.Dimension(650, 350));

        pnlHeader.setPreferredSize(new java.awt.Dimension(690, 45));

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        getContentPane().add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlContent.setLayout(new java.awt.BorderLayout());

        tabUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UserID", "First name", "Last name", "E-Mail"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        tabUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabUser.getTableHeader().setReorderingAllowed(false);
        tabUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabUser);
        if (tabUser.getColumnModel().getColumnCount() > 0) {
            tabUser.getColumnModel().getColumn(0).setResizable(false);
            tabUser.getColumnModel().getColumn(1).setResizable(false);
            tabUser.getColumnModel().getColumn(2).setResizable(false);
            tabUser.getColumnModel().getColumn(3).setResizable(false);
        }

        pnlContent.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setPreferredSize(new java.awt.Dimension(690, 45));

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close-line.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setMaximumSize(new java.awt.Dimension(95, 35));
        btnCancel.setMinimumSize(new java.awt.Dimension(95, 35));
        btnCancel.setPreferredSize(new java.awt.Dimension(95, 35));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        pnlFooter.add(btnCancel);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete-bin-line.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setMaximumSize(new java.awt.Dimension(95, 35));
        btnDelete.setMinimumSize(new java.awt.Dimension(95, 35));
        btnDelete.setPreferredSize(new java.awt.Dimension(95, 35));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlFooter.add(btnDelete);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pencil-line.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.setMaximumSize(new java.awt.Dimension(95, 35));
        btnEdit.setMinimumSize(new java.awt.Dimension(95, 35));
        btnEdit.setPreferredSize(new java.awt.Dimension(95, 35));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        pnlFooter.add(btnEdit);

        getContentPane().add(pnlFooter, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Events">

    /**
     * Opens the {@link FrmEditUser} form to edit the selected operator.
     * 
     * @param evt The action event.
     */
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (this.selectedUser != null) {
            FrmEditUser frmEditUserForm = new FrmEditUser((JFrame) this.getParent(), true, this.selectedUser);
            frmEditUserForm.setVisible(true);
            this.refreshTable();
            this.selectedUser = null;
        } else {
            JOptionPane.showMessageDialog(this, "You have to select a user first in order to edit the user.", "No user selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    /**
     * Delets the operator at the specified position in this list.
     * 
     * @param evt The action event.
     */
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (this.selectedUser != null) {
            int retVal = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the user?", "Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (retVal == JOptionPane.YES_OPTION) {
                UserHandler uHander = new UserHandler();
                uHander.deleteUser(this.selectedUser);
                this.refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "You have to select a user first in order to delete the user.", "No user selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * Opens the {@link FrmEditUser} form to edit the selected operator.
     * 
     * @param evt The action event.
     */
    private void tabUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabUserMouseClicked
        int selectedRowIndex = tabUser.getSelectedRow();
        this.selectedUser = getSelectedUserFromTabUser(selectedRowIndex);

        if (evt.getClickCount() == 2) {
            if (selectedUser != null) {
                FrmEditUser frmEditUserForm = new FrmEditUser((JFrame) this.getParent(), true, selectedUser);
                frmEditUserForm.setVisible(true);
                this.refreshTable();
            }
        }
    }//GEN-LAST:event_tabUserMouseClicked

    /**
     * Close this and discard all changes.
     * 
     * @param evt The action event.
     */
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    // </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTable tabUser;
    // End of variables declaration//GEN-END:variables
}
