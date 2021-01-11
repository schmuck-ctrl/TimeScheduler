/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import classes.*;

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
public class EmailHandler {

    public static void emailSenderAddEvent(Operator orginater, ArrayList<Operator> participants) {
        //String to = ""; // to address. It can be any like gmail, yahoo etc.
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
            for (int i = 0; i <= participants.size(); i++) {
                System.out.println(participants.get(i).getLastName());
                if (participants.get(i).getEmail() != null && !participants.get(i).getEmail().isEmpty()) {
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(participants.get(i).getEmail()));
                    message.setSubject("You : " + participants.get(i).getLastName() + "were invited to a Appointment from");
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

    public static void emailSenderEditEvent(Operator orginater, ArrayList<Operator> participants) {
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
            for (int i = 0; i <= participants.size(); i++) {
                System.out.println(participants.get(i).getLastName());
                if (participants.get(i).getEmail() != null && !participants.get(i).getEmail().isEmpty()) {
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(participants.get(i).getEmail()));
                    // Edit benachrichtigung
                    message.setSubject("You : " + participants.get(i).getLastName() + "were invited to a Appointment from");
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
}
