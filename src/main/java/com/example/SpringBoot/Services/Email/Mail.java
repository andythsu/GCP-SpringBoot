package com.example.SpringBoot.Services.Email;

import com.example.SpringBoot.Services.Datastore.DatastoreService;
import com.example.SpringBoot.Services.Error.MessageKey;
import com.example.SpringBoot.Services.Error.WebRequestException;
import com.google.cloud.datastore.Entity;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.Properties;


/**
 * @author: Andy Su
 * @Date: 11/1/2018
 */
@Component
public class Mail {

    public void sendEmail(MailImp mail){
       MailContent content = mail.getContent();
       MailUserCredential credential = mail.getCredential();
       sendEmail(content, credential);
    }

    private void sendEmail(MailContent mailContent, MailUserCredential userCredential) {
        if (userCredential == null) {
            userCredential = initDefaultUser();
        }

        String userName = userCredential.getUserName();
        String password = userCredential.getPassword();
        String recipient = mailContent.getRecipient();
        String subject = mailContent.getSubject();
        String body = mailContent.getBody();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new WebRequestException(new MessageKey(
                    HttpURLConnection.HTTP_INTERNAL_ERROR,
                    MessageKey.MessageKeyTags.RUN_TIME_ERROR,
                    e.getMessage()
            ));
        }
    }

    private MailUserCredential initDefaultUser() {
        String credential_col = "credential";
        String user_col = "Username";
        String pwd_col = "Password";

        Iterator<Entity> data = DatastoreService.getLastUpdatedByKind(credential_col);
        MailUserCredential credential = null;
        while (data.hasNext()) {
            Entity en = data.next();
            credential = new MailUserCredential(en.getString(user_col), en.getString(pwd_col));
        }

        if (credential == null) throw new WebRequestException(MessageKey.UNAUTHORIZED);

        return credential;
    }

}
