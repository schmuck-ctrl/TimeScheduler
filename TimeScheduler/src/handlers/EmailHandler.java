/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import classes.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Vadym
 */
public class EmailHandler implements Runnable {

    /**
     * Emails that will be send.
     *
     * @see MailOperation#VERIFY_ACCOUNT
     * @see MailOperation#VERIFY_EMAIL
     * @see MailOperation#CREATE_EVENT
     * @see MailOperation#EDIT_EVENT
     * @see MailOperation#DELETE_EVENT
     * @see MailOperation#REMIND_EVENT
     */
    public enum MailOperation {
        /**
         * Send email to verify Login.
         */
        VERIFY_ACCOUNT,
        /**
         * Send email to verify Email.
         */
        VERIFY_EMAIL,
        /**
         * Send email if event is created.
         */
        CREATE_EVENT,
        /**
         * Send email if event is edited.
         */
        EDIT_EVENT,
        /**
         * Send email if event is deleted.
         */
        DELETE_EVENT,
        /**
         * Send email if reminder is triggered.
         */
        REMIND_EVENT;
    }
    /**
     * The user which is displayed if an event is edited,deleted or created..
     */
    private Operator user;
    /**
     * All participants of an event if an event is edited,deleted or created..
     */
    private ArrayList<Operator> participants;
    /**
     * Current event if an event is edited,deleted or created..
     */
    private Event event;
    /**
     * Generated random number if a email verification or two step verification
     * happens.
     */
    private int rand;
    /**
     * Organizer of an event if an event is edited,deleted or created.
     */
    private Operator organizer;
    /**
     * The enum that is used.
     */
    private MailOperation operation;
    /**
     * Email address of the user used for the email verification.
     */
    private String email;

    /**
     * Constructs a new EmailHandler that is used to send an verification email.
     *
     * @param email of the user that trys to create an account.
     * @param rand generated random number.
     */
    public EmailHandler(String email, int rand) {
        this.operation = MailOperation.VERIFY_EMAIL;
        this.email = email;
        this.rand = rand;
    }

    /**
     * Constructs a new EmailHandler that is used to send a account verification
     * to the user.
     *
     * @param user the User operator that trys to login.
     * @param rand A random number.
     */
    public EmailHandler(Operator user, int rand) {
        this.operation = MailOperation.VERIFY_ACCOUNT;
        this.user = user;
        this.rand = rand;
    }

    /**
     * Constructs a new EmailHandler that is used to send emails when the user
     * edits, delets or creates events.
     *
     * @param operation The Operation that the user does like editing, deleting
     * or creating a event.
     * @param event The event that is used by the user.
     */
    public EmailHandler(MailOperation operation, Event event) {
        this.operation = operation;
        this.organizer = event.getHost();
        this.participants = event.getParticipants();
        this.event = event;
    }

    /**
     * Formates the time from this event into a string
     *
     * @return A time string which contains the Time of the event.
     */
    public String timeFormater() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.of(event.getDate().getHour(), event.getDate().getMinute());
        String time = formatter.format(localTime);
        return time;
    }

    /**
     * Sending an email to verify the user who trys to log in.
     *
     * @param user The user that should get the email.
     * @param rand The random number that the user should receive to verifiy his
     * account
     */
    public void emailSenderVerifyAccount(Operator user, int rand) {
        // email string .
        String from = "Javprojekt@gmail.com";
        // password of the email string.
        String password = "Javaprojekt123";

        //Allows to combine (value pairs)multiple properties to one list/propertie. The prop will be used as an setting.
        Properties prop = new Properties();
        //The host that is used here it is gmail
        prop.put("mail.smtp.host", "smtp.gmail.com");
        //The port that is used by gmail.
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");

        prop.put("mail.smtp.socketFactory.port", "465");
        //creats an secure socket. Socket = is one endpoint of a two-way communication link between two programs running on the network.
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //Creating a mail session with all above props and a new mail Authenticator.
        //The mail authenticator is necessery for the network connection.
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            // The getPasswordAuthentication() method is needed for a password authorization for basic HTTP authentication.
            //Socket classes are used to represent the connection between a client program and a server program.
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            //An mime message is an internet standard  that extends the format of email messages to support text and attachments.
            Message message = new MimeMessage(session);
            //Who sends the mail
            message.setFrom(new InternetAddress(from));
            // !!!!!!!!! Später entfernen !!!!!!
            System.out.println(user.getLastName());
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                //Who gets the Email.
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
                message.setSubject("Verification Number");
                message.setText("Your Verification code: " + rand);

                Transport.send(message);
                LoggerHandler.logger.info("Email was send for the two step verification.");
                System.out.println("Mail Sent...");
            } else {
                System.out.println("fehler ist hier");
                LoggerHandler.logger.severe("Error Email was not sended to the user.");
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sending an email with a random number to verify a user account.
     *
     * @param email The email of the user that trys to create a account.
     * @param rand The generated random number.
     */
    public void emailSenderVerifyEmail(String email, int rand) {
        String from = "Javprojekt@gmail.com"; // from address. As this is using Gmail SMTP your from address should be gmail
        String password = "Javaprojekt123"; // password for from gmail address that you have used in above line. 

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            if (this.email != null && !this.email.isEmpty()) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.email));
                message.setSubject("Email verification");
                message.setText("Your email verification code: " + rand);

                Transport.send(message);
                LoggerHandler.logger.info("Email was send to verifiy the user email address");
//                System.out.println("Mail Sent...");
            } else {
                LoggerHandler.logger.severe("Error Email was not sended to the user");
//                System.out.println("fehler ist hier");
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sending an email with a random number to verify a user account.
     *
     * @param event Event that was new created by the user.
     */
    public void emailSenderAddEvent(Event event) {
        //String to = ""; // to address. It can be any like gmail, yahoo etc.
        String from = "Javprojekt@gmail.com"; // from address. As this is using Gmail SMTP your from address should be gmail
        String password = "Javaprojekt123"; // password for from gmail address that you have used in above line. 
        StringBuilder text = new StringBuilder();
        String time = timeFormater();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            for (int i = 0; i < participants.size(); i++) {
                text.append(participants.get(i).getLastName() + ", " + participants.get(i).getFirstName());
                text.append("\n");
            }
            for (int i = 0; i < participants.size(); i++) {
                System.out.println(participants.get(i).getLastName());
                if (participants.get(i).getEmail() != null && !participants.get(i).getEmail().isEmpty()) {
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(participants.get(i).getEmail()));
                    message.setSubject("You where invited to the: \"" + event.getName() + "\" meeting");
//                    BodyPart mainMessage = new MimeBodyPart();
                    message.setText("Dear Mrs/Mr " + participants.get(i).getLastName() + ",\n\n"
                            + "all informations about the new meeting are below." + "\n" + "\n"
                            + "Appointment name: " + event.getName() + "\n"
                            + "Meeting owner: " + organizer.getLastName() + "\n"
                            + "Date: " + event.getDate().getDayOfMonth() + "." + event.getDate().getMonth() + "." + event.getDate().getYear() + "\n"
                            + "Time: " + time + "\n"
                            + "Meeting duration: " + event.getDuration() + " Minutes" + "\n"
                            + "Place: " + event.getLocation() + "\n"
                            + "Participants of the meeting: \n"
                            + text);
//                    Multipart multipart = new MimeMultipart();
//                    multipart.addBodyPart(mainMessage);
                    // Attachment
//                    mainMessage = new MimeBodyPart();

                    Transport.send(message);
                    LoggerHandler.logger.info("Email was send to notify all participants about the new Event");
//                    System.out.println("Mail Sent...");
                } else {
                    LoggerHandler.logger.severe("Error Email was not sended to the user.");
//                    System.out.println("fehler ist hier");
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sending an email to all participants of this event that this event was
     * edited.
     *
     * @param event Event that was edited by the user.
     */
    public void emailSenderEditEvent(Event event) {
        String from = "Javprojekt@gmail.com"; // from address. As this is using Gmail SMTP your from address should be gmail
        String password = "Javaprojekt123"; // password for from gmail address that you have used in above line. 
        StringBuilder text = new StringBuilder();

        String time = timeFormater();
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for (int i = 0; i < participants.size(); i++) {
                text.append(participants.get(i).getLastName() + ", " + participants.get(i).getFirstName());
                text.append("\n");
            }

            for (int i = 0; i < participants.size(); i++) {

                System.out.println(participants.get(i).getLastName());
                if (participants.get(i).getEmail() != null && !participants.get(i).getEmail().isEmpty()) {
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(participants.get(i).getEmail()));

                    message.setSubject("The event \"" + event.getName() + "\" was updated");
                    message.setText("Dear Mrs/Mr " + participants.get(i).getLastName() + ",\n\n"
                            + "all informations about the updated meeting are below." + "\n"
                            + "Appointment name: " + event.getName() + "\n"
                            + "Meeting owner: " + organizer.getLastName() + "\n"
                            + "Date: " + event.getDate().getDayOfMonth() + "." + event.getDate().getMonth() + "." + event.getDate().getYear() + "\n"
                            + "Time: " + time + "\n"
                            + "Meeting duration: " + event.getDuration() + " Minutes" + "\n"
                            + "Place: " + event.getLocation() + "\n"
                            + "Participants of the meeting: \n"
                            + text);

                    Transport.send(message);
                    LoggerHandler.logger.info("Email was send to notify all participants about the edited event");
//                    System.out.println("Mail Sent...");
                } else {
//                    System.out.println("fehler ist hier");
                    LoggerHandler.logger.severe("Error Email was not sended to the user.");
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sending an email to all participants of this event that the event was
     * deleted.
     *
     * @param event Event which was deleted by the user.
     */
    public void emailSenderDeleteEvent(Event event) {
        String from = "Javprojekt@gmail.com"; // from address. As this is using Gmail SMTP your from address should be gmail
        String password = "Javaprojekt123"; // password for from gmail address that you have used in above line. 
        String time = timeFormater();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            for (int i = 0; i < participants.size(); i++) {
                System.out.println(participants.get(i).getLastName());
                if (participants.get(i).getEmail() != null && !participants.get(i).getEmail().isEmpty()) {
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(participants.get(i).getEmail()));

                    message.setSubject("The event: \"" + event.getName() + "\" was deleted");
                    message.setText("Dear Mrs/Mr" + participants.get(i).getLastName() + ",\n"
                            + "the meeting: \"" + event.getName() + "\" on the: " + event.getDate().getDayOfMonth() + "." + event.getDate().getMonth() + "." + event.getDate().getYear() + " at: " + time + " was deleted.");

                    Transport.send(message);
                    LoggerHandler.logger.info("Email was send to notify all participants about the deleted event");
//                    System.out.println("Mail Sent...");
                } else {
//                    System.out.println("fehler ist hier");
                    LoggerHandler.logger.severe("Error Email was not sended to the user.");
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sends an Email to all Participants of the Event when the reminder is
     * activated
     *
     * @param event Event which should be send as a reminder.
     */
    public void emailSenderAppointmentReminder(Event event) {
        String from = "Javprojekt@gmail.com"; // from address. As this is using Gmail SMTP your from address should be gmail
        String password = "Javaprojekt123"; // password for from gmail address that you have used in above line. 
        String time = timeFormater();
        String notify = null;
        switch (event.getNotification()) {
            case TEN_MINUTES:
                notify = "Your meeting: \"" + event.getName() + "\" will start in 10 minutes";
                break;
            case ONE_HOUR:
                notify = "Your meeting: \"" + event.getName() + "\" will start in an Hour";
                break;
            case THREE_DAYS:
                notify = "Your meeting: \"" + event.getName() + "\" will start " + event.getDate().getDayOfWeek();
                break;
            case ONE_WEEK:
                notify = "Your meeting: \"" + event.getName() + "\" will start in one week";
                break;
        }
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for (int i = 0; i < participants.size(); i++) {
                System.out.println(participants.get(i).getLastName());
                if (participants.get(i).getEmail() != null && !participants.get(i).getEmail().isEmpty()) {
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(participants.get(i).getEmail()));
                    // Edit benachrichtigung
                    message.setSubject("Event reminder");
                    message.setText("Dear Mrs/Mr" + participants.get(i).getLastName() + ",\n\n"
                            + notify + " on the: " + event.getDate().getDayOfMonth() + "." + event.getDate().getMonth() + "." + event.getDate().getYear() + " at: " + time);

                    Transport.send(message);
                    LoggerHandler.logger.info("Email was send to notify all participants about the event time (Reminder)");
//                    System.out.println("Mail Sent...");
                } else {
//                    System.out.println("fehler ist hier");
                    LoggerHandler.logger.severe("Error Email was not sended to the user.");
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     */
    @Override
    /**
     * The run method is called if the thread was constructed using separate
     * Runnable object otherwise this method does nothing and returns. The run
     * method checks with an switch operation which email will be triggered and
     * sended to the users email address.
     */
    public void run() {
        switch (this.operation) {
            case VERIFY_ACCOUNT:
                this.emailSenderVerifyAccount(this.user, this.rand);
                break;
            case VERIFY_EMAIL:
                this.emailSenderVerifyEmail(this.email, this.rand);
                break;
            case CREATE_EVENT:
                this.emailSenderAddEvent(this.event);
                break;
            case EDIT_EVENT:
                this.emailSenderEditEvent(this.event);
                break;
            case DELETE_EVENT:
                this.emailSenderDeleteEvent(this.event);
                break;
            case REMIND_EVENT:
                this.emailSenderAppointmentReminder(this.event);
                break;
            default:
                return;
        }
    }
}
