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
 * @author nilss
 */
public final class ParticipantListModel extends AbstractListModel<Operator>{

    private ArrayList<Operator> listOfParticipants = null;
    
    public ParticipantListModel() {
        listOfParticipants = new ArrayList();
    }

    public ParticipantListModel(ArrayList<Operator> attachments) {
        listOfParticipants = new ArrayList();
        addElement(attachments);
    }
    
    public void addElement(Operator user) {
        if (user != null) {
            this.listOfParticipants.add(user);
        }
    }

    public void addElement(ArrayList<Operator> users) {
        if (users != null) {
            this.listOfParticipants = users;
        }
    }
    
    @Override
    public int getSize() {
        return this.listOfParticipants.size();
    }

    @Override
    public Operator getElementAt(int index) {
        return this.listOfParticipants.get(index);
    }
    
}
