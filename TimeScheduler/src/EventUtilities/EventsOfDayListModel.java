/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventUtilities;

import classes.Event;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author nilss
 */
public final class EventsOfDayListModel extends AbstractListModel<Event>{

    private ArrayList<Event> listOfEvents = null;

    public EventsOfDayListModel() {
        this.listOfEvents = new ArrayList();
    }
    
    public EventsOfDayListModel(ArrayList<Event> events) {
        this.listOfEvents = new ArrayList();
        addElement(events);
    }
    
    public void addElement(Event event) {
        if(event != null)
            this.listOfEvents.add(event);
    }
    
    public void addElement(ArrayList<Event> events) {
        if(events != null)
            this.listOfEvents = events;
    }
    
    @Override
    public int getSize() {
        return this.listOfEvents.size();
    }

    @Override
    public Event getElementAt(int index) {
        return this.listOfEvents.get(index);
    }
    
    
}
