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

/**
 *
 * @author nilss
 */
public class FrmAdminInterface extends javax.swing.JDialog {

    // <editor-fold defaultstate="collapsed" desc="Global Variables">
    
    private DefaultTableModel tabUserModel = null;
    private Operator selectedUser = null;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates new form FrmAdminInterface
     */
    public FrmAdminInterface(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabUserModel = (DefaultTableModel) tabUser.getModel();

        bindDataToTable(getUsers());
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
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

    }

    private ArrayList<Operator> getUsers() {
        UserHandler uHandler = new UserHandler();

        return uHandler.getAllUser();
    }

    private Operator getSelectedUserFromTabUser(int rowIndex) {
        Operator selectedUser = null;
        UserHandler uHandler = new UserHandler();

        if (rowIndex >= 0) {
            int userId = (int) tabUserModel.getValueAt(rowIndex, 0); //0 = ID spalte.
            selectedUser = uHandler.getUser(userId);
        }

        return selectedUser;
    }

    private void refreshTable(){
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
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlHeader.setPreferredSize(new java.awt.Dimension(690, 50));

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
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
        tabUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabUser);

        pnlContent.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setPreferredSize(new java.awt.Dimension(690, 75));

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pencil-line.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete-bin-line.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close-line.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFooterLayout = new javax.swing.GroupLayout(pnlFooter);
        pnlFooter.setLayout(pnlFooterLayout);
        pnlFooterLayout.setHorizontalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 407, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit)
                .addContainerGap())
        );
        pnlFooterLayout.setVerticalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        getContentPane().add(pnlFooter, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Events">
    
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (this.selectedUser != null) {
            FrmEditUser frmEditUserForm = new FrmEditUser((JFrame) this.getParent(), true, this.selectedUser);
            frmEditUserForm.setVisible(true);
            this.refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "You have to select a user first in order to edit the user.", "No user selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int retVal = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the user?", "Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (retVal == JOptionPane.YES_OPTION) {
            UserHandler uHander = new UserHandler();
            uHander.deleteUser(this.selectedUser);
            this.refreshTable();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tabUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabUserMouseClicked
        int selectedRowIndex = tabUser.getSelectedRow();
        this.selectedUser = getSelectedUserFromTabUser(selectedRowIndex);

        if (evt.getClickCount() == 2) {
            if (selectedUser != null) {
                FrmEditUser frmEditUserForm = new FrmEditUser((JFrame)this.getParent(), true, selectedUser);
                frmEditUserForm.setVisible(true);
                this.refreshTable();
            }
        }
    }//GEN-LAST:event_tabUserMouseClicked

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
