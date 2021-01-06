/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import classes.*;
import classes.Operator;
import java.io.File;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author joshua
 */
public class DatabaseHandler {

    private Connection con = null;

    public DatabaseHandler() {
        con = getConnection();
    }

    //PRIVATE FUNCTION SECTION 
    private void setOrganiserOfEvent(int userID, int eventID) {
        String sql = "INSERT INTO organiser  VALUES (?,?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, eventID);
            //System.out.println(stmt.toString());
            stmt.executeUpdate();
        } catch (SQLException ex) {

        }
    }

    private int getMaxEventID() {
        int max;
        String sql = "select max(E_eventID) as max from event";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                max = rs.getInt("max");
                return max;
            }
        } catch (SQLException ex) {

        }
        return 0;
    }

    private void setParticipantsOfEvent(ArrayList<Operator> participants, int eventID) {
        String sql = "INSERT INTO participant VALUES (?,?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (Operator participant : participants) {
                //   stmt.setInt(1, participant.getUserId());
                stmt.setInt(2, eventID);
                stmt.addBatch();
                //System.out.println(stmt.toString());
            }
            stmt.executeBatch();
        } catch (SQLException ex) {

        }
    }

    private static String listToString(ArrayList<Integer> numbers) {
        StringBuilder buffer = new StringBuilder();
        if (!numbers.isEmpty()) {
            for (Integer nextNumber : numbers) {
                buffer.append("").append(nextNumber).append("").append(",");
            }
            return buffer.toString().substring(0, buffer.length() - 1);
        }
        return null;
    }

    private ArrayList<Integer> getUsersEvents(int userID) {
        ArrayList<Integer> eventIDs = new ArrayList<>();
        String sql = "SELECT OE_eventID, OE_deleted FROM organiserevent WHERE OE_userID = ? AND OE_deleted = 0;";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    eventIDs.add(rs.getInt("OE_eventID"));
                }
            }
        } catch (SQLException ex) {

        }

        return eventIDs;

    }

    private Operator returnUser(ResultSet rs, String prefix) {
        Operator user = null;
        int userID = -1;
        String email = "", firstName = "", lastName = "", role = "";
        try {
            userID = rs.getInt(prefix + "_userID");
            role = rs.getString(prefix + "_role");
            email = rs.getString(prefix + "_email");
            firstName = rs.getString(prefix + "_firstName");
            lastName = rs.getString(prefix + "_lastName");
            user = AdminOrUser(role, userID, firstName, lastName, email);
        } catch (SQLException ex) {

        }
        return user;
    }

    private Event setEvent(ResultSet rs) {
        Operator orginator;
        String orgFirstName, orgLastName, orgEmail, orgRole; //Orginator
        int orgID, eventID;
        String eventName, eventLocation; //EventStrings
        LocalDateTime event, reminder;
        ArrayList<Operator> participants = null;
        ArrayList<File> files = null;
        int eventDuration;
        try {
            orgID = rs.getInt("ED_userID");
            orgRole = rs.getString("ED_role");
            orgFirstName = rs.getString("ED_firstName");
            orgLastName = rs.getString("ED_lastName");
            orgEmail = rs.getString("ED_email");
            orginator = AdminOrUser(orgRole, orgID, orgFirstName, orgLastName, orgEmail);
            eventID = rs.getInt("ED_eventID");
            eventName = rs.getString("ED_eventName");
            event = rs.getTimestamp("ED_eventDate").toLocalDateTime();
            eventDuration = rs.getInt("ED_eventDuration");
            eventLocation = rs.getString("ED_eventLocation");
            Event.Priority Priority = Event.Priority.valueOf(rs.getString("ED_priority"));
            reminder = rs.getTimestamp("ED_reminder").toLocalDateTime();
            Event.Notification notification = Event.Notification.valueOf(rs.getString("ED_notification"));
            ArrayList<Operator> participant;
            Event temp
                    = new Event(eventID, eventName, orginator, event, eventDuration, eventLocation, participants, files, Priority, notification, reminder);
            return temp;
        } catch (SQLException ex) {

        }
        return null;
    }

    private Operator AdminOrUser(String role, int userId, String firstName, String lastName, String email) {
        Operator toBeSpecified = null;
        
        if (role.equals(Operator.Role.ADMIN.toString())) {
            toBeSpecified = new Admin(userId, firstName, lastName, email);
            return toBeSpecified;
        } else {
            toBeSpecified = new User(userId, firstName, lastName, email);
            return toBeSpecified;
        }  
    }

    //PUBLIC FUNCTION SECTION 
    public static Connection getConnection() {
        final String USERNAME = "Admin";
        final String PASSWORD = "admin";
        String URL = "jdbc:mysql://localhost:3306/timescheduler?zeroDateTimeBehavior=convertToNull";
        //String URL = "jdbc:mysql://localhost:3306/timescheduler?zerodatetimebehavior=Converttonull";
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Verbindung erfolgreich");
            return con;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    //USER FUNCTIONS
    public boolean checkIfUserExists(String username, String password) {
        String sql = "SELECT * FROM user WHERE U_email = ? AND U_password = ? AND U_deleted = 0";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {

        }
        return false;
    }

    public boolean checkIfUserExists(String username) {
        String sql = "SELECT * FROM user WHERE U_email = ? AND U_deleted = 0";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {

        }
        return false;
    }

    public int insertNewUser(Operator user, String password) {
        int insertSuccessfull = -1;
        String sql = "INSERT INTO user (U_email, U_firstName, U_lastName, U_role, U_password) VALUES (?,?,?,?,?)";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getRole().toString());
            stmt.setString(5, password);
            insertSuccessfull = stmt.executeUpdate();
        } catch (SQLException ex) {

        }
        System.out.println("Nutzer Erfolgreich angelegt");
        return insertSuccessfull;
    }

    public Operator getUserByID(int userID) {
        String prefix = "U";
        String sql = "SELECT * FROM user WHERE U_UserID = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return returnUser(rs, prefix);
                }
            }
        } catch (SQLException ex) {

        }
        return null;
    }

    public ArrayList<Operator> getUserByLastName(String usersLastName) {
        String prefix = "U";
        ArrayList<Operator> allUser = new ArrayList<>();
        Operator toBeAdded;

        String sql = "SELECT * FROM user WHERE U_lastName = ? AND U_deleted = 0";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usersLastName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    allUser.add(returnUser(rs, prefix));
                }
            }
        } catch (SQLException ex) {
          //  Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allUser;
    }

    public ArrayList<Operator> getUserByFirstName(String usersFirstName) {
        String prefix = "U";
        ArrayList<Operator> allUser = new ArrayList<>();
        Operator toBeReturned;
        String sql = "SELECT * FROM user WHERE U_firstName = ? AND U_deleted = 0";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usersFirstName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    allUser.add(returnUser(rs, prefix));
                }

            }
        } catch (SQLException ex) {
           // Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allUser;
    }

    public Operator getUserByUsername(String username) {
        String prefix = "U";
        String sql = "SELECT * FROM user WHERE U_email = ? AND U_deleted = 0";
        Operator user = null;
        int userID = -1;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    return returnUser(rs, prefix);
                }
            }
        } catch (SQLException ex) {
            //Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public ArrayList<Operator> getAllUser() {
        String prefix = "U";
        ArrayList<Operator> allUser = new ArrayList<>();
        User user = null;
        String sql = "SELECT * FROM user WHERE U_deleted = 0";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    allUser.add(returnUser(rs, prefix));
                }
            }
        } catch (SQLException ex) {

        }

        return allUser;
    }

    public Operator getOrganiserByEventName(String eventName) {
        String prefix = "OE";
        String sql = "SELECT * FROM organiserevent WHERE OE_EventID = ? AND OE_deleted = 0";
        int userID;
        Operator organiser = null;
        String role, firstName, lastName, email;
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    organiser = returnUser(rs, prefix);
                }
                return organiser;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Kein Organisator für dieses Event");
        return null;
    }

    public Operator getOrganiserByID(int EventID) {
        String prefix = "OE";
        String sql = "SELECT * FROM organiserevent WHERE OE_EventID = ?  AND OE_deleted = 0";
        Operator organiser = null;
        int userID;
        String role, firstName, lastName, email;
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, EventID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    organiser = returnUser(rs, prefix);
                }
                return organiser;
            }
        } catch (SQLException ex) {

        }
        System.out.println("There's no organisator for thisppointment");
        return null;
    }

    public ArrayList<Operator> getParticipantsByID(int EventID) {
        String prefix = "EM";
        ArrayList<Operator> participants = new ArrayList<>();
        Operator participant;
        int userID;
        String role, firstName, lastName, email;
        String sql = "SELECT * from eventmembers WHERE EM_EventID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, EventID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    participant = returnUser(rs, prefix);
                    participants.add(participant);
                }
            }
        } catch (Exception ex) {

        }
        return participants;
    }

    public ArrayList<Operator> getParticipantsByEventName(String eventName) {
        String prefix = "EM";
        ArrayList<Operator> participants = new ArrayList<>();
        String sql = "SELECT * from eventmembers WHERE EM_eventName = ?";

        try ( PreparedStatement stmt = con.prepareCall(sql)) {
            stmt.setString(1, eventName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    participants.add(returnUser(rs, prefix));
                }
            }
        } catch (Exception ex) {

        }
        return participants;
    }

    public ArrayList<Operator> getAllAdmins() {
        String prefix = "U";
        ArrayList<Operator> allAdmins = new ArrayList<>();
        String sql = "SELECT * FROM user where U_role = 'Admin' AND U_deleted = 0";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    allAdmins.add(returnUser(rs, prefix));
                }
            }
        } catch (SQLException ex) {

        }

        return allAdmins;
    }

    public ArrayList<Operator> getAllOperator() {
        String prefix = "U";
        ArrayList<Operator> userAndAdmins = new ArrayList<>();
        String sql = "SELECT * FROM USER WHERE U_deleted = 0";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    userAndAdmins.add(returnUser(rs, prefix));
                }
            }
        } catch (SQLException ex) {

        }
        return userAndAdmins;
    }

    public int editUser(Operator toBeEdited) {
        int editSuccessfull = -1;
        String sqlUpdate
                = "UPDATE user SET U_email = ?,U_firstName =?, U_lastName = ?,U_role = ? WHERE U_userID = ?";
        try ( PreparedStatement stmt = con.prepareStatement(sqlUpdate)) {
            stmt.setString(1, toBeEdited.getEmail());
            stmt.setString(2, toBeEdited.getFirstName());
            stmt.setString(3, toBeEdited.getLastName());
            stmt.setString(4, toBeEdited.getRole().toString());
            stmt.setInt(5, toBeEdited.getUserId()); 
            editSuccessfull = stmt.executeUpdate();
        } catch (SQLException ex) {

        }
        System.out.println("User erfolgreich bearbeitet");
        return editSuccessfull;

    }

    public void deleteUser(Operator toBeDeleted) {
        String eventIDs = listToString(getUsersEvents(toBeDeleted.getUserId()));
        String deleteUser = "UPDATE user SET U_deleted = 1 WHERE U_userID = ?";
        String deleteUsersEvents = "UPDATE Event SET E_deleted = 1 WHERE E_EventID IN (" + eventIDs + ")";
        int deleteSuccessfull = -1;

        try (PreparedStatement stmt = con.prepareStatement(deleteUser)) {
            stmt.setInt(1, toBeDeleted.getUserId());
            System.out.println(stmt.toString());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            // Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (PreparedStatement stmt = con.prepareStatement(deleteUsersEvents)) {
            System.out.println(stmt.toString());
            stmt.executeUpdate();

        } catch (SQLException ex) {

        }
        if (deleteSuccessfull == 0) {
            System.out.println("User erfolgreich gelÃ¶scht");
        }
    }

    public ArrayList<Operator> getAllUsersLike(String toBeLookedFor) {
        ArrayList<Operator> users = new ArrayList<>();
        String sql = "SELECT * FROM USER WHERE U_firstName LIKE ? OR U_lastName LIKE ? OR U_email LIKE ? AND U_deleted = 0";
        char prefix = '%';
        char suffix = '%';
        Operator user = null;
        int userID = -1;
        String email, firstName, lastName, role;

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, prefix + toBeLookedFor + suffix);
            stmt.setString(2, prefix + toBeLookedFor + suffix);
            stmt.setString(3, prefix + toBeLookedFor + suffix);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    email = rs.getString("U_email");
                    firstName = rs.getString("U_firstName");
                    lastName = rs.getString("U_lastName");
                    role = rs.getString("U_role");
                    user = AdminOrUser(role, userID, firstName, lastName, email);
                    users.add(user);
                }
            }
        } catch (SQLException ex) {

        }

        if (users.isEmpty()) {
            System.out.println("No User with such criteria does exist");
            return null;
        } else {
            return users;
        }

    }

    //Appointment Methods
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> Events = new ArrayList<>();
        String sql = "SELECT * FROM eventDetails";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Events.add(setEvent(rs));
                }
            }
        } catch (SQLException ex) {

        }

        for (Event a : Events) {
            a.setParticipants(getParticipantsByID(a.getID()));
        }
        return Events;
    }

    public void addEvent(int userID, Event Event) {

        String sqlEvent
                = "INSERT INTO Event (E_eventName, E_eventDuration, E_eventDate, E_priority, E_eventLocation, E_eventStartTime, E_reminder, E_reminderTime, E_notification) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        try ( PreparedStatement stmt = con.prepareStatement(sqlEvent)) {
            stmt.setString(1, Event.getName());
            stmt.setInt(2, Event.getDuration());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(Event.getDate()));
            stmt.setString(4, Event.getPriority().toString());
            stmt.setString(5, Event.getLocation());
            stmt.setTimestamp(7, java.sql.Timestamp.valueOf(Event.getReminder()));
            stmt.setString(9, Event.getNotification().toString());
            //System.out.println(stmt.toString());
            stmt.executeUpdate();
        } catch (SQLException ex) {

        }
        setOrganiserOfEvent(Event.getOrganisator().getUserId(), getMaxEventID());
        setParticipantsOfEvent(Event.getParticipants(), getMaxEventID());
    }

    public void deleteEvent(int EventID) {
        String sql = "UPDATE Event SET E_deleted = 1 WHERE E_EventID = ?";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, EventID);
            //System.out.println(stmt.toString());
            stmt.executeUpdate();
        } catch (SQLException ex) {

        }
    }

    public ArrayList<Event> getEventsByUsername(String username) {
        ArrayList<Event> usersEvents = new ArrayList<>();
        String usersEventIDs = listToString(getEventIDsOfUser(username));
        String sql = "SELECT * FROM eventDetails WHERE ED_EventID IN (" + usersEventIDs + ") AND ED_deleted = 0";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usersEvents.add(setEvent(rs));
                }
            }
        } catch (SQLException ex) {

        }

        for (Event a : usersEvents) {
            a.setParticipants(getParticipantsByID(a.getID()));
        }

        return usersEvents;
    }

    public ArrayList<Event> getThisWeeksEventsByUsername(String username) {
        ArrayList<Event> usersEvents = new ArrayList<>();
        ArrayList<Integer> usersEventIDs = getEventIDsOfUser(username);

        String sql
                = "SELECT * FROM eventDetails WHERE ED_eventDate >= (SELECT DATE_ADD(CURDATE(), INTERVAL - WEEKDAY(CURDATE()) DAY)) AND ED_eventDate <= (SELECT date(now() + INTERVAL 6 - weekday(now()) DAY)) AND ED_email = ? AND ED_deleted = 0;";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Event temp = setEvent(rs);
                    usersEvents.add(temp);
                }
            }
        } catch (SQLException ex) {

        }

        if (!usersEvents.isEmpty()) {
            for (Event a : usersEvents) {
                a.setParticipants(getParticipantsByID(a.getID()));
            }
            return usersEvents;
        } else {
            System.out.println("User doesn't have any Events this week");
        }
        return null;
    }

    public Event getEventByUser(String userID, int EventID) {
        ArrayList<Integer> EventIDs = getEventIDsOfUser(userID);

        if (EventIDs.contains(EventID)) {
            return getEventById(EventID);
        } else {
            System.out.println("User doesn't take part in this Event or the Event doesn't exist.");
        }
        return null;
    }

    public ArrayList<Event> getAllEventByUser(int userID) {
        ArrayList<Event> Events = new ArrayList<>();
        String sql = "SELECT * FROM eventDetails WHERE ED_userID = ? ";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Events.add(setEvent(rs));
                }
            }
        } catch (SQLException ex) {

        }

        for (Event a : Events) {
            a.setParticipants(getParticipantsByID(a.getID()));
        }
        return Events;
    }

    public Event getEventById(int EventID) {
        Event toBeReturned = null;

        String sql = "SELECT * FROM eventDetails WHERE ED_EventID = ? AND ED_deleted = 0";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, EventID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    toBeReturned = setEvent(rs);
                }
            }
        } catch (SQLException ex) {

        }

        toBeReturned.setParticipants(getParticipantsByID(toBeReturned.getID()));
        return toBeReturned;
    }

    private ArrayList<Integer> getEventIDsOfUser(String username) {
        ArrayList<Integer> EventIDs = new ArrayList<>();
        String sql = "SELECT EM_EventID FROM eventmembers WHERE EM_email = ? AND EM_deleted = 0";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EventIDs.add(rs.getInt("EM_EventID"));
                }
            }
        } catch (SQLException ex) {

        }

        return EventIDs;

    }
}