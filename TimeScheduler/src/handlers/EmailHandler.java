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
import java.util.Locale;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Vadym
 */
public class EmailHandler implements Runnable {

    public enum MailOperation {
        VERIFY_ACCOUNT, VERIFY_EMAIL, CREATE_EVENT, EDIT_EVENT, DELETE_EVENT, REMIND_EVENT;
    }
    private Operator user;
    private ArrayList<Operator> participants;
    private Event event;
    private int rand;
    private Operator organizer;
    private MailOperation operation;
    private String email;

    public EmailHandler(String email, int rand) {
        this.operation = MailOperation.VERIFY_EMAIL;
        this.email = email;
        this.rand = rand;
    }

    public EmailHandler(Operator user, int rand) {
        this.operation = MailOperation.VERIFY_ACCOUNT;
        this.user = user;
        this.rand = rand;
    }

    public EmailHandler(MailOperation operation, Event event) {
        this.operation = operation;
        this.organizer = event.getHost();
        this.participants = event.getParticipants();
        this.event = event;
    }

    public String timeFormater() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.of(event.getDate().getHour(), event.getDate().getMinute());
        String time = formatter.format(localTime);
        return time;
    }

    public void emailSenderVerifyAccount(Operator user, int rand) {
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
            System.out.println(user.getLastName());
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
                message.setSubject("Verification Number");
                message.setText("Your Verification code: " + rand);

                Transport.send(message);

                System.out.println("Mail Sent...");
            } else {
                System.out.println("fehler ist hier");
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

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

                System.out.println("Mail Sent...");
            } else {
                System.out.println("fehler ist hier");
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

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

                    Transport.send(message);

                    System.out.println("Mail Sent...");
                } else {
                    System.out.println("fehler ist hier");
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

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

                    System.out.println("Mail Sent...");
                } else {
                    System.out.println("fehler ist hier");
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

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

                    message.setSubject("Your : " + participants.get(i).getLastName() + "Event:" + "was deleted");
                    message.setText("Dear Mrs/Mr" + participants.get(i).getLastName() + ",\n"
                            + "the meeting: " + event.getName() + " on the: " + event.getDate().getDayOfMonth()+ "." + event.getDate().getMonth() + "." + event.getDate().getYear() + " at: " + time + " was deleted.");

                    Transport.send(message);

                    System.out.println("Mail Sent...");
                } else {
                    System.out.println("fehler ist hier");
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sends an Email to all Participants of the Event
     *
     * @param event
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
                        + notify +" on the: " + event.getDate().getDayOfMonth()+ "." + event.getDate().getMonth() + "." + event.getDate().getYear() + " at: " + time);

                    Transport.send(message);

                    System.out.println("Mail Sent...");
                } else {
                    System.out.println("fehler ist hier");
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
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
