package com.example.SpringBoot.Services.Email;

import org.springframework.stereotype.Component;

@Component
public class MailContent {
    private String subject = "";
    private String body = "";
    // default sender
    private String sender = "sbsp19990501@gmail.com";
    // default recipient
    private String recipient = "f2280c@gmail.com";

    public MailContent subject(String subject){
        this.subject = subject;
        return this;
    }
    public MailContent body(String body){
        this.body = body;
        return this;
    }

    public MailContent sender(String sender){
        this.sender = sender;
        return this;
    }

    public MailContent recipient(String recipient){
        this.recipient = recipient;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }
}
