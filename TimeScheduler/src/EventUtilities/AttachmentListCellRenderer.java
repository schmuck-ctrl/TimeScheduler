/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventUtilities;

import java.awt.Component;
import java.io.File;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author nilss
 */
public class AttachmentListCellRenderer extends DefaultListCellRenderer{
     public Component getListCellRendererComponent(
        JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        File file = (File)value;
        setText(file.getName());

        return this;
    }
    
}
