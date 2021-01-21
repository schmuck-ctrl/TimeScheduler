/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventUtilities;

import classes.Attachment;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Default ListCellRenderer for the JList {@link forms.FrmEvent#liEventAttachments} in {@link forms.FrmEvent}.
 * to display the name of the File in the specified JList.
 * 
 * @author Nils Schmuck
 */
public class AttachmentListCellRenderer extends DefaultListCellRenderer{
    
    /**
     * Return a JList that has been configured to display the file name value.
     * 
     * @param list The JList we're painting.
     * @param value The value returned by list.getModel().getElementAt(index).
     * @param index The cells index.
     * @param isSelected True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * 
     * @return A component whose paint() method will render the specified value.
     */ 
    public Component getListCellRendererComponent(
        JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        Attachment file = (Attachment)value;
        setText(file.getFileName());

        return this;
    }
    
}
