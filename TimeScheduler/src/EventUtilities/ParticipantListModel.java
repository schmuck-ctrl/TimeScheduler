/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventUtilities;

import classes.Operator;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Nils Schmuck
 */
public final class ParticipantListModel extends AbstractListModel<Operator> {

    // <editor-fold defaultstate="collapsed" desc="Global variables">
    
    private ArrayList<Operator> listOfParticipants = null;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Constructs an empty list.
     */
    public ParticipantListModel() {
        listOfParticipants = new ArrayList();
    }

    /**
     * Constructs a list containing the elements of the specified collection, in the order they are returned by the collection's iterator.
     * 
     * @param attachments The collection whose elements are to be placed into this list.
     * @throws NullPointerException If the specified collection is null.
     */
    public ParticipantListModel(ArrayList<Operator> attachments) {
        listOfParticipants = new ArrayList();
        addElement(attachments);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Appends the Operator element to the end of this list.
     *
     * @param user The element to be added to the list.
     * @throws NullPointerException If the specified element is null
     */
    public void addElement(Operator user) {
        if (user != null) {
            this.listOfParticipants.add(user);
        }
    }

    /**
     * Appends all of the elements in the Operator collection to the end of this
     * list, in the order that they are returned by the specified collection's
     * Iterator.
     *
     * @param users The collection which elements added to this list.
     * @throws NullPointerException If the specified collection is null.
     */
    public void addElement(ArrayList<Operator> users) {
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                this.listOfParticipants.add(users.get(i));
            }

        }
    }

    /**
     * Removes from this list all of its elements.
     *
     * @throws ClassCastException If the class of an element of this list is incompatible with the specified collection.
     * @throws NullPointerException If this list contains a null element and the specified collection does not permit null elements, or if the specified collection is null.
     */
    public void removeAll() {
        for (int i = 0; i < this.listOfParticipants.size(); i++) {
            this.listOfParticipants.remove(i);
        }

    }

    /**
     * Returns the number of elements in this list.
     *
     * @return The number of elements in this list.
     */
    @Override
    public int getSize() {
        return this.listOfParticipants.size();
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index Index of the element to return.
     * @return The element at the specified position in this list.
     */
    @Override
    public Operator getElementAt(int index) {
        return this.listOfParticipants.get(index);
    }
    
    // </editor-fold>

}
