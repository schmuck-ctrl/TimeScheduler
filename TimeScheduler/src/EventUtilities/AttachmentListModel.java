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
 *
 * @author nilss
 */
public final class AttachmentListModel extends AbstractListModel<File> {

    private ArrayList<File> listOfAttachments = null;

    public AttachmentListModel() {
        listOfAttachments = new ArrayList();
    }

    public AttachmentListModel(ArrayList<File> attachments) {
        listOfAttachments = new ArrayList();
        addElement(attachments);
    }

    public void addElement(File attachment) {
        if (attachment != null) {
            this.listOfAttachments.add(attachment);
        }
    }

    public void addElement(ArrayList<File> attachments) {
        if (attachments != null) {
            this.listOfAttachments = attachments;
        }
    }

    @Override
    public int getSize() {
        return this.listOfAttachments.size();
    }

    @Override
    public File getElementAt(int index) {
        return this.listOfAttachments.get(index);
    }

}
