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
 * Default ListModel for the JList {@link forms.FrmEventsOfDay#liEventsOfDay} in {@link forms.FrmEventsOfDay}.
 * 
 * @author Nils Schmuck
 */
public final class EventsOfDayListModel extends AbstractListModel<Event>{

    // <editor-fold defaultstate="collapsed" desc="Global variables">
    
    //The current list.
    private ArrayList<Event> listOfEvents = null;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Constructs an empty list.
     */
    public EventsOfDayListModel() {
        this.listOfEvents = new ArrayList();
    }
    
    /**
     * Constructs a list containing the elements of the specified collection, in the order they are returned by the collection's iterator.
     * 
     * @param events The collection whose elements are to be placed into this list.
     * @throws NullPointerException If the specified collection is null.
     */
    public EventsOfDayListModel(ArrayList<Event> events) {
        this.listOfEvents = new ArrayList();
        addElement(events);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Appends the Event element to the end of this list.
     * 
     * @param event The collection whose elements are to be placed into this list.
     * @throws NullPointerException If the specified collection is null.
     */
    public void addElement(Event event) {
        if(event != null)
            this.listOfEvents.add(event);
    }
    
    /**
     * Appends all of the elements in the Operator collection to the end of this
     * list, in the order that they are returned by the specified collection's
     * Iterator.
     * 
     * @param events The collection which elements added to this list.
     * @throws NullPointerException If the specified collection is null.
     */
    public void addElement(ArrayList<Event> events) {
        if(events != null)
            this.listOfEvents = events;
    }
    
    /**
     * Removes from this list all of its elements.
     *
     * @throws ClassCastException If the class of an element of this list is incompatible with the specified collection.
     * @throws NullPointerException If this list contains a null element and the specified collection does not permit null elements, or if the specified collection is null.
     */
    public void removeAll(){
        for(int i = 0; i < this.listOfEvents.size(); i++) {
            this.listOfEvents.remove(i);
        }
    }
    
    /**
     * Returns the number of elements in this list.
     *
     * @return The number of elements in this list.
     */
    @Override
    public int getSize() {
        return this.listOfEvents.size();
    }

    /**
     * Returns the element at the specified position in this list.
     * 
     * @param index Index of the element to return.
     * @return The element at the specified position in this list.
     */
    @Override
    public Event getElementAt(int index) {
        return this.listOfEvents.get(index);
    }
    
    // </editor-fold>   
}
