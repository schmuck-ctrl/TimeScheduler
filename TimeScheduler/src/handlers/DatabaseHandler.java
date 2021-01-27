/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import classes.*;
import classes.Event.Priority;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author joshua
 */
public class DatabaseHandler {
    
    private Connection con = null;
    
    public static void main(String[] Args) {
        DatabaseHandler db = new DatabaseHandler();
        ArrayList<Integer> test = new ArrayList<>();
    }
    
    public DatabaseHandler() {
        con = getConnection();
        //System.out.println("Bin da!");
    }

    //PRIVATE FUNCTION SECTION 
    /**
     * Returns the IDs of Events of a User of which he hasn't been notified yet
     *
     * @param userID the Id by which the user is Identified in the Database
     * @return a List of IDs
     */
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

    /**
     * Stores the attachment files of a certain Event in the Database
     *
     * @param toBeAdded files which are going to be stored
     * @param eventID identifier of the event
     */
    private void insertAttachments(ArrayList<Attachment> toBeAdded, int eventID) {
        String addAttachment = "INSERT INTO file (F_file,F_eventID,F_fileName) VALUES(?,?,?)";
        
        try ( PreparedStatement stmt = con.prepareStatement(addAttachment)) {
            for (Attachment file : toBeAdded) {
                FileInputStream fileInput = new FileInputStream(file);
                stmt.setBinaryStream(1, fileInput);
                stmt.setInt(2, eventID);
                stmt.setString(3, file.getName());
                stmt.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println("add Files: " + ex.getMessage());
        }
    }

    /**
     * Stores the information of additional participants of an event in the
     * database
     *
     * @param toBeAdded a List of Operator#Operator which should be stored
     * @param eventID the ID of the Event they're participating
     */
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

    /**
     * Deletes the Attachments of an Event in the Database
     *
     * @param toBeDeleted List of Attachments/Files
     * @param eventID EventID of the Event the files are assigned to
     */
    private void deleteAttachments(ArrayList<Attachment> toBeDeleted, int eventID) {
        
        String deleteAttachments = "DELETE FROM file WHERE F_fileID = ? ";
        
        try ( PreparedStatement stmt = con.prepareStatement(deleteAttachments)) {
            
            for (Attachment attachment : toBeDeleted) {
                stmt.setInt(1, attachment.getAttachmentID());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException ex) {
            System.out.println("delete file: " + ex.getMessage());
        }
        
    }

    /**
     * Assigns the host to a certain Event and stores it in the Database
     *
     * @param userID ID of the User which is hosting the Event
     * @param eventID ID of the Event which ist hosted
     */
    private void setHostOfEvent(int userID, int eventID) {
        String sql = "INSERT INTO host (H_userID,H_eventID)  VALUES (?,?);";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, eventID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
    }

    /**
     * private function only called for getting the ID of a newly created Event
     *
     * @return the ID of an Event which was just created
     */
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
            System.out.println("" + ex.getMessage());
        }
        
        return -1;
    }

    /**
     * Iterates over a list of Events and sets {@link Event#participants} and
     * {@link Event#attachments} respectively to their ID
     *
     * @param events List of Events
     */
    private void fillEventsWithParticipantsAndFiles(ArrayList<Event> events) {
        if (!events.isEmpty()) {
            events.forEach(temp -> {
                temp.setParticipants(selectParticipantsByID(temp.getID()));
                temp.setAttachments(getEventsDocuments(temp.getID()));
            });
        }
    }

    /*private static String getParticipantsID(ArrayList<Operator> participants) {
        StringBuilder buffer = new StringBuilder();
        
        participants.forEach(participant -> {
            buffer.append(participant.getUserId()).append(",");
        });
            return buffer.toString().substring(0, buffer.length() - 1);

    }*/
    /**
     * Returns a comma seperated String of numbers
     *
     * @param numbers a list of eventIDs
     * @return a String is returned
     */
    private static String listToString(ArrayList<Integer> numbers) {
        StringBuilder buffer = new StringBuilder();
        
        numbers.forEach(number -> {
            buffer.append(number).append(",");
        });
        
        return buffer.toString().substring(0, buffer.length() - 1);
    }

    /**
     * return the EventIDs of a Events a certain user hosts
     *
     * @param userID ID by which the host is identified
     * @return a Integer List of Event IDs
     */
    private ArrayList<Integer> getHostsEventIDs(int userID) {
        ArrayList<Integer> eventIDs = new ArrayList<>();
        String sql = "SELECT HE_eventID, HE_deleted FROM hostevent WHERE HE_userID = ? AND HE_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    eventIDs.add(rs.getInt("HE_eventID"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
        
        return eventIDs;
        
    }

    /**
     * Returns a {@link User} or an {@link Admin} Object respectively to their
     * role
     *
     * @param rs dataset regarding the Operator
     * @param prefix Prefix of the table the information is contained
     * @return an {@link User} or an {@link Admin}
     */
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
            System.out.println("return User: " + ex.getMessage());
        }
        return user;
    }

    /**
     * returns a {@link Event} Object respectively to the dataset
     *
     * @param rs the Dataset containing the information of an Event
     * @return an Event
     */
    private Event getEvent(ResultSet rs) {
        Event event = null;
        Operator host = null;
        String hostFirstName, hostLastName, hostEmail, hostRole; //Host
        int hostID, eventID = 0;
        String eventName = null, eventLocation = null; //EventStrings
        LocalDateTime eventDate = null;
        ArrayList<Operator> participants = null;
        ArrayList<Attachment> attachments = null;
        
        Priority Priority = null;
        classes.Event.Notification notification = null;
        int eventDuration = 0;
        try {
            hostID = rs.getInt("ED_userID");
            hostRole = rs.getString("ED_role");
            hostFirstName = rs.getString("ED_firstName");
            hostLastName = rs.getString("ED_lastName");
            hostEmail = rs.getString("ED_email");
            host = AdminOrUser(hostRole, hostID, hostFirstName, hostLastName, hostEmail);
            eventID = rs.getInt("ED_eventID");
            eventName = rs.getString("ED_eventName");
            eventDate = rs.getTimestamp("ED_eventDate").toLocalDateTime();
            eventDuration = rs.getInt("ED_eventDuration");
            eventLocation = rs.getString("ED_eventLocation");
            Priority = Event.Priority.valueOf(rs.getString("ED_priority"));
            notification = Event.Notification.valueOf(rs.getString("ED_notification"));            
        } catch (SQLException ex) {
            System.out.println("get Event: " + ex.getMessage());
        }
        event = new Event(eventID, eventName, host, eventDate, eventDuration, eventLocation,
                participants, attachments, Priority, notification);
        return event;
    }

    /**
     * Converts a {@link Operator} respectively to its role object respectively
     * to its role
     *
     * @param role role of the {@link Operator} Object
     * @param userId id of the {@link Operator} Object
     * @param firstName first Name of the {@link Operator} Object
     * @param lastName last Name of the {@link Operator} Object
     * @param email email adress of the {@link Operator} Object
     * @return an {@link User} or {@link Admin}
     */
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
    /**
     * establishes a Connection to the Database
     *
     * @return a Connection
     */
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
            System.out.println("" + ex.getMessage());
        }
        return null;
    }

    //USER FUNCTIONS
    /**
     * Updates the notification status of a User ragarding a certain
     * {@link Event} in the database
     *
     * @param eventID ID of the Event he's been notified
     * @param userID ID of the User who's been notified
     */
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

    /**
     * Returns a list of Event IDs of which the user hasn't been notified yet
     *
     * @param userID ID of the User
     * @return a list of Integers.
     */
    public ArrayList<Event> getNotNotifiedEventsFromUser(int userID) {
        //ArrayList<Integer> eventIDs = getEventIDsNotNotified(userID);
        String notNotified
                = "SELECT * FROM eventdetails WHERE ED_eventID IN"
                + "(SELECT P_eventID FROM participant WHERE P_userID = ? AND P_notified = 0)"
                + " AND ED_notification <> 'NONE';";
        
        
        ArrayList<Event> events = new ArrayList<>();
        try ( PreparedStatement stmt = con.prepareStatement(notNotified)) {
            stmt.setInt(1, userID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    events.add(getEvent(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println("getEventsNotNotified" + ex.getMessage());
        }
        
        fillEventsWithParticipantsAndFiles(events);
        return events;
    }

    /**
     * verifies if a User with given input exists
     *
     * @param username {@link Operator#email} of a {@link Operator}
     * @param password hashed password of the user
     * @return {@link Boolean#TRUE} if input is valid else {@link  Boolean#FALSE}
     */
    public boolean checkIfUserExists(String username, String password) {
        String sql = "SELECT * FROM user WHERE U_email = ? AND U_password = ? AND U_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try ( ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
        return false;
    }

    /**
     * Verified is a User with given {@link Operator#email} exists
     *
     * @param username {@link Operator#email} of a {@link Operator}
     * @return {@link Boolean#TRUE} if input is valid else {@link  Boolean#FALSE}
     */
    public boolean checkIfUserExists(String username) {
        String sql = "SELECT * FROM user WHERE U_email = ? AND U_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try ( ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
        return false;
    }

    /**
     * Inserts a newly created profile of a user into the database
     *
     * @param user {@link Operator} Object containing the users data
     * @param password hashed password value of a user
     */
    public void insertNewUser(Operator user, String password) {
        
        String sql = "INSERT INTO user (U_email, U_firstName, U_lastName, U_role, U_password) VALUES (?,?,?,?,?);";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getRole().toString());
            stmt.setString(5, password);
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
        System.out.println("Nutzer Erfolgreich angelegt");
        
    }

    /**
     * Returns Operator Object containing information of a User who's identified
     * by their ID
     *
     * @param userID
     * @return {@link User} or {@link Admin} respectively to their role
     */
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
            System.out.println("" + ex.getMessage());
        }
        return null;
    }

    /*public ArrayList<Operator> getUserByLastName(String usersLastName) {
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
            System.out.println(""+ex.getMessage());
        }

        return allUser;
    }*/

 /*public ArrayList<Operator> getUserByFirstName(String usersFirstName) {
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
            System.out.println(""+ex.getMessage());
        }

        return allUser;
    }*/
    /**
     * Returns an {@link Operator} Object containing information of a user
     * identified by their username
     *
     * @param username
     * @return {@link User} or {@link Admin} respectively to their role
     */
    public Operator getUserByUsername(String username) {
        String prefix = "U";
        String sql = "SELECT * FROM user WHERE U_email = ? AND U_deleted = 0;";
        Operator user = null;
        int userID = -1;
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = returnUser(rs, prefix);
                }
            }
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
        return user;
        
    }

    /**
     * returns a List of {@link Operator} Objects containing all users
     *
     * @return a List of Users
     */
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
            System.out.println("" + ex.getMessage());
        }
        
        return allUser;
    }

    /*public Operator getHostByEventName(String eventName) {
        String prefix = "HE";
        String sql = "SELECT * FROM hostevent WHERE HE_EventID = ? AND HE_deleted = 0;";
        int userID;
        Operator host = null;
        String role, firstName, lastName, email;
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    host = returnUser(rs, prefix);
                }
                return host;
            }
        } catch (SQLException ex) {
            System.out.println(""+ex.getMessage());
        }
        System.out.println("Kein Host für dieses Event");
        return null;
    }*/
 /*public Operator getHostByID(int EventID) {
        String prefix = "HE";
        String sql = "SELECT * FROM hostevent WHERE HE_eventID = ?  AND HE_deleted = 0;";
        Operator host = null;
        int userID;
        String role, firstName, lastName, email;
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, EventID);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    host = returnUser(rs, prefix);
                }
                return host;
            }
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
        System.out.println("There's no host for this appointment");
        return null;
    }*/
    /**
     * Returns a List of Users
     *
     * @param EventID
     * @return
     */
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

    /*
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
            System.out.println("" + ex.getMessage());
        }
        return userAndAdmins;
    }*/
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
            System.out.println("" + ex.getMessage());
        }
        System.out.println("User erfolgreich bearbeitet");
        return editSuccessfull;
    }
    
    public void deleteUser(Operator toBeDeleted) {
        String eventIDs = listToString(getHostsEventIDs(toBeDeleted.getUserId()));
        String deleteUser = "UPDATE user SET U_deleted = 1 WHERE U_userID = ?;";
        String deleteUsersEvents = "UPDATE Event SET E_deleted = 1 WHERE E_EventID IN (" + eventIDs + ");";
        
        try ( PreparedStatement stmt = con.prepareStatement(deleteUser)) {
            stmt.setInt(1, toBeDeleted.getUserId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
        
        try ( PreparedStatement stmt = con.prepareStatement(deleteUsersEvents)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
    }

    /*
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
            System.out.println("" + ex.getMessage());
        }

        if (users.isEmpty()) {
            System.out.println("No User with such criteria does exist");

        }
        return users;

    }*/
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
    
    public void setFilesOfEvent(ArrayList<Attachment> attachments, int eventID) {
        //Every old Attachment{x1,x2,x3,x4}
        ArrayList<Attachment> attachment_old = getEventsDocuments(eventID);
        //Every new Attachment {x3,x4,x5,x6}
        ArrayList<Attachment> attachment_new = (ArrayList<Attachment>) attachments.clone();
        //Every Attachment which is estimated to be added {x3,x4,x5,x6}
        ArrayList<Attachment> toBeAdded = (ArrayList<Attachment>) attachment_new.clone();
        //Every Attachment which ist estimated to be removed .{x1,x2,x3,x4}
        ArrayList<Attachment> toBeDeleted = (ArrayList<Attachment>) attachment_old.clone();

        //{x3,x4,x5,x6} remove {x1,x2,x3,x4} return {x5,x6}
        //Remove every Attachment which was already contained in the Events Attachment list
        toBeAdded.removeAll(attachment_old);
        //{x1,x2,x3,x4} remove {x3,x4,x5,x6} return {x1,x2}
        //Remove every Attachment which was contained in the old list of the Event but isn't contained in the new list of
        //Attachments anymore.
        toBeDeleted.removeAll(attachment_new);
        deleteAttachments(toBeDeleted, eventID);
        insertAttachments(toBeAdded, eventID);
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
            System.out.println("insert Participants: " + ex.getMessage());
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
            System.out.println("delete Participants: " + ex.getMessage());
        }
    }
    
    public int editEvent(Event toBeEdited) {
        int editSuccessfull = -1;
        ArrayList<Operator> new_participants = (ArrayList<Operator>) toBeEdited.getParticipants().clone();
        // new_participants.add(toBeEdited.getHost());
        String editEvent
                = "UPDATE event SET E_eventName = ?, E_eventDuration = ?, E_eventDate = ?, E_priority = ?,"
                + "E_eventLocation = ?, E_notification = ? "
                + "WHERE E_eventID = ?;";
        
        setParticipantsOfEvent(new_participants, toBeEdited.getID());
        
        setFilesOfEvent(toBeEdited.getAttachments(), toBeEdited.getID());
        try ( PreparedStatement stmt = con.prepareStatement(editEvent)) {
            stmt.setString(1, toBeEdited.getName());
            stmt.setInt(2, toBeEdited.getDuration());
            stmt.setTimestamp(3, Timestamp.valueOf(toBeEdited.getDate()));
            stmt.setString(4, toBeEdited.getPriority().toString());
            stmt.setString(5, toBeEdited.getLocation());
            stmt.setString(6, toBeEdited.getNotification().toString());
            stmt.setInt(7, toBeEdited.getID());
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
            System.out.println("get All Events: " + ex.getMessage());
        }
        
        fillEventsWithParticipantsAndFiles(Events);
        return Events;
    }
    
    public void createNewEvent(Event new_event) {
        
        String sqlEvent = "INSERT INTO event (E_eventName, E_eventDuration, E_eventDate, E_priority, E_eventLocation, E_notification) VALUES (?, ?, ?, ?, ?, ?);";
        ArrayList<Operator> temp = new_event.getParticipants();
        //temp.add(new_event.getHost());

        try ( PreparedStatement stmt = con.prepareStatement(sqlEvent)) {
            stmt.setString(1, new_event.getName());
            stmt.setInt(2, new_event.getDuration());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(new_event.getDate()));
            stmt.setString(4, new_event.getPriority().toString());
            stmt.setString(5, new_event.getLocation());
            stmt.setString(6, new_event.getNotification().toString());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Create new Event: " + ex.getMessage());
        }
        
        setHostOfEvent(new_event.getHost().getUserId(), getMaxEventID());
        setParticipantsOfEvent(temp, getMaxEventID());
        setFilesOfEvent(new_event.getAttachments(), getMaxEventID());
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
    
    public ArrayList<Event> getEventsByUserID(int userID) {
        ArrayList<Event> usersEvents = new ArrayList<>();
        
        String sql = "SELECT * FROM eventDetails WHERE ED_EventID IN "
                + "(SELECT P_eventID FROM Participant WHERE P_userID = ?) "
                + "AND ED_deleted = 0 ORDER BY ED_eventDate;";
        
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usersEvents.add(getEvent(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
        
        fillEventsWithParticipantsAndFiles(usersEvents);
        
        return usersEvents;
    }
    
    public ArrayList<Event> getUsersEventsOfACertainMonth(int userID, int month) {
        ArrayList<Event> usersEvents = new ArrayList<>();
        ArrayList<Integer> usersEventIDs = getEventIDsOfUser(userID);
        
        String sql
                = "SELECT * FROM eventdetails WHERE ed_eventdate >= "
                + "(SELECT MAKEDATE(YEAR(CURDATE()),1) + INTERVAL ? MONTH) "
                + "AND ed_eventdate <= (SELECT MAKEDATE(YEAR(CURDATE()),1) + INTERVAL ? MONTH) "
                + "AND ed_eventID IN( " + listToString(usersEventIDs) + ") AND ed_deleted = 0 ORDER BY ed_eventDate;";
        
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, month - 1);
            stmt.setInt(2, month);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Event temp = getEvent(rs);
                    
                    usersEvents.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.out.println("get Users Appointment of a certain Month: " + ex.getMessage());
        }
        if (!usersEvents.isEmpty()) {
            fillEventsWithParticipantsAndFiles(usersEvents);
            return usersEvents;
        }
        return usersEvents;
    }
    
    public ArrayList<Event> getThisMonthsEventsByUserID(int userID) {
        ArrayList<Event> usersEvents = new ArrayList<>();
        ArrayList<Integer> usersEventIDs = getEventIDsOfUser(userID);
        
        String sql
                = "SELECT * FROM eventdetails\n"
                + " WHERE ed_eventdate >= (SELECT MAKEDATE(YEAR(CURDATE()),1)) \n"
                + " AND ed_eventdate <= (SELECT MAKEDATE(YEAR(CURDATE()),1) + INTERVAL 1 MONTH) "
                + " AND IN (" + listToString(usersEventIDs)
                + ") AND ed_deleted = 0"
                + " ORDER BY ed_eventDate;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Event temp = getEvent(rs);
                    
                    usersEvents.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.out.println("getThisMonthsEventsByUserID" + ex.getMessage());
        }
        if (!usersEvents.isEmpty()) {
            fillEventsWithParticipantsAndFiles(usersEvents);
            return usersEvents;
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
                + "AND ED_eventID IN(" + listToString(usersEventIDs)
                + ") AND ED_deleted = 0 "
                + "ORDER BY ED_eventDate;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
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
            fillEventsWithParticipantsAndFiles(usersEvents);
            return usersEvents;
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

    /*public ArrayList<Event> getAllEventsOfUser(int userID) {
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
            System.out.println("get all Events of user" + ex.getMessage());
        }

        fillEventsWithParticipantsAndFiles(Events);
        return Events;
    }*/
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
            System.out.println("Get Event By Id: " + ex.getMessage());
        }
        
        toBeReturned.setParticipants(selectParticipantsByID(toBeReturned.getID()));
        toBeReturned.setAttachments(getEventsDocuments(toBeReturned.getID()));
        return toBeReturned;
    }
    
    private ArrayList<Integer> getEventIDsOfUser(int userID) {
        ArrayList<Integer> EventIDs = new ArrayList<>();
        String sql = "SELECT EM_EventID FROM eventmembers WHERE EM_userID = ? AND EM_deleted = 0;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EventIDs.add(rs.getInt("EM_EventID"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("get EventIDs Of User" + ex.getMessage());
        }
        
        return EventIDs;
    }
    
    public ArrayList<Event> getUsersEventsOfCertainDay(int userId, LocalDate eventDate) {
        ArrayList<Integer> EventIDs = getEventIDsOfUser(userId);
        ArrayList<Event> usersEvents = new ArrayList<>();
        String sql = "SELECT * FROM eventDetails WHERE ED_eventID IN(" + listToString(EventIDs) + ")AND ED_eventDate LIKE ? AND ED_deleted = 0 ORDER BY ed_eventDate;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, eventDate.toString() + "%");
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usersEvents.add(getEvent(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println("getUsersEventsOfCertainDay: " + ex.getMessage());
        }
        
        if (!usersEvents.isEmpty()) {
            fillEventsWithParticipantsAndFiles(usersEvents);
            return usersEvents;
        }
        return usersEvents;
    }
    
    public ArrayList<Event> getEventsOfPeriod(int userID, LocalDate from, LocalDate to) {
        ArrayList<Integer> EventIDs = getEventIDsOfUser(userID);
        ArrayList<Event> usersEvents = new ArrayList<>();
        String sql = "SELECT * FROM eventDetails WHERE ED_eventID IN (" + listToString(EventIDs) + ") AND ED_eventDate >= ? AND ED_eventDate <= ? AND ED_deleted = 0 ORDER BY ED_eventDate;";
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(from.atStartOfDay()));
            stmt.setTimestamp(2, Timestamp.valueOf(to.atTime(23, 59, 59, 59)));
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usersEvents.add(getEvent(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println("get Events Of Period: " + ex.getMessage());
        }
        
        fillEventsWithParticipantsAndFiles(usersEvents);
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
        } catch (SQLException ex) {
            System.out.println("reset Reminder:" + ex.getMessage());
        }
    }
    
    public ArrayList<Attachment> getEventsDocuments(int eventID) {
        String sql = "SELECT F_fileID, F_fileName FROM File where F_eventID = ?";
        ArrayList<Attachment> attachments = new ArrayList<>();
        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, eventID);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Attachment temp = new Attachment(rs.getString("F_fileName"), rs.getInt("F_fileID"));
                    attachments.add(temp);
                }
            }
        } catch (Exception ex) {
            System.out.println("getEventsDocuments: " + ex.getMessage());
        }
        return attachments;
    }
    
    public byte[] getDocument(int FileID) {
        byte[] document;
        String getDoc = "SELECT F_file FROM file WHERE F_fileID = ?";
        try ( PreparedStatement stmt = con.prepareStatement(getDoc)) {
            stmt.setInt(1, FileID);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    InputStream is = rs.getBinaryStream("F_file");
                    document = is.readAllBytes();
                    return document;
                }
            }
        } catch (Exception ex) {
            System.out.println("getDocument:" + ex.getMessage());
        }
        return null;
    }
}
