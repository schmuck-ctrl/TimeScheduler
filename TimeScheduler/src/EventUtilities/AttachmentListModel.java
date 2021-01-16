/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventUtilities;

import java.io.File;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 * Default ListModel for the JList {@link forms.FrmEvent#liEventAttachments} in {@link forms.FrmEvent}.
 * 
 * @author Nils Schmuck
 */
public final class AttachmentListModel extends AbstractListModel<File> {

    // <editor-fold defaultstate="collapsed" desc="Global variables">
    
    //The current list.
    private ArrayList<File> listOfAttachments = null;

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
    public AttachmentListModel(ArrayList<File> attachments) {
        listOfAttachments = new ArrayList();
        addElement(attachments);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Appends the File element to the end of this list.
     * 
     * @param attachment The collection whose elements are to be placed into this list.
     * @throws NullPointerException If the specified collection is null.
     */
    public void addElement(File attachment) {
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
    public void addElement(ArrayList<File> attachments) {
        if (attachments != null) {
            for(int i = 0; i < attachments.size(); i++) {
                this.listOfAttachments.add(attachments.get(i));
            }
            
        }
    }

    /**
     * Removes from this list all of its elements.
     *
     * @throws ClassCastException If the class of an element of this list is incompatible with the specified collection.
     * @throws NullPointerException If this list contains a null element and the specified collection does not permit null elements, or if the specified collection is null.
     */
    public void removeAll(){
        for(int i = 0; i < this.listOfAttachments.size(); i++) {
            this.listOfAttachments.remove(i);
        }
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
    public File getElementAt(int index) {
        return this.listOfAttachments.get(index);
    }
    // </editor-fold>    
}
