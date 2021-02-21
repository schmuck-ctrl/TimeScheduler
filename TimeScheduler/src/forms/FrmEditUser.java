/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import classes.Admin;
import classes.Operator;
import classes.User;
import handlers.LoggerHandler;
import handlers.UserHandler;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 * Displays the settings (first name, last name, email and role) of the
 * specified user. In addition it is also possible to edit them.
 *
 * @author Nils Schmuck
 */
public class FrmEditUser extends javax.swing.JDialog {

    // <editor-fold defaultstate="collapsed" desc="Global Variables">
    /**
     * The user which is displayed.
     */
    private Operator user = null;
    /**
     * True if this operator has the role "Admin"
     */
    private boolean isAdmin = false;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates a new dialog with the specified modality and Frame as its owner.
     * In addition the specified user will be set.
     *
     * @param parent The Frame from which the dialog is displayed.
     * @param modal Specifies if the dialog blocks user input to other windows
     * when shown.
     * @param user The user for the settings.
     */
    public FrmEditUser(java.awt.Frame parent, boolean modal, Operator user) {
        super(parent, modal);
        initComponents();

        setUser(user);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Sets this user to the specified user and loads the data in the relevant
     * controls on the form.
     *
     * @param user The user who is to be set in this and displayed in the form.
     */
    public void setUser(Operator user) {
        if (user != null) {
            this.user = user;

            if (this.user.getUserId() == FrmMain.getInstance().getCurrentUser().getUserId()) {
                btnDelete.setEnabled(false);
                btnDelete.setVisible(false);
                chkAdminPrivileges.setEnabled(false);
                txtFirstName.setEnabled(false);
                txtFirstName.setEditable(false);
            }

            txtFirstName.setText(this.user.getFirstName());
            txtLastName.setText(this.user.getLastName());
            txtEmail.setText(this.user.getEmail());

            chkAdminPrivileges.setSelected(this.user.getRole() == Operator.Role.ADMIN ? true : false);
            
            LoggerHandler.logger.info("User successfully loaded.");
        } else {
            LoggerHandler.logger.severe("User is NULL.");
        }
    }

    /**
     * Validates if the String match the pattern.
     *
     * @param firstName The String from {@link#txtFirstName}
     * @return Returns true if the String match pattern.
     */
    private boolean validateFirstName(String firstName) {
        if (firstName != null && !firstName.isBlank()) {

            Pattern pattern = Pattern.compile("^[A-Za-z]{2,}$");
            Matcher matcher = pattern.matcher(firstName);
            boolean matchFound = matcher.find();

            if (matchFound) {
                txtFirstName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            } else {
                txtFirstName.setBorder(BorderFactory.createLineBorder(Color.RED));
            }

            return matchFound;
        }
        txtFirstName.setBorder(BorderFactory.createLineBorder(Color.RED));

        return false;
    }

    /**
     * Validates if the String match the pattern
     *
     * @param lastNameThe String from {@link#txtLastName}
     * @return Returns true if the String match pattern.
     */
    private boolean validateLastName(String lastName) {
        if (lastName != null && !lastName.isBlank()) {

            Pattern pattern = Pattern.compile("^[A-Za-z]{2,}$");
            Matcher matcher = pattern.matcher(lastName);
            boolean matchFound = matcher.find();

            if (matchFound) {
                txtLastName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            } else {
                txtLastName.setBorder(BorderFactory.createLineBorder(Color.RED));
            }

            return matchFound;
        }
        txtLastName.setBorder(BorderFactory.createLineBorder(Color.RED));

        return false;
    }

    /**
     * Validates if the String match the pattern
     *
     * @param The String from {@link#txtEmail}
     * @return Returns true if the String match pattern.
     */
    private boolean validateEmail(String email) {
        if (email != null && !email.isBlank()) {

            Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
            Matcher matcher = pattern.matcher(email);
            boolean matchFound = matcher.find();

            if (matchFound) {
                txtEmail.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            } else {
                txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
            }

            return matchFound;
        }
        txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));

        return false;
    }

    /**
     * Validate the user input if the input changed. If input changed, the
     * gloabel User will changed.
     *
     * @return -1 User input not correct. 0 Input not changed. 1 Global user
     * updated successfully.
     */
    private int getInput() {
        if (checkIfDataChanged()) {

            if (validateInput()) {

                int id = this.user.getUserId();
                String firstName = txtFirstName.getText().trim();
                String lastNString = txtLastName.getText().trim();
                String email = txtEmail.getText().trim();

                Operator newUser = null;

                if (this.isAdmin) {
                    this.user = new Admin(id, firstName, lastNString, email);
                } else {
                    this.user = new User(id, firstName, lastNString, email);
                }
                
                LoggerHandler.logger.info("Input correct");
                return 1;
            } else {
                LoggerHandler.logger.info("Input not correct.");
                return -1;
            }

        } else {
            LoggerHandler.logger.info("Data  not changed.");
            return 0;  
        }

    }

    /**
     * Checks whether the content of the controls corresponds to the validation.
     *
     * @return Returns true if and only if all controls match the specified
     * pattern.
     *
     * @see FrmEditUser#validateFirstName(java.lang.String)
     * @see FrmEditUser#validateLastName(java.lang.String)
     * @see FrmEditUser#validateEmail(java.lang.String)
     */
    private boolean validateInput() {
        boolean checkFirstName = false;
        boolean checkLastName = false;
        boolean checkEmail = false;

        checkFirstName = validateFirstName(txtFirstName.getText().trim());
        checkLastName = validateLastName(txtLastName.getText().trim());
        checkEmail = validateEmail(txtEmail.getText().trim());

        if (checkFirstName && checkLastName && checkEmail) {
            LoggerHandler.logger.info("Validation successful.");
            return true;
        } else {
            LoggerHandler.logger.info("Validation failed.");
            return false;
        }
    }

    /**
     * Checks if the input of an control changed.
     *
     * @return Returns true if and only if the input of an control changed.
     *
     * @throws NullPointerException
     */
    private boolean checkIfDataChanged() {
        boolean dataChanged = false;

        try {

            if (!txtFirstName.getText().trim().equals(this.user.getFirstName())
                    || !txtLastName.getText().trim().equals(this.user.getLastName())
                    || !txtEmail.getText().trim().equals(this.user.getEmail())) {
                dataChanged = true;
            }

            if (this.user.getRole() == Operator.Role.USER && chkAdminPrivileges.isSelected()) {
                this.isAdmin = true;
                dataChanged = true;
            } else if (this.user.getRole() == Operator.Role.ADMIN && !chkAdminPrivileges.isSelected()) {
                this.isAdmin = false;
                dataChanged = true;
            }

        } catch (NullPointerException e) {
            LoggerHandler.logger.severe(e.getMessage());
        }

        return dataChanged;
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

        pnlContent = new javax.swing.JPanel();
        lblFirstName = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        chkAdminPrivileges = new javax.swing.JCheckBox();
        lblInformation = new javax.swing.JLabel();
        pnlFooter = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit user");
        setMinimumSize(new java.awt.Dimension(470, 270));
        setPreferredSize(new java.awt.Dimension(470, 270));

        pnlContent.setPreferredSize(new java.awt.Dimension(470, 200));

        lblFirstName.setText("First name: *");

        lblLastName.setText("Last name: *");

        lblEmail.setText("Email: *");

        txtFirstName.setToolTipText("At least 2 characters. only letters.");

        txtLastName.setToolTipText("At least 2 characters. only letters.");

        txtEmail.setToolTipText(" At least the following characters: letter,. and @.");

        chkAdminPrivileges.setText("Admin privileges");

        lblInformation.setText("* mandatory fields");

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFirstName)
                            .addComponent(txtLastName)
                            .addComponent(txtEmail)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(chkAdminPrivileges)
                                .addGap(0, 266, Short.MAX_VALUE))))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addComponent(lblInformation)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(lblInformation)
                .addContainerGap())
        );

        getContentPane().add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setPreferredSize(new java.awt.Dimension(471, 45));

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
        pnlFooter.add(btnOk);

        getContentPane().add(pnlFooter, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Events">
    /**
     * Checks the input and save the changes if necessary.
     *
     * @param evt The action event.
     * @see FrmEditUser#getInput()
     * @see handlers.UserHandler#editUser(classes.Operator)
     */
    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        int retVal = getInput();

        if (retVal == 1) {
            UserHandler uHandler = new UserHandler();
            uHandler.editUser(this.user);
            this.dispose();
        } else if (retVal == -1) {
            JOptionPane.showMessageDialog(this, "Please check your input.", "Invalid input", JOptionPane.WARNING_MESSAGE);
        } else if (retVal == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_btnOkActionPerformed

    /**
     * Delete the specified user.
     *
     * @param evt The action event.
     * @see handlers.UserHandler#deleteUser(classes.Operator)
     */
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int retVal = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the user?", "Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (retVal == JOptionPane.YES_OPTION) {
            UserHandler uHandler = new UserHandler();
            uHandler.deleteUser(this.user);
            this.dispose();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * Close this and discard all changes.
     *
     * @param evt
     */
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
    private javax.swing.JLabel lblInformation;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    // End of variables declaration//GEN-END:variables
}
