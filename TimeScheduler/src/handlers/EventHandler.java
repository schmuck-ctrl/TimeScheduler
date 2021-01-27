/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import classes.Event;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Manages the database access especially for the events.
 *
 * @author Nils Schmuck
 */
public class EventHandler {

    // <editor-fold defaultstate="collapsed" desc="Global Variables">
    /**
     * The object for the database access
     */
    private DatabaseHandler dbHandler = null;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructs a new event handler and initialized the object for the
     * database access.
     */
    public EventHandler() {
        dbHandler = new DatabaseHandler();

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Gets all events of the specified operator.
     *
     * @param userID The id of the specified operator.
     * @return A collection with all events
     */
    public ArrayList<Event> getAllEvents(int userID) {

        ArrayList<Event> eventList = dbHandler.getEventsByUserID(userID);

        return eventList;
    }

    /**
     * Gets a specified event from the specified operator.
     *
     * @param userId The id of the operator.
     * @param eventID The id of the event.
     * @return The specified event.
     */
    public Event getEventByUser(int userId, int eventID) {
        Event event = dbHandler.getEventByUser(userId, eventID);

        return event;
    }

    /**
     * Gets a specified event.
     *
     * @param eventID
     * @return The id of the event.
     */
    public Event getEvent(int eventID) {
        Event event = dbHandler.getEventById(eventID);

        return event;
    }

    /**
     * Gets all events of the specified operator by the given month.
     *
     * @param userId The id of the operator.
     * @param monthValue The value of the month. (1 = January, ..., 12 =
     * December).
     * @return A collection of the events by the value of the month.
     */
    public ArrayList<Event> getEventsOfMonth(int userId, int monthValue) {
        ArrayList<Event> eventList = dbHandler.getUsersEventsOfACertainMonth(userId, monthValue);

        return eventList;
    }

    /**
     * Gets all events of the specified operator for the current month.
     *
     * @param userId The id of the operator.
     * @return A collection of the events for the current month.
     */
    public ArrayList<Event> getEventsOfCurrentMonth(int userId) {
        ArrayList<Event> eventList = dbHandler.getThisMonthsEventsByUserID(userId);

        return eventList;
    }

    /**
     * Gets all events of the specified operator by the gevin start date and end
     * date. The start date and end date are included.
     *
     * @param userId The id of the operator.
     * @param start The start date
     * @param end The end date
     * @return A collection of the events by the given period.
     */
    public ArrayList<Event> getEventsOfPeriod(int userId, LocalDate start, LocalDate end) {
        ArrayList<Event> eventList = dbHandler.getEventsOfPeriod(userId, start, end);

        return eventList;
    }

    /**
     * Gets all events of the specified operator for the current week.
     *
     * @param userId The id of the operator.
     * @return A collection of the events for the current month.
     */
    public ArrayList<Event> getEventsOfWeek(int userId) {

        ArrayList<Event> eventList = dbHandler.getThisWeeksEventsByUserID(userId);

        return eventList;
    }

    /**
     * Gets all events of the specified operator by the given date.
     *
     * @param userId The id of the operator.
     * @param day The date of the day.
     * @return A collection of the evnts for the given date.
     */
    public ArrayList<Event> getEventsOfDay(int userId, LocalDate day) {
        ArrayList<Event> eventList = dbHandler.getUsersEventsOfCertainDay(userId, day);

        return eventList;
    }

    /**
     * Creates a new event in the database. In addition an email is send to this
     * event participant about the creation.
     *
     * @param event The event that is created.
     */
    public void addEvent(Event event) {
        dbHandler.createNewEvent(event);
//        EmailHandler eHandler = new EmailHandler(EmailHandler.MailOperation.CREATE_EVENT, event);
//        new Thread(eHandler).start();

    }

    /**
     * Changes the configurations of a event. In addition an email is send to
     * this event participant about the modifications.
     *
     * @param event The event that is changed.
     */
    public void editEvent(Event event) {
        dbHandler.editEvent(event);
//        EmailHandler eHandler = new EmailHandler(EmailHandler.MailOperation.EDIT_EVENT, event);
//        new Thread(eHandler).start();
    }

    /**
     * Delete the specified event. In addition an email is send to this event
     * participant about the deletion.
     *
     * @param eventID The id of the event that is deleted
     */
    public void deleteEvent(int eventID) {
//        EmailHandler eHandler = new EmailHandler(EmailHandler.MailOperation.DELETE_EVENT,dbHandler.getEventById(eventID));
//        new Thread(eHandler).start();
        dbHandler.deleteEvent(eventID);

    }
    // </editor-fold>
}
