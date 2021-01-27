/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import handlers.DatabaseHandler;

/**
 * Represent an attachment.
 *
 * @author Nils Schmuck
 */
public class Attachment extends java.io.File {
    // <editor-fold defaultstate="collapsed" desc="Attributes">

    /**
     * The name and file extension of the attachment.
     */
    private String fileName = null;

    /**
     * The id of the attachment.
     */
    public int attachmentID = -1;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructs a new attachment. This constructor is used to create a new
     * attachment or rather to add a new attachment to an event. In addition,
     * the file name is set.
     *
     * @param file
     * @see Attachment#setFileName(java.lang.String)
     */
    public Attachment(java.io.File file) {
        super(file.getPath());
        setFileName(file.getName());
    }

    /**
     * Constructs a new attachment. WARNING: There is no file behind this
     * attachment. You need to call {@link Attachment#getByteStream()} to get
     * the byte stream of the file from the database and from this a file must
     * be created! This constructor is used to create an attachment that already
     * exists. In addtition, the filename and the file id is set.
     *
     * @param fileName The name and extension of the file.
     * @param id The id of the file.
     * @see Attachment#setFileName(java.lang.String)
     * @see Attachment#setAttachmentID(int)
     */
    public Attachment(String fileName, int id) {
        super("");
        setAttachmentID(id);
        setFileName(fileName);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Setter">
    /**
     * Sets the value to this {@link Attachment#attachmentID}.
     *
     * @param attachmentID The id of the attachment.
     */
    private void setAttachmentID(int attachmentID) {
        if (attachmentID > -1) {
            this.attachmentID = attachmentID;
        }
    }

    /**
     * Sets the value to this {@link Attachment#fileName}.
     *
     * @param fileName The name and extension of the attachment.
     */
    private void setFileName(String fileName) {
        if (fileName != null && !fileName.isBlank()) {
            this.fileName = fileName;
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Getter">
    /**
     * Gets the {@link Attachment#fileName} of this attachment.
     *
     * @return The attachment name and extension of the attachment.
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Gets the {@link Attachment#attachmentID} of this attachment.
     *
     * @return The id of the attachment.
     */
    public int getAttachmentID() {
        return attachmentID;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Gets the byte stream of this attachment from the database.
     *
     * @return The byte stream of the attachment.
     */
    public byte[] getByteStream() {
        handlers.DatabaseHandler dbHandler = new DatabaseHandler();
        byte[] buffer = dbHandler.getDocument(this.attachmentID);

        return buffer;
    }

    /**
     * Gets the {@link Attachment#fileName} of this attachment.
     *
     * @return The attachment name and extension of the attachment.
     */
    @Override
    public String toString() {
        return this.fileName;
    }

    /**
     * Checks if the specified object is equal to an attachment.
     *
     * @param anObject The object that is beeing compared
     * @return true if the object equals an attachment, false if the object is
     * not equal.
     */
    @Override
    public boolean equals(Object anObject) {
        if (!(anObject instanceof Attachment)) {
            return false;
        }
        Attachment otherMember = (Attachment) anObject;
        return otherMember.getAttachmentID() == (getAttachmentID());
    }
    // </editor-fold>
}
