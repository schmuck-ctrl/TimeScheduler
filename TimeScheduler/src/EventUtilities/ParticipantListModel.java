/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventUtilities;

import classes.Operator;
import javax.swing.DefaultListModel;

/**
 *
 * @author nilss
 */
public class ParticipantListModel extends DefaultListModel{
    
    public Object getElementAt(int index) {
        Operator user = (Operator)super.getElementAt(index);
        return user.getEmail();
    }
    
    public void addElement(Object obj) {
        if(!this.contains(obj))
        {
            this.addElement(obj);
        }
    }
    
}
