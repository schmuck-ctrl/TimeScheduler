/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.time.*;
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
    private Operator host = null;
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
    
    public Event(int eventID, String eventName, Operator host, LocalDateTime event, int eventDuration, String eventLocation, ArrayList<Operator> participants, ArrayList<File> files, Priority Priority, Notification notification, LocalDateTime reminder) {
        this.setID(eventID);
        this.setName(eventName);
        this.setHost(host);
        this.setDate(event);
        this.setDuration(eventDuration);
        this.setLocation(eventLocation);
        this.setParticipants(participants);
        this.setAttachments(files);
        this.setPriority(Priority);
        this.setNotification(notification);
        this.setReminder(reminder);
    }
    
    public Event(String eventName, Operator host, LocalDateTime event, int eventDuration, String eventLocation, ArrayList<Operator> participants, ArrayList<File> files, Priority Priority, Notification notification) {
        this.setName(eventName);
        this.setHost(host);
        this.setDate(event);
        this.setDuration(eventDuration);
        this.setLocation(eventLocation);
        this.setParticipants(participants);
        this.setAttachments(files);
        this.setPriority(Priority);
        this.setNotification(notification);
        this.setReminder(calculateReminder());
    }
    
    public Event(int eventId, String eventName, Operator host, LocalDateTime event, int eventDuration, String eventLocation, ArrayList<Operator> participants, ArrayList<File> files, Priority Priority, Notification notification) {
        this.setID(eventId);
        this.setName(eventName);
        this.setHost(host);
        this.setDate(event);
        this.setDuration(eventDuration);
        this.setLocation(eventLocation);
        this.setParticipants(participants);
        this.setAttachments(files);
        this.setPriority(Priority);
        this.setNotification(notification);
        this.setReminder(calculateReminder());
    }
    
    private void setID(int eventID) {
        if(eventID >= 0)
            this.id = eventID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHost(Operator host) {
        this.host = host;
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

    public Operator getHost() {
        return this.host;
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
    
    private LocalDateTime calculateReminder() {
        
        switch(this.notification){
            case NONE: return this.date; 
            case ONE_HOUR: return this.date.minus(Duration.ofHours(1));
            case TEN_MINUTES: return this.date.minus(Duration.ofMinutes(10));
            case ONE_WEEK: return this.date.minus(Period.ofWeeks(1));
            case THREE_DAYS: return this.date.minus(Period.ofDays(3));
            default: return null;
        }
    }
    
    @Override
    public String toString() {
        return name;
    }
}
