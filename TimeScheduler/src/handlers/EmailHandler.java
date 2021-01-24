/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import classes.*;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
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
        VERIFY_ACCOUNT,VERIFY_EMAIL,CREATE_EVENT, EDIT_EVENT, DELETE_EVENT, REMIND_EVENT;
    }
    private Operator user;
    private ArrayList<Operator> participants;
    private Event event;
    private int rand;
    private Operator organizer;
    private MailOperation operation;
    private String email;
    
    public EmailHandler(String email,int rand){
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
                text.append("Participant of the event: " + participants.get(i).getLastName() + ", " + participants.get(i).getFirstName());
                text.append("\n");
            }
            for (int i = 0; i < participants.size(); i++) {
                System.out.println(participants.get(i).getLastName());
                if (participants.get(i).getEmail() != null && !participants.get(i).getEmail().isEmpty()) {
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(participants.get(i).getEmail()));
                    message.setSubject("You where invited to the: \"" + event.getName() + "\" meeting");
                    message.setText("Dear Mrs/Mr " + participants.get(i).getLastName() + ",\n"
                            + "All informations about the new meeting are below." + "\n"
                            + "Appointment name: " + event.getName() + "\n"
                            + "Meeting owner: " + organizer.getLastName() + "\n"
                            + "Date: " + event.getDate() + "\n"
                            + "Meeting duration: " + event.getDuration() + " Minutes" + "\n"
                            + "Place: " + event.getLocation() + "\n"
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
                text.append("Participant of the event: " + participants.get(i).getLastName() + ", " + participants.get(i).getFirstName());
                text.append("\n");
            }
            for (int i = 0; i < participants.size(); i++) {

                System.out.println(participants.get(i).getLastName());
                if (participants.get(i).getEmail() != null && !participants.get(i).getEmail().isEmpty()) {
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(participants.get(i).getEmail()));

                    message.setSubject("The event \"" + event.getName() + "\" was updated");
                    message.setText("Dear Mrs/Mr " + participants.get(i).getLastName() + ",\n"
                            + "All informations about the updated meeting are below." + "\n"
                            + "Appointment name: " + event.getName() + "\n"
                            + "Meeting owner: " + organizer.getLastName() + "\n"
                            + "Date: " + event.getDate() + "\n"
                            + "Meeting duration: " + event.getDuration() + " Minutes" + "\n"
                            + "Place: " + event.getLocation() + "\n"
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

                    message.setSubject("The meeting: \"" + event.getName() + "\" was cancelled");
                    message.setText("appointment info");

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
//        String from = "Javprojekt@gmail.com"; // from address. As this is using Gmail SMTP your from address should be gmail
//        String password = "Javaprojekt123"; // password for from gmail address that you have used in above line. 
//
//        Properties prop = new Properties();
//        prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.port", "465");
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.socketFactory.port", "465");
//        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(from, password);
//            }
//        });
//
//        try {
//
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            for (int i = 0; i < participants.size(); i++) {
//                System.out.println(participants.get(i).getLastName());
//                if (participants.get(i).getEmail() != null && !participants.get(i).getEmail().isEmpty()) {
//                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(participants.get(i).getEmail()));
//                    // Edit benachrichtigung
//                    message.setSubject("You : " + participants.get(i).getLastName() + "Event:" + "was deleted");
//                    message.setText("appointment info");
//
//                    Transport.send(message);
//
//                    System.out.println("Mail Sent...");
//                } else {
//                    System.out.println("fehler ist hier");
//                }
//            }
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

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
