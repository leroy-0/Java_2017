package com.area.jersey;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.Profile;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.mortbay.util.ajax.JSON;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public class GmailEmail {

    public static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException, javax.mail.MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("notifications.area@gmail.com", "epitech59");
                    }
                });
        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException, javax.mail.MessagingException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    public static Message sendMessage(Gmail service, String userId, MimeMessage emailContent) throws MessagingException, IOException, javax.mail.MessagingException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();
        //System.out.println("Message id: " + message.getId());
        //System.out.println(message.toPrettyString());
        return message;
    }

    public static String getBodyText(String text, String keyword) {
        String msg = "Hello,\n\nYou have a new twitter with your listening keyword: " + keyword + ".\n\n";
        msg = msg.concat("- " + text);
        msg = msg.concat("\n\n\n\nAREA Interface - © Copyright epibros 2017");
        return msg;
    }

    public static String getBodyTextFb(String text, String keyword) {
        String msg = "Hello,\n\nYou have  post a new status: " + keyword + ".\n\n";
        msg = msg.concat("- " + text);
        msg = msg.concat("\n\n\n\nAREA Interface - © Copyright epibros 2017");
        return msg;
    }
    public static String getSubjectFb(String keyword) {
        return ("AREA: New post with " + keyword + " keyword");
    }

    public static String getSubject(String keyword) {
        return ("AREA: New tweet with " + keyword + " keyword");
    }

    public static String getEmailAddress(Gmail service) throws IOException {
        Profile user = service.users().getProfile("me").execute();

        System.out.println("\n\n\n\n\n\nGet fields: " + user.getEmailAddress() + "\n\n\n\n\n\n");
        return user.getEmailAddress();
    }
}
