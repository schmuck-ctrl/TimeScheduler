/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import classes.Admin;
import classes.Operator;
import classes.User;
import handlers.UserHandler;
import javax.swing.JOptionPane;

/**
 *
 * @author nilss
 */
public class FrmEditUser extends javax.swing.JDialog {

    // <editor-fold defaultstate="collapsed" desc="Global Variables">
    
    private Operator user = null;
    private boolean isAdmin = false;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates new form FrmEditUser
     */
    public FrmEditUser(java.awt.Frame parent, boolean modal, Operator user) {
        super(parent, modal);
        initComponents();
        setUser(user);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void setUser(Operator user) {
        if (user != null) {
            this.user = user;
            
            txtFirstName.setText(this.user.getFirstName());
            txtLastName.setText(this.user.getLastName());
            txtEmail.setText(this.user.getEmail());
            
            chkAdminPrivileges.setSelected(this.user.getRole() == Operator.Role.ADMIN ? true : false);
        }
    }
    
    private void editFirstName(String firstName) {
        if (firstName != null && !firstName.isBlank()) {
            user.setFirstName(firstName);
        }
    }

    private void editLastName(String lastName) {
        if (lastName != null && !lastName.isBlank()) {
            user.setLastName(lastName);
        }
    }

    private void editEmail(String email) {
        if (email != null && !email.isBlank()) {
            user.setEmail(email);
        }
    }

    private boolean checkIfDataChanged() {
        boolean edit = false;

        if (this.user.getFirstName() != txtFirstName.getText().trim()) {
            editFirstName(txtFirstName.getText());
            edit = true;
        }

        if (this.user.getLastName() != txtLastName.getText().trim()) {
            editLastName(txtLastName.getText());
            edit = true;
        }

        if (this.user.getEmail() != txtEmail.getText().trim()) {
            editEmail(txtEmail.getText());
            edit = true;
        }

        if (this.user.getRole() == Operator.Role.USER && chkAdminPrivileges.isSelected()) {
            isAdmin = true;
            edit = true;
        } else if (this.user.getRole() == Operator.Role.ADMIN && !chkAdminPrivileges.isSelected()) {
            isAdmin = false;
            edit = true;
        }

        return edit;
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
        lblFirstName = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        chkAdminPrivileges = new javax.swing.JCheckBox();
        pnlFooter = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit user");
        setPreferredSize(new java.awt.Dimension(400, 250));

        pnlHeader.setPreferredSize(new java.awt.Dimension(471, 45));

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        getContentPane().add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        lblFirstName.setText("First name:");

        lblLastName.setText("Last name:");

        lblEmail.setText("Email:");

        chkAdminPrivileges.setText("Admin privileges");

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addComponent(lblFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFirstName))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLastName)
                            .addComponent(txtEmail)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(chkAdminPrivileges)
                                .addGap(0, 195, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLastName)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chkAdminPrivileges)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setPreferredSize(new java.awt.Dimension(471, 45));

        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save-line.png"))); // NOI18N
        btnOk.setText("Ok");
        btnOk.setMaximumSize(new java.awt.Dimension(95, 35));
        btnOk.setMinimumSize(new java.awt.Dimension(95, 35));
        btnOk.setPreferredSize(new java.awt.Dimension(95, 35));
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout pnlFooterLayout = new javax.swing.GroupLayout(pnlFooter);
        pnlFooter.setLayout(pnlFooterLayout);
        pnlFooterLayout.setHorizontalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlFooterLayout.setVerticalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(pnlFooter, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Events">
    
    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (checkIfDataChanged()) {
            UserHandler uHandler = new UserHandler();
            if (isAdmin) {
                uHandler.editUser(new Admin(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail()));
            } else {
                uHandler.editUser(new User(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail()));
            }
        }
        
        this.dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int retVal = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the user?", "Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (retVal == JOptionPane.YES_OPTION) {
            UserHandler uHandler = new UserHandler();
            uHandler.deleteUser(user);
            this.dispose();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    // </editor-fold>
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnOk;
    private javax.swing.JCheckBox chkAdminPrivileges;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    // End of variables declaration//GEN-END:variables
}
