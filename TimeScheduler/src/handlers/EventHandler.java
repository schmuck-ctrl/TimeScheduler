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

        ArrayList<Event> eventList = null;

        return eventList;
    }

    public Event getEvent(int userID, int eventID) {
        Event event = null;

        return event;
    }

    public ArrayList<Event> getEventsOfWeek(int userID, LocalDate monday, LocalDate sunday) {

        ArrayList<Event> eventList = null;

        return eventList;
    }

    public void addEvent(int userID, Event event) {

    }

    public void editEvent(int eventID, Event event) {

    }

    public void deleteEvent(int eventID) {

    }
}
