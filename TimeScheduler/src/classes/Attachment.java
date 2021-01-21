/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author Nils Schmuck
 */
public class Attachment {

    private String fileName = null;
    public int attachmentID = -1;

    public Attachment(String fileName, int id) {
        setAttachmentID(id);
        setFileName(fileName);
    }

    private void setAttachmentID(int attachmentID) {
        if (attachmentID > -1) {
            this.attachmentID = attachmentID;
        }
    }

    private void setFileName(String fileName) {
        if (fileName != null && !fileName.isBlank()) {
            this.fileName = fileName;
        }
    }
    
    public String getFileName() {
        return this.fileName;
    }

    public int getAttachmentID() {
        return attachmentID;
    }

}
