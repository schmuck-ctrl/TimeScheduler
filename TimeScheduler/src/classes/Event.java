/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.time.*;
import java.util.ArrayList;

/**
 * Represent an event.
 *
 * @author Nils Schmuck
 */
public class Event {

    // <editor-fold defaultstate="collapsed" desc="Enums">
    /**
     * The diffrerent priorities that an event can assume.
     */
    public enum Priority {

        /**
         * Low priority
         */
        LOW,
        /**
         * Medium priority
         */
        MEDIUM,
        /**
         * High priority
         */
        HIGH
    }

    /**
     * The different notification types of an event.
     */
    public enum Notification {

        /**
         * No notification.
         */
        NONE,
        /**
         * Notify 10 minutes before start.
         */
        TEN_MINUTES,
        /**
         * Notify 1 hour before start.
         */
        ONE_HOUR,
        /**
         * Notify 3 days before start.
         */
        THREE_DAYS,
        /**
         * Notify 1 week before start.
         */
        ONE_WEEK
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Attributes">
    /**
     * The id of the event.
     */
    private int id = -1;
    /**
     * The name of the event.
     */
    private String name = null;
    /**
     * The creator of the event.
     */
    private Operator host = null;
    /**
     * The date and time of the event.
     */
    private LocalDateTime date = null;
    /**
     * The duration in minutes of the event.
     */
    private int duration = 0;
    /**
     * The location of the event.
     */
    private String location = null;
    /**
     * The participants of the event.
     */
    private ArrayList<Operator> participants = null;
    /**
     * The attachments of the event.
     */
    private ArrayList<Attachment> attachments = null;
    /**
     * The priority of the event.
     */
    private Priority priority = null;
    /**
     * The notification type of the event.
     */
    private Notification notification = Notification.NONE;
    /**
     * The reminder date and time of the event.
     */
    private LocalDateTime reminder = null;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructs an empty Event.
     */
    public Event() {

    }

    /**
     * Constructs a new event. This constrcutor is used to create a new event.
     * In addition, the reminder is calculated based on the notification type.
     *
     * @param eventName The name of the event.
     * @param host The creator of the event.
     * @param date The date and time of the event.
     * @param eventDuration The duration in minutes of the event.
     * @param eventLocation The location of the event.
     * @param participants The participants of the event.
     * @param files The attachments of the event.
     * @param Priority The priority of the event.
     * @param notification The notification type of the event.
     */
    public Event(String eventName, Operator host, LocalDateTime date, int eventDuration, String eventLocation, ArrayList<Operator> participants, ArrayList<Attachment> files, Priority Priority, Notification notification) {
        this.setName(eventName);
        this.setHost(host);
        this.setDate(date);
        this.setDuration(eventDuration);
        this.setLocation(eventLocation);
        this.setParticipants(participants);
        this.setAttachments(files);
        this.setPriority(Priority);
        this.setNotification(notification);
        this.setReminder(calculateReminder());
    }

    /**
     * Constructs a new event. This constructor is used to create an event that
     * already exists. In addition, the reminder is calculated based on the
     * notification type.
     *
     * @param eventId The id of the event.
     * @param eventName The name of the event.
     * @param host The creator of the event.
     * @param date The date and time of the event.
     * @param eventDuration The duration in minutes of the event.
     * @param eventLocation The location of the event.
     * @param participants The participants of the event.
     * @param files The attachments of the event.
     * @param Priority The priority of the event.
     * @param notification The notification type of the event.
     */
    public Event(int eventId, String eventName, Operator host, LocalDateTime date, int eventDuration, String eventLocation, ArrayList<Operator> participants, ArrayList<Attachment> files, Priority Priority, Notification notification) {
        this.setID(eventId);
        this.setName(eventName);
        this.setHost(host);
        this.setDate(date);
        this.setDuration(eventDuration);
        this.setLocation(eventLocation);
        this.setParticipants(participants);
        this.setAttachments(files);
        this.setPriority(Priority);
        this.setNotification(notification);
        this.setReminder(calculateReminder());
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Setter">
    /**
     * Sets the value to this {@link Event#id}.
     *
     * @param eventID The id of this event.
     */
    private void setID(int eventID) {
        if (eventID >= 0) {
            this.id = eventID;
        }
    }

    /**
     * Sets the value to this {@link Event#name}.
     *
     * @param name The name of this event.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the value to this {@link Event#host}.
     *
     * @param host The creator of this event.
     */
    public void setHost(Operator host) {
        this.host = host;
    }

    /**
     * Sets the value to this {@link Event#date}.
     *
     * @param date The date and time of this event.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Sets the value to this {@link Event#duration}.
     *
     * @param duration The duration in minutes of this event.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Sets the value to this {@link Event#location}.
     *
     * @param location The location of this events.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the value to this {@link Event#participants}.
     *
     * @param participants The participants of this event.
     */
    public void setParticipants(ArrayList<Operator> participants) {
        this.participants = participants;
    }

    /**
     * Sets the value to this {@link Event#attachments}.
     *
     * @param attachments The attachments of this event.
     */
    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * Sets the value to this {@link Event#priority}.
     *
     * @see Priority
     * @param priority The priority of this event
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Sets the value to this {@link Event#notification}.
     *
     * @see Notification
     * @param notification The notification type of this event.
     */
    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    /**
     * Sets the value to this {@link Event#reminder}.
     *
     * @param reminder The reminder notification of this event.
     */
    public void setReminder(LocalDateTime reminder) {
        this.reminder = reminder;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Getter">
    /**
     * Gets the {@link Event#id} of this event.
     *
     * @return The id of the event.
     */
    public int getID() {
        return this.id;
    }

    /**
     * Gets the {@link Event#name} of this event.
     *
     * @return The name of the event.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the {@link Event#host} of this event.
     *
     * @return The creator of the event.
     */
    public Operator getHost() {
        return this.host;
    }

    /**
     * Gets the {@link Event#date} of this event.
     *
     * @return The date and time of the event.
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Gets the {@link Event#duration} of this event.
     *
     * @return The duration of the event.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Gets the {@link Event#location} of this event.
     *
     * @return The location of the event.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Gets the {@link Event#participants} of this event.
     *
     * @return The participants of the event.
     */
    public ArrayList<Operator> getParticipants() {
        return this.participants;
    }

    /**
     * Gets the {@link Event#attachments} of this event.
     *
     * @return The attachments of the event.
     */
    public ArrayList<Attachment> getAttachments() {
        return this.attachments;
    }

    /**
     * Gets the {@link Event#priority} of this event.
     *
     * @see Priority
     * @return The priority of the event.
     */
    public Priority getPriority() {
        return this.priority;
    }

    /**
     * Gets the {@link Event#notification} of this event.
     *
     * @see Notification
     * @return The notification type of the event.
     */
    public Notification getNotification() {
        return this.notification;
    }

    /**
     * Gets the {@link Event#reminder} of this event.
     *
     * @return The reminder date and time of the event.
     */
    public LocalDateTime getReminder() {
        return this.reminder;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Calculate the reminder date and time of this event.
     *
     * @return The date and time of the reminder.
     */
    private LocalDateTime calculateReminder() {

        switch (this.notification) {
            case NONE:
                return this.date;
            case ONE_HOUR:
                return this.date.minus(Duration.ofHours(1));
            case TEN_MINUTES:
                return this.date.minus(Duration.ofMinutes(10));
            case ONE_WEEK:
                return this.date.minus(Period.ofWeeks(1));
            case THREE_DAYS:
                return this.date.minus(Period.ofDays(3));
            default:
                return null;
        }
    }

    /**
     * Gets the name of this event.
     *
     * @return The name of the event.
     */
    @Override
    public String toString() {
        return name;
    }

    // </editor-fold>
}
