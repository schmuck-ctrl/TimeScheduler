/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

/**
 *
 * @author nilss
 */
public class FrmEvent extends javax.swing.JPanel {

    /**
     * Creates new form FrmEvent
     */
    public FrmEvent() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblHeadline = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        lblEventName = new javax.swing.JLabel();
        txtEventName = new javax.swing.JTextField();
        lblDateTime = new javax.swing.JLabel();
        dtPicker = new com.github.lgooddatepicker.components.DateTimePicker();
        lblEventDuration = new javax.swing.JLabel();
        txtEventDuration = new javax.swing.JTextField();
        lblEventLocation = new javax.swing.JLabel();
        txtEventLocation = new javax.swing.JTextField();
        lblEventPriority = new javax.swing.JLabel();
        cbEventPriority = new javax.swing.JComboBox<>();
        lblEventNotification = new javax.swing.JLabel();
        cbEventNotification = new javax.swing.JComboBox<>();
        lblEventPArticipants = new javax.swing.JLabel();
        pnlEventParticipants = new javax.swing.JPanel();
        lblEventAttachments = new javax.swing.JLabel();
        pnlEventAttachments = new javax.swing.JPanel();
        pnlFooter = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        pnlHeader.setPreferredSize(new java.awt.Dimension(400, 30));
        pnlHeader.setLayout(new java.awt.BorderLayout());
        pnlHeader.add(lblHeadline, java.awt.BorderLayout.CENTER);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlContent.setLayout(new javax.swing.BoxLayout(pnlContent, javax.swing.BoxLayout.Y_AXIS));

        lblEventName.setText("Name:");
        lblEventName.setToolTipText("");
        pnlContent.add(lblEventName);
        pnlContent.add(txtEventName);

        lblDateTime.setText("Date:");
        pnlContent.add(lblDateTime);
        pnlContent.add(dtPicker);

        lblEventDuration.setText("Duration");
        pnlContent.add(lblEventDuration);
        pnlContent.add(txtEventDuration);

        lblEventLocation.setText("Location");
        pnlContent.add(lblEventLocation);
        pnlContent.add(txtEventLocation);

        lblEventPriority.setText("Priority");
        pnlContent.add(lblEventPriority);

        cbEventPriority.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlContent.add(cbEventPriority);

        lblEventNotification.setText("Notification:");
        pnlContent.add(lblEventNotification);

        cbEventNotification.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlContent.add(cbEventNotification);

        lblEventPArticipants.setText("Participants:");
        pnlContent.add(lblEventPArticipants);

        javax.swing.GroupLayout pnlEventParticipantsLayout = new javax.swing.GroupLayout(pnlEventParticipants);
        pnlEventParticipants.setLayout(pnlEventParticipantsLayout);
        pnlEventParticipantsLayout.setHorizontalGroup(
            pnlEventParticipantsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );
        pnlEventParticipantsLayout.setVerticalGroup(
            pnlEventParticipantsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        pnlContent.add(pnlEventParticipants);

        lblEventAttachments.setText("Attachments:");
        pnlContent.add(lblEventAttachments);

        javax.swing.GroupLayout pnlEventAttachmentsLayout = new javax.swing.GroupLayout(pnlEventAttachments);
        pnlEventAttachments.setLayout(pnlEventAttachmentsLayout);
        pnlEventAttachmentsLayout.setHorizontalGroup(
            pnlEventAttachmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );
        pnlEventAttachmentsLayout.setVerticalGroup(
            pnlEventAttachmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );

        pnlContent.add(pnlEventAttachments);

        add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlFooter.setLayout(new java.awt.GridLayout());

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete-bin-line.png"))); // NOI18N
        btnDelete.setText("Delete");
        pnlFooter.add(btnDelete);

        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save-line.png"))); // NOI18N
        btnOk.setText("Ok");
        pnlFooter.add(btnOk);

        add(pnlFooter, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<String> cbEventNotification;
    private javax.swing.JComboBox<String> cbEventPriority;
    private com.github.lgooddatepicker.components.DateTimePicker dtPicker;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblEventAttachments;
    private javax.swing.JLabel lblEventDuration;
    private javax.swing.JLabel lblEventLocation;
    private javax.swing.JLabel lblEventName;
    private javax.swing.JLabel lblEventNotification;
    private javax.swing.JLabel lblEventPArticipants;
    private javax.swing.JLabel lblEventPriority;
    private javax.swing.JLabel lblHeadline;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlEventAttachments;
    private javax.swing.JPanel pnlEventParticipants;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTextField txtEventDuration;
    private javax.swing.JTextField txtEventLocation;
    private javax.swing.JTextField txtEventName;
    // End of variables declaration//GEN-END:variables
}
