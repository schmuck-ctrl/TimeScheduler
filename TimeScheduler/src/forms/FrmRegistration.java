/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import classes.*;
import handlers.*;
import java.awt.Color;
import javax.swing.ToolTipManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Vadym
 */
public class FrmRegistration extends javax.swing.JFrame {

    /**
     * Creates new form FrmRegistration
     */
    public FrmRegistration() {
        initComponents();

        setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/calendar-todo-fill.png")));

        this.pnlHeader.setBackground(new Color(255, 255, 255));
        this.pnlContent.setBackground(new Color(255, 255, 255));
        this.pnlFooter.setBackground(new Color(255, 255, 255));
        int dismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
        dismissDelay = Integer.MAX_VALUE;
        ToolTipManager.sharedInstance().setDismissDelay(dismissDelay);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFooter = new javax.swing.JPanel();
        btnRegistration = new javax.swing.JButton();
        btnBackToLogin = new javax.swing.JButton();
        pnlHeader = new javax.swing.JPanel();
        lblRegistration = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        lblFirstName = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblRepeatPassword = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        ptxtPassword = new javax.swing.JPasswordField();
        ptxtRepeatPassword = new javax.swing.JPasswordField();
        lblFirstNameError = new javax.swing.JLabel();
        lblLastNameError = new javax.swing.JLabel();
        lblEmailError = new javax.swing.JLabel();
        lblPasswordError = new javax.swing.JLabel();
        lblRepeatPasswordError = new javax.swing.JLabel();
        lblPasswordToolTip = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        btnRegistration.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRegistration.setText("Create account");
        btnRegistration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrationActionPerformed(evt);
            }
        });

        btnBackToLogin.setBackground(new java.awt.Color(255, 255, 255));
        btnBackToLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/arrow-left-circle-line.png"))); // NOI18N
        btnBackToLogin.setToolTipText("back to login");
        btnBackToLogin.setBorder(null);
        btnBackToLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackToLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFooterLayout = new javax.swing.GroupLayout(pnlFooter);
        pnlFooter.setLayout(pnlFooterLayout);
        pnlFooterLayout.setHorizontalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnBackToLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114)
                .addComponent(btnRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(193, Short.MAX_VALUE))
        );
        pnlFooterLayout.setVerticalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBackToLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(pnlFooter, java.awt.BorderLayout.PAGE_END);

        lblRegistration.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblRegistration.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRegistration.setText("Registration");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRegistration, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        lblFirstName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblFirstName.setForeground(new java.awt.Color(0, 0, 0));
        lblFirstName.setText("First Name:");

        lblLastName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(0, 0, 0));
        lblLastName.setText("Last Name:");

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(0, 0, 0));
        lblEmail.setText("E-Mail:");

        lblPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(0, 0, 0));
        lblPassword.setText("Password:");

        lblRepeatPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRepeatPassword.setForeground(new java.awt.Color(0, 0, 0));
        lblRepeatPassword.setText("Repeat Password:");

        txtFirstName.setBackground(new java.awt.Color(243, 242, 241));
        txtFirstName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFirstName.setForeground(new java.awt.Color(0, 0, 0));
        txtFirstName.setToolTipText("First name should contain at least two letters");
        txtFirstName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFirstNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFirstNameFocusLost(evt);
            }
        });

        txtLastName.setBackground(new java.awt.Color(243, 242, 241));
        txtLastName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtLastName.setForeground(new java.awt.Color(0, 0, 0));
        txtLastName.setToolTipText("Last name should contain at least two letters");
        txtLastName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLastNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLastNameFocusLost(evt);
            }
        });

        txtEmail.setBackground(new java.awt.Color(243, 242, 241));
        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(0, 0, 0));
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });

        ptxtPassword.setBackground(new java.awt.Color(243, 242, 241));
        ptxtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ptxtPassword.setForeground(new java.awt.Color(0, 0, 0));
        ptxtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ptxtPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ptxtPasswordFocusLost(evt);
            }
        });

        ptxtRepeatPassword.setBackground(new java.awt.Color(243, 242, 241));
        ptxtRepeatPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ptxtRepeatPassword.setForeground(new java.awt.Color(0, 0, 0));
        ptxtRepeatPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ptxtRepeatPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ptxtRepeatPasswordFocusLost(evt);
            }
        });

        lblFirstNameError.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblFirstNameError.setForeground(new java.awt.Color(204, 0, 51));
        lblFirstNameError.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lblLastNameError.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLastNameError.setForeground(new java.awt.Color(204, 0, 51));
        lblLastNameError.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lblEmailError.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblEmailError.setForeground(new java.awt.Color(204, 0, 51));
        lblEmailError.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lblPasswordError.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPasswordError.setForeground(new java.awt.Color(204, 0, 51));
        lblPasswordError.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lblRepeatPasswordError.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRepeatPasswordError.setForeground(new java.awt.Color(204, 0, 51));
        lblRepeatPasswordError.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lblPasswordToolTip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/question-line.png"))); // NOI18N
        lblPasswordToolTip.setToolTipText("");
        lblPasswordToolTip.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblPasswordToolTipMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLastName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFirstName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRepeatPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentLayout.createSequentialGroup()
                        .addComponent(lblPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPasswordToolTip)))
                .addGap(18, 18, 18)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtLastName, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(txtFirstName)
                    .addComponent(ptxtPassword)
                    .addComponent(ptxtRepeatPassword))
                .addGap(16, 16, 16)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPasswordError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEmailError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFirstNameError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLastNameError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addComponent(lblRepeatPasswordError, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFirstNameError))
                .addGap(18, 18, 18)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLastName)
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblLastNameError)
                        .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmailError))
                .addGap(18, 18, 18)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPassword)
                        .addComponent(lblPasswordError)
                        .addComponent(lblPasswordToolTip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ptxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRepeatPassword)
                    .addComponent(ptxtRepeatPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRepeatPasswordError))
                .addGap(19, 19, 19))
        );

        getContentPane().add(pnlContent, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtFirstNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFirstNameFocusGained

        txtFirstName.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtFirstNameFocusGained

    private void txtFirstNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFirstNameFocusLost

        txtFirstName.setBackground(new Color(243, 242, 241));
    }//GEN-LAST:event_txtFirstNameFocusLost

    private void txtLastNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLastNameFocusGained

        txtLastName.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtLastNameFocusGained

    private void txtLastNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLastNameFocusLost

        txtLastName.setBackground(new Color(243, 242, 241));
    }//GEN-LAST:event_txtLastNameFocusLost

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained

        txtEmail.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost

        txtEmail.setBackground(new Color(243, 242, 241));
    }//GEN-LAST:event_txtEmailFocusLost

    private void ptxtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ptxtPasswordFocusGained

        ptxtPassword.setBackground(Color.WHITE);
    }//GEN-LAST:event_ptxtPasswordFocusGained

    private void ptxtPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ptxtPasswordFocusLost

        ptxtPassword.setBackground(new Color(243, 242, 241));
    }//GEN-LAST:event_ptxtPasswordFocusLost

    private void ptxtRepeatPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ptxtRepeatPasswordFocusGained

        ptxtRepeatPassword.setBackground(Color.WHITE);
    }//GEN-LAST:event_ptxtRepeatPasswordFocusGained

    private void ptxtRepeatPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ptxtRepeatPasswordFocusLost

        ptxtRepeatPassword.setBackground(new Color(243, 242, 241));
    }//GEN-LAST:event_ptxtRepeatPasswordFocusLost

    private void btnRegistrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrationActionPerformed
        lblFirstNameError.setText("");
        lblLastNameError.setText("");
        lblEmailError.setText("");
        lblEmailError.setText("");
        lblPasswordError.setText("");
        lblRepeatPasswordError.setText("");
        RegistrationHandler reHandler = new RegistrationHandler();
        boolean inputError = true;

        if (!reHandler.checkInputUserName(txtFirstName.getText())) {
            lblFirstNameError.setText("Firstname is incorrect");
            inputError = false;
        }
        if (!reHandler.checkInputUserName(txtLastName.getText())) {
            lblLastNameError.setText("Lastname is incorrect");
            inputError = false;
        }
        if (!reHandler.checkInputUserEmail(txtEmail.getText())) {
            lblEmailError.setText("Email is incorrect");
            inputError = false;
        }
        if (!reHandler.checkIfNewUserExist(txtEmail.getText())) {
            lblEmailError.setText("User already exists");
            inputError = false;
        }
        if (!reHandler.checkInputUserPassword(ptxtPassword.getPassword())) {
            lblPasswordError.setText("Password is incorrect");
            inputError = false;
        } else if (!reHandler.checkIfPasswordTheSame(ptxtPassword.getPassword(), ptxtRepeatPassword.getPassword())) {
            lblRepeatPasswordError.setText("Password does not match ");
            inputError = false;
        }
        if (inputError == true) {
//            int rand = reHandler.getRandomEmailVerificationNumber();
//            reHandler.sendEmailVerificationCode(txtEmail.getText().trim(), rand);
//            String userVerificationInput = JOptionPane.showInputDialog("Email with the verification code was send to you");
            if (reHandler.checkVerificationCode(txtEmail.getText()) == true) {
                User user = new User(txtFirstName.getText().trim(), txtLastName.getText().trim(), txtEmail.getText().trim());
                reHandler.createNewUser(user, ptxtPassword.getPassword());
                JOptionPane.showMessageDialog(null, "Registration was successful", "Registration Information", JOptionPane.INFORMATION_MESSAGE);
                FrmLogin frmLogin = new FrmLogin();
                frmLogin.setVisible(true);
                this.dispose();

            }

        }

        ptxtPassword.setText("");
        ptxtRepeatPassword.setText("");

    }//GEN-LAST:event_btnRegistrationActionPerformed

    private void lblPasswordToolTipMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPasswordToolTipMouseEntered

        lblPasswordToolTip.setToolTipText("<html>Your password should contain at least 8 characters <br> at least one upper case letter <br> at least one lower case letter <br> at least one digit <br> at least one of those special symbols (@#$%^&+=]) </html>");
    }//GEN-LAST:event_lblPasswordToolTipMouseEntered

    private void btnBackToLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackToLoginActionPerformed
        // TODO add your handling code here:
        new FrmLogin().setVisible(true);
        this.dispose();

    }//GEN-LAST:event_btnBackToLoginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBackToLogin;
    private javax.swing.JButton btnRegistration;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEmailError;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblFirstNameError;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblLastNameError;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPasswordError;
    private javax.swing.JLabel lblPasswordToolTip;
    private javax.swing.JLabel lblRegistration;
    private javax.swing.JLabel lblRepeatPassword;
    private javax.swing.JLabel lblRepeatPasswordError;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPasswordField ptxtPassword;
    private javax.swing.JPasswordField ptxtRepeatPassword;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    // End of variables declaration//GEN-END:variables
}
