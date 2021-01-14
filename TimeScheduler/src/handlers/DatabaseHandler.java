/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import classes.*;
import classes.Operator;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author joshua
 */
public class DatabaseHandler {

    /*public static void main(String[] args) {
        DatabaseHandler db = new DatabaseHandler();
        //Event evt = db.getEventById(5)
        ArrayList<Event> evt = db.getNotNotified(1);
        for(Event ev : evt)
        System.out.println(ev.getID());
    }*/
    private Connection con = null;

    public DatabaseHandler() {
        con = getConnection();
    }

    //PRIVATE FUNCTION SECTION 
    private ArrayList<Integer> getEventIDsNotNotified(int userID) {
        ArrayList<Integer> eventIDs = new ArrayList<>();
        String notNotifiedIds = "SELECT P_eventID FROM participant WHERE P_userID = ? AND P_notified = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(notNotifiedIds)) {
            stmt.setInt(1, userID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    eventIDs.add(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            System.out.println("not Notified:" + ex.getMessage());
        }
        return eventIDs;
    }

    private void addFiles(ArrayList<File> toBeAdded, int eventID) {
        String addFile = "INSERT INTO file (F_file,F_eventID) VALUES(?,?)";

        try ( PreparedStatement stmt = con.prepareStatement(addFile)) {
            for (File f : toBeAdded) {
                FileInputStream file = new FileInputStream(f);
                stmt.setBinaryStream(1, file);
                stmt.setInt(2, eventID);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception ex) {
            System.out.println("add Files: " + ex.getMessage());
        }
    }

    private void addParticipants(ArrayList<Operator> toBeAdded, int eventID) {
        String addParticipant = "INSERT INTO participant (P_userID,P_eventID) VALUES(?,?)";

        try ( PreparedStatement stmt = con.prepareStatement(addParticipant)) {
            for (Operator participant : toBeAdded) {
                stmt.setInt(1, participant.getUserId());
                stmt.setInt(2, eventID);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException ex) {
            System.out.println("add participants: " + ex.getMessage());
        }
    }

    private void deleteParticipants(int eventID) {

        String deleteParticipant = "DELETE FROM participant WHERE P_eventID = ?;";
        //int update_successfull = -1;
        try ( PreparedStatement stmt = con.prepareStatement(deleteParticipant)) {
            stmt.setInt(1, eventID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("delete parti: " + ex.getMessage());
        }
        //return update_successfull;
    }

    private void deleteFiles(int eventID) {

        String deleteFiles = "DELETE FROM file WHERE P_eventID = ?;";
        //int update_successfull = -1;
        try ( PreparedStatement stmt = con.prepareStatement(deleteFiles)) {
            stmt.setInt(1, eventID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("delete parti: " + ex.getMessage());
        }
        //return update_successfull;
    }

    private void setOrganiserOfEvent(int userID, int eventID) {
        String sql = "INSERT INTO organiser (O_userID,O_eventID)  VALUES (?,?);";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, eventID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private int getMaxEventID() {
        int max;
        String sql = "select max(E_eventID) as max from event;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            try ( ResultSet rs = stmt.executeQuery()) {
                rs.next();
                max = rs.getInt("max");
                return max;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return -1;
    }

    private void insertParticipantsOfEvent(ArrayList<Operator> participants, int eventID) {
        String sql = "INSERT INTO participant (P_userID,P_eventID) VALUES (?,?);";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            for (Operator participant : participants) {
                stmt.setInt(1, participant.getUserId());
                stmt.setInt(2, eventID);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void fillEventsWithParticipants(ArrayList<Event> events) {
        if (!events.isEmpty()) {
            events.forEach(temp -> {
                temp.setParticipants(selectParticipantsByID(temp.getID()));
            });
        }
    }

    private static String getParticipantsID(ArrayList<Operator> participants) {
        StringBuilder buffer = new StringBuilder();
        if (!participants.isEmpty()) {
            for (Operator participant : participants) {
                buffer.append("").append(participant.getUserId()).append("").append(",");
            }
            return buffer.toString().substring(0, buffer.length() - 1);
        }
        return null;
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

    private ArrayList<Integer> getOrganiserEventIDs(int userID) {
        ArrayList<Integer> eventIDs = new ArrayList<>();
        String sql = "SELECT OE_eventID, OE_deleted FROM organiserevent WHERE OE_userID = ? AND OE_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    eventIDs.add(rs.getInt("OE_eventID"));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
            System.out.println(ex.getMessage());
        }
        return user;
    }

    private Event getEvent(ResultSet rs) {
        Operator organisator;
        String orgFirstName, orgLastName, orgEmail, orgRole; //Organisator
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
            organisator = AdminOrUser(orgRole, orgID, orgFirstName, orgLastName, orgEmail);
            eventID = rs.getInt("ED_eventID");
            eventName = rs.getString("ED_eventName");
            event = rs.getTimestamp("ED_eventDate").toLocalDateTime();
            eventDuration = rs.getInt("ED_eventDuration");
            eventLocation = rs.getString("ED_eventLocation");
            Event.Priority Priority = Event.Priority.valueOf(rs.getString("ED_priority"));
            reminder = rs.getTimestamp("ED_reminder").toLocalDateTime();
            Event.Notification notification = Event.Notification.valueOf(rs.getString("ED_notification"));
            //ArrayList<Operator> participant;
            Event temp
                    = new Event(eventID, eventName, organisator, event, eventDuration, eventLocation, participants, files, Priority, notification, reminder);
            return temp;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
        String URL = "jdbc:mysql://localhost:3306/timescheduler?zeroDateTimeBehavior=convertToNull&serverTimezone=Europe/Berlin";
        //String URL = "jdbc:mysql://localhost:3306/timescheduler?zerodatetimebehavior=Converttonull";
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return con;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    //USER FUNCTIONS
    public void setUserNotified(int eventID, int userID) {
        String notify = "UPDATE participant SET P_notified = 1 WHERE P_userID = ? AND P_eventID = ?;";

        try ( PreparedStatement stmt = con.prepareStatement(notify)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, eventID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("setUserNotified" + ex.getMessage());
        }

    }

    public ArrayList<Event> getNotNotifiedEventsFromUser(int userID) {
        ArrayList<Integer> eventIDs = getEventIDsNotNotified(userID);
        String notNotified = "SELECT * FROM eventdetails WHERE ED_eventID IN(" + listToString(eventIDs) + ");";
        ArrayList<Event> events = new ArrayList<>();
        try ( PreparedStatement stmt = con.prepareStatement(notNotified)) {

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    events.add(getEvent(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println("getEventsNotNotified" + ex.getMessage());
        }
        return events;
    }

    public boolean checkIfUserExists(String username, String password) {
        String sql = "SELECT * FROM user WHERE U_email = ? AND U_password = ? AND U_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try ( ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean checkIfUserExists(String username) {
        String sql = "SELECT * FROM user WHERE U_email = ? AND U_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try ( ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public int insertNewUser(Operator user, String password) {
        int insertSuccessfull = -1;
        String sql = "INSERT INTO user (U_email, U_firstName, U_lastName, U_role, U_password) VALUES (?,?,?,?,?);";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getRole().toString());
            stmt.setString(5, password);
            insertSuccessfull = stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Nutzer Erfolgreich angelegt");
        return insertSuccessfull;
    }

    public Operator getUserByID(int userID) {
        String prefix = "U";
        String sql = "SELECT * FROM user WHERE U_UserID = ? AND U_deleted = 0;";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return returnUser(rs, prefix);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public ArrayList<Operator> getUserByLastName(String usersLastName) {
        String prefix = "U";
        ArrayList<Operator> allUser = new ArrayList<>();
        Operator toBeAdded;

        String sql = "SELECT * FROM user WHERE U_lastName = ? AND U_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usersLastName);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    allUser.add(returnUser(rs, prefix));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return allUser;
    }

    public ArrayList<Operator> getUserByFirstName(String usersFirstName) {
        String prefix = "U";
        ArrayList<Operator> allUser = new ArrayList<>();
        Operator toBeReturned;
        String sql = "SELECT * FROM user WHERE U_firstName = ? AND U_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usersFirstName);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    allUser.add(returnUser(rs, prefix));
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return allUser;
    }

    public Operator getUserByUsername(String username) {
        String prefix = "U";
        String sql = "SELECT * FROM user WHERE U_email = ? AND U_deleted = 0;";
        Operator user = null;
        int userID = -1;
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return returnUser(rs, prefix);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;

    }

    public ArrayList<Operator> getAllUser() {
        String prefix = "U";
        ArrayList<Operator> allUser = new ArrayList<>();
        User user = null;
        String sql = "SELECT * FROM user WHERE U_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    allUser.add(returnUser(rs, prefix));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return allUser;
    }

    public Operator getOrganiserByEventName(String eventName) {
        String prefix = "OE";
        String sql = "SELECT * FROM organiserevent WHERE OE_EventID = ? AND OE_deleted = 0;";
        int userID;
        Operator organiser = null;
        String role, firstName, lastName, email;
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    organiser = returnUser(rs, prefix);
                }
                return organiser;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Kein Organisator für dieses Event");
        return null;
    }

    public Operator getOrganiserByID(int EventID) {
        String prefix = "OE";
        String sql = "SELECT * FROM organiserevent WHERE OE_eventID = ?  AND OE_deleted = 0;";
        Operator organiser = null;
        int userID;
        String role, firstName, lastName, email;
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, EventID);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    organiser = returnUser(rs, prefix);
                }
                return organiser;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("There's no organisator for thisppointment");
        return null;
    }

    public ArrayList<Operator> selectParticipantsByID(int EventID) {
        String prefix = "EM";
        ArrayList<Operator> participants = new ArrayList<>();
        Operator participant;
        int userID;
        String role, firstName, lastName, email;
        String sql = "SELECT * from eventmembers WHERE EM_eventID = ?;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, EventID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    participant = returnUser(rs, prefix);
                    participants.add(participant);
                }
            }
        } catch (Exception ex) {

        }
        return participants;
    }

    public ArrayList<Operator> getAllOperators() {
        String prefix = "U";
        ArrayList<Operator> userAndAdmins = new ArrayList<>();
        String sql = "SELECT * FROM USER WHERE U_deleted = 0;";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    userAndAdmins.add(returnUser(rs, prefix));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return userAndAdmins;
    }

    public int editUser(Operator toBeEdited) {
        int editSuccessfull = -1;
        String sqlUpdate
                = "UPDATE user SET U_email = ?,U_firstName =?, U_lastName = ?,U_role = ? WHERE U_userID = ?;";
        try ( PreparedStatement stmt = con.prepareStatement(sqlUpdate)) {
            stmt.setString(1, toBeEdited.getEmail());
            stmt.setString(2, toBeEdited.getFirstName());
            stmt.setString(3, toBeEdited.getLastName());
            stmt.setString(4, toBeEdited.getRole().toString());
            stmt.setInt(5, toBeEdited.getUserId());

            editSuccessfull = stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("User erfolgreich bearbeitet");
        return editSuccessfull;

    }

    public void deleteUser(Operator toBeDeleted) {
        String eventIDs = listToString(getOrganiserEventIDs(toBeDeleted.getUserId()));
        String deleteUser = "UPDATE user SET U_deleted = 1 WHERE U_userID = ?;";
        String deleteUsersEvents = "UPDATE Event SET E_deleted = 1 WHERE E_EventID IN (" + eventIDs + ");";
        int deleteSuccessfull = -1;

        try ( PreparedStatement stmt = con.prepareStatement(deleteUser)) {
            stmt.setInt(1, toBeDeleted.getUserId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        try ( PreparedStatement stmt = con.prepareStatement(deleteUsersEvents)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if (deleteSuccessfull == 0) {
            System.out.println("User erfolgreich gelöscht");
        }
    }

    public ArrayList<Operator> getAllUsersLike(String toBeLookedFor) {
        ArrayList<Operator> users = new ArrayList<>();
        String sql = "SELECT * FROM USER WHERE U_firstName LIKE ? OR U_lastName LIKE ? OR U_email LIKE ? AND U_deleted = 0;";
        char prefix = '%';
        char suffix = '%';
        Operator user = null;
        int userID = -1;
        String email, firstName, lastName, role;

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, prefix + toBeLookedFor + suffix);
            stmt.setString(2, prefix + toBeLookedFor + suffix);
            stmt.setString(3, prefix + toBeLookedFor + suffix);
            try ( ResultSet rs = stmt.executeQuery()) {
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
            System.out.println(ex.getMessage());
        }

        if (users.isEmpty()) {
            System.out.println("No User with such criteria does exist");

        }
        return users;

    }

    //Event Methods
    public void setParticipantsOfEvent(ArrayList<Operator> participants, int eventID) {
        ArrayList<Operator> event_old = selectParticipantsByID(eventID); //old
        ArrayList<Operator> event_new = (ArrayList<Operator>) participants.clone(); //new
        ArrayList<Operator> toBeAdded = (ArrayList<Operator>) event_new.clone();
        ArrayList<Operator> toBeDeleted = (ArrayList<Operator>) event_old.clone();

        toBeAdded.removeAll(event_old);
        toBeDeleted.removeAll(event_new);

        deleteParticipants(toBeDeleted, eventID);
        insertParticipants(toBeAdded, eventID);
    }

    public void insertParticipants(ArrayList<Operator> toBeAdded, int eventID) {
        String sql = "INSERT INTO participant (P_userID,P_eventID) VALUES (?,?);";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            for (Operator o : toBeAdded) {
                stmt.setInt(1, o.getUserId());
                stmt.setInt(2, eventID);
                stmt.addBatch();

            }
            stmt.executeBatch();
        } catch (SQLException ex) {
            System.out.println("insertPart: " + ex.getMessage());
        }
    }

    public void deleteParticipants(ArrayList<Operator> toBeDeleted, int eventID) {
        String sql = "DELETE FROM participant WHERE P_userID = ? AND P_eventID = ?;";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            for (Operator o : toBeDeleted) {
                stmt.setInt(1, o.getUserId());
                stmt.setInt(2, eventID);
                stmt.addBatch();

            }
            stmt.executeBatch();
        } catch (SQLException ex) {
            System.out.println("deletePart: " + ex.getMessage());
        }
    }

    public int editEvent(Event toBeEdited) {
        int editSuccessfull = -1;
        ArrayList<Operator> new_participants = toBeEdited.getParticipants();
        new_participants.add(toBeEdited.getHost());
        String editEvent
                = "UPDATE event SET E_eventName = ?, E_eventDuration = ?, E_eventDate = ?, E_priority = ?,"
                + "E_eventLocation = ?, E_reminder = ?, E_notification = ? "
                + "WHERE E_eventID = ?;";

        setParticipantsOfEvent(new_participants, toBeEdited.getID());

        //setFilesOfEvent(toBeEdited.getAttachments(), toBeEdited.getID());
        try ( PreparedStatement stmt = con.prepareStatement(editEvent)) {
            stmt.setString(1, toBeEdited.getName());
            stmt.setInt(2, toBeEdited.getDuration());
            stmt.setTimestamp(3, Timestamp.valueOf(toBeEdited.getDate()));
            stmt.setString(4, toBeEdited.getPriority().toString());
            stmt.setString(5, toBeEdited.getLocation());
            stmt.setTimestamp(6, Timestamp.valueOf(toBeEdited.getReminder()));
            stmt.setString(7, toBeEdited.getNotification().toString());
            stmt.setInt(8, toBeEdited.getID());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("editEvent: " + ex.getMessage());
        }

        if (LocalDateTime.now().isBefore(toBeEdited.getReminder())) {
            resetReminder(toBeEdited.getParticipants(), toBeEdited.getID());

        }
        System.out.println("Event erfolgreich bearbeitet");
        return editSuccessfull;
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> Events = new ArrayList<>();
        String sql = "SELECT * FROM eventDetails WHERE ED_deleted = 0 ORDER BY ED_eventDate;";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Events.add(getEvent(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        fillEventsWithParticipants(Events);
        return Events;
    }

    public void createNewEvent(Event new_event) {

        String sqlEvent = "INSERT INTO event (E_eventName, E_eventDuration, E_eventDate, E_priority, E_eventLocation, E_reminder, E_notification) VALUES (?, ?, ?, ?, ?, ?, ?);";
        ArrayList<Operator> temp = new_event.getParticipants();
        temp.add(new_event.getHost());

        try ( PreparedStatement stmt = con.prepareStatement(sqlEvent)) {
            stmt.setString(1, new_event.getName());
            stmt.setInt(2, new_event.getDuration());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(new_event.getDate()));
            stmt.setString(4, new_event.getPriority().toString());
            stmt.setString(5, new_event.getLocation());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(new_event.getReminder()));
            stmt.setString(7, new_event.getNotification().toString());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        setOrganiserOfEvent(new_event.getHost().getUserId(), getMaxEventID());
        setParticipantsOfEvent(temp, getMaxEventID());
        //setFilesOfEvent(new_event.getAttachments(), getMaxEventID());
    }

    public void deleteEvent(int EventID) {
        String sql = "UPDATE Event SET E_deleted = 1 WHERE E_EventID = ?;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, EventID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("delete event:" + ex.getMessage());
        }
    }

    public ArrayList<Event> getEventsByUsername(String username) {
        ArrayList<Event> usersEvents = new ArrayList<>();
        String usersEventIDs = listToString(getEventIDsOfUser(username));
        String sql = "SELECT * FROM eventDetails WHERE ED_EventID IN (" + usersEventIDs + ") AND ED_deleted = 0 ORDER BY ED_eventDate;";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usersEvents.add(getEvent(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        fillEventsWithParticipants(usersEvents);

        return usersEvents;
    }

    public ArrayList<Event> getUsersEventsOfACertainMonth(int userID, int month) {
        ArrayList<Event> usersEvents = new ArrayList<>();
        ArrayList<Integer> usersEventIDs = getEventIDsOfUser(userID);

        String sql
                = "SELECT * FROM eventdetails WHERE ed_eventdate >= "
                + "(SELECT MAKEDATE(YEAR(CURDATE()),1) + INTERVAL ? MONTH) "
                + "AND ed_eventdate <= (SELECT MAKEDATE(YEAR(CURDATE()),1) + INTERVAL ? MONTH) "
                + "AND ed_userID = ? AND ed_deleted = 0 ORDER BY ed_eventDate;";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, month - 1);
            stmt.setInt(2, month);
            stmt.setInt(3, userID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Event temp = getEvent(rs);

                    usersEvents.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if (!usersEvents.isEmpty()) {
            fillEventsWithParticipants(usersEvents);
            return usersEvents;
        } else {
            System.out.println("User doesn't have any Events this week");
        }
        return usersEvents;
    }

    public void setFilesOfEvent(ArrayList<File> eventFiles, int eventID) {
        deleteFiles(eventID);
        addFiles(eventFiles, eventID);
    }

    public ArrayList<Event> getThisMonthsEventsByUserID(int userID) {
        ArrayList<Event> usersEvents = new ArrayList<>();
        ArrayList<Integer> usersEventIDs = getEventIDsOfUser(userID);

        String sql
                = "SELECT * FROM eventdetails\n"
                + " WHERE ed_eventdate >= (SELECT MAKEDATE(YEAR(CURDATE()),1)) \n"
                + " AND ed_eventdate <= (SELECT MAKEDATE(YEAR(CURDATE()),1) + INTERVAL 1 MONTH) "
                + " AND ed_userID = ?"
                + " AND ed_deleted = 0"
                + " ORDER BY ed_eventDate;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Event temp = getEvent(rs);

                    usersEvents.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if (!usersEvents.isEmpty()) {
            fillEventsWithParticipants(usersEvents);
            return usersEvents;
        } else {
            System.out.println("User doesn't have any Events this week");
        }
        return usersEvents;
    }

    public ArrayList<Event> getThisWeeksEventsByUserID(int userID) {
        ArrayList<Event> usersEvents = new ArrayList<>();
        ArrayList<Integer> usersEventIDs = getEventIDsOfUser(userID);

        String sql
                = "SELECT * FROM eventdetails "
                + "WHERE ED_eventDate >= (SELECT DATE_ADD(CURDATE(), INTERVAL - WEEKDAY(CURDATE()) DAY)) "
                + "AND ED_eventDate <= (SELECT date(now() + INTERVAL 6 - weekday(now()) DAY)) "
                + "AND ED_userID = ? "
                + "AND ED_deleted = 0 "
                + "ORDER BY ED_eventDate;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Event temp = getEvent(rs);

                    usersEvents.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.out.println("This Weeks: " + ex.getMessage());
        }
        if (!usersEvents.isEmpty()) {
            fillEventsWithParticipants(usersEvents);
            return usersEvents;
        } else {
            System.out.println("User doesn't have any Events this week");
        }
        return usersEvents;
    }

    public Event getEventByUser(int userID, int eventID) {
        ArrayList<Integer> EventIDs = getEventIDsOfUser(userID);

        if (EventIDs.contains(eventID)) {
            return getEventById(eventID);
        } else {
            System.out.println("User doesn't take part in this Event or the Event doesn't exist.");
        }
        return null;
    }

    public ArrayList<Event> getAllEventByUser(int userID) {
        ArrayList<Event> Events = new ArrayList<>();
        String sql = "SELECT * FROM eventDetails WHERE ED_userID = ? AND ED_deleted = 0 ORDER BY ed_eventDate;";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Events.add(getEvent(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        fillEventsWithParticipants(Events);
        return Events;
    }

    public Event getEventById(int EventID) {
        Event toBeReturned = null;

        String sql = "SELECT * FROM eventDetails WHERE ED_EventID = ? AND ED_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, EventID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    toBeReturned = getEvent(rs);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        toBeReturned.setParticipants(selectParticipantsByID(toBeReturned.getID()));
        return toBeReturned;
    }

    private ArrayList<Integer> getEventIDsOfUser(String username) {
        ArrayList<Integer> EventIDs = new ArrayList<>();
        String sql = "SELECT EM_EventID FROM eventmembers WHERE EM_email = ? AND EM_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EventIDs.add(rs.getInt("EM_EventID"));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return EventIDs;

    }

    private ArrayList<Integer> getEventIDsOfUser(int userId) {
        ArrayList<Integer> EventIDs = new ArrayList<>();
        String sql = "SELECT EM_EventID FROM eventmembers WHERE EM_userID = ? AND EM_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EventIDs.add(rs.getInt("EM_EventID"));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return EventIDs;
    }

    public ArrayList<Event> getUsersEventsOfCertainDay(int userId, LocalDate eventDate) {
        ArrayList<Integer> EventIDs = getEventIDsOfUser(userId);
        ArrayList<Event> usersEvents = new ArrayList<>();
        String sql = "SELECT * FROM eventDetails WHERE ED_userID = ? AND ED_eventDate LIKE ? AND ED_deleted = 0 ORDER BY ed_eventDate;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, eventDate.toString() + "%");
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usersEvents.add(getEvent(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        if (!usersEvents.isEmpty()) {
            fillEventsWithParticipants(usersEvents);
            return usersEvents;
        } else {
            System.out.println("User doesn't have any Events this week");
        }
        return usersEvents;
    }

    public ArrayList<Event> getEventsOfPeriod(int userID, LocalDate from, LocalDate to) {
        ArrayList<Integer> EventIDs = getEventIDsOfUser(userID);
        ArrayList<Event> usersEvents = new ArrayList<>();
        String sql = "SELECT * FROM eventDetails WHERE ED_userID = ? AND ED_eventDate >= ? AND ED_eventDate <= ? AND ED_deleted = 0 ORDER BY ED_eventDate;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setTimestamp(2, Timestamp.valueOf(from.atStartOfDay()));
            stmt.setTimestamp(3, Timestamp.valueOf(to.atTime(23, 59, 59, 59)));

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usersEvents.add(getEvent(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        fillEventsWithParticipants(usersEvents);
        return usersEvents;
    }

    private void resetReminder(ArrayList<Operator> participants, int eventID) {
        String sql = "UPDATE participant SET P_notified = 0 WHERE P_userID = ? AND P_eventID = ?;";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            for (Operator o : participants) {
                stmt.setInt(1, o.getUserId());
                stmt.setInt(2, eventID);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }catch(SQLException ex){
            System.out.println("reset Reminder:"+ex.getMessage());
        }
    }

    }
