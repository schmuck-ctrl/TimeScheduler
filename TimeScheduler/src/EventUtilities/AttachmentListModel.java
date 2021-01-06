/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventUtilities;

import java.io.File;
import javax.swing.DefaultListModel;

/**
 *
 * @author nilss
 */
public class AttachmentListModel extends DefaultListModel{
    
    public Object getElementAt(int index) {
        File attachment = (File)super.getElementAt(index);
        return attachment.getPath();
    }
    
    public void addElement(Object obj) {
        if(!this.contains(obj))
        {
            this.addElement(obj);
        }
    }
    
}
