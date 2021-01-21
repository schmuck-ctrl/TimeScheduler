/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventUtilities;

import classes.Attachment;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 * Default ListModel for the JList {@link forms.FrmEvent#liEventAttachments} in {@link forms.FrmEvent}.
 * 
 * @author Nils Schmuck
 */
public final class AttachmentListModel extends AbstractListModel<Attachment> {

    // <editor-fold defaultstate="collapsed" desc="Global variables">
    
    //The current list.
    private ArrayList<Attachment> listOfAttachments = null;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    
    /**
     * Constructs an empty list.
     */
    public AttachmentListModel() {
        listOfAttachments = new ArrayList();
    }

    /**
     * Constructs a list containing the elements of the specified collection, in the order they are returned by the collection's iterator.
     * 
     * @param attachments The collection whose elements are to be placed into this list.
     * @throws NullPointerException If the specified collection is null.
     */
    public AttachmentListModel(ArrayList<Attachment> attachments) {
        listOfAttachments = new ArrayList();
        addElement(attachments);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Appends the Attachment> element to the end of this list.
     * 
     * @param attachment The element to be added to the list.
     * @throws NullPointerException If the specified collection is null.
     */
    public void addElement(Attachment attachment) {
        if (attachment != null) {
            this.listOfAttachments.add(attachment);
        }
    }

    /**
     * Appends all of the elements in the Operator collection to the end of this
     * list, in the order that they are returned by the specified collection's
     * Iterator.
     * 
     * @param attachments The collection which elements added to this list.
     * @throws NullPointerException If the specified collection is null.
     */
    public void addElement(ArrayList<Attachment> attachments) {
        if (attachments != null) {
            for(int i = 0; i < attachments.size(); i++) {
                this.listOfAttachments.add(attachments.get(i));
            }
            
        }
    }

    /**
     * Removes from this list all of its elements.
     * The list will be empty after this call.
     *
     * @throws ClassCastException If the class of an element of this list is incompatible with the specified collection.
     * @throws NullPointerException If this list contains a null element and the specified collection does not permit null elements, or if the specified collection is null.
     */
    public void removeAll(){
        this.listOfAttachments.clear();
    }
    
    /**
     * Remove the element at the specified position in this list.
     * 
     * @param index The index of the element to be removed.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public void remove(int index) {
        this.listOfAttachments.remove(index);
    }
    
    /**
     * Returns the number of elements in this list.
     *
     * @return The number of elements in this list.
     */
    @Override
    public int getSize() {
        return this.listOfAttachments.size();
    }

    /**
     * Returns the element at the specified position in this list.
     * 
     * @param index Index of the element to return.
     * @return The element at the specified position in this list.
     */
    @Override
    public Attachment getElementAt(int index) {
        return this.listOfAttachments.get(index);
    }
    // </editor-fold>    
}
