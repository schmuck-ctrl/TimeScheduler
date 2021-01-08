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
 *
 * @author nilss
 */
public class EventHandler {

    private DatabaseHandler dbHandler = null;

    public EventHandler() {
        dbHandler = new DatabaseHandler();
    }

    public ArrayList<Event> getAllEvents(int userID) {

        ArrayList<Event> eventList = dbHandler.getAllEvents();

        return eventList;
    }

    public Event getEventByUser(int userId, int eventID) {
        Event event = dbHandler.getEventByUser(userId, eventID);

        return event;
    }

    public Event getEvent(int eventID) {
        Event event = dbHandler.getEventById(eventID);
        
        return event;
    }
    
    public ArrayList<Event> getEventsOfMonth(int userId, int monthValue) {
        ArrayList<Event> eventList = dbHandler.getThisMonthsEventsByUserID(userId);
        
        return eventList;
    }
    
    public ArrayList<Event> getEventsOfWeek(int userId) {

        ArrayList<Event> eventList = dbHandler.getThisWeeksEventsByUserID(userId);

        return eventList;
    }
    
    public ArrayList<Event> getEventsOfDay(int userId, LocalDate day) {
        ArrayList<Event> eventList = dbHandler.getUsersEventsOfCertainDay(userId, day);

        return eventList;
    }

    public void addEvent(int userID, Event event) {
        dbHandler.addEvent(userID, event);
    }

    public void editEvent(Event event) {
        //dbHandler.
    }

    public void deleteEvent(int eventID) {
        dbHandler.deleteEvent(eventID);
    }
}
