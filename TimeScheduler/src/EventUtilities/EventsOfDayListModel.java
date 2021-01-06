/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventUtilities;

import classes.Event;
import javax.swing.DefaultListModel;

/**
 *
 * @author nilss
 */
public class EventsOfDayListModel extends DefaultListModel{
    
    public Object getElementAt(int index) {
        Event event = (Event)super.getElementAt(index);
        return event.getID();
    }
    
    public void addElement(Object obj) {
        if(!this.contains(obj))
        {
            this.addElement(obj);
        }
    }
    
}
