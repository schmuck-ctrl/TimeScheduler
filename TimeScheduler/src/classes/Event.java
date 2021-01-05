/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author nilss
 */
public class Event {

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum Notification {
        NONE,
        TEN_MINUTES,
        ONE_HOUR,
        THREE_DAYS,
        ONE_WEEK
    }

    private int id = -1;
    private String name = null;
    private Operator orginator = null;
    private LocalDateTime date = null;
    private int duration = 0;
    private String location = null;
    private ArrayList<Operator> participants = null;
    private ArrayList<File> attachments = null;
    private Priority priority = null;
    private Notification notification = Notification.NONE;
    private LocalDateTime reminder = null;
    
    public Event(){
        
    }
    
    private void setID(int eventID) {
        if(eventID >= 0)
            this.id = eventID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrginator(Operator orginator) {
        this.orginator = orginator;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setParticipants(ArrayList<Operator> participants) {
        this.participants = participants;
    }

    public void setAttachments(ArrayList<File> attachments) {
        this.attachments = attachments;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void setReminder(LocalDateTime reminder) {
        this.reminder = reminder;
    } 
    
    
    public int getID(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Operator getOrginator() {
        return this.orginator;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public int getDuration() {
        return this.duration;
    }

    public String getLocation() {
        return this.location;
    }

    public ArrayList<Operator> getParticipants() {
        return this.participants;
    }

    public ArrayList<File> getAttachments() {
        return this.attachments;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public Notification getNotification() {
        return this.notification;
    }

    public LocalDateTime getReminder() {
        return this.reminder;
    }
    
    
}
