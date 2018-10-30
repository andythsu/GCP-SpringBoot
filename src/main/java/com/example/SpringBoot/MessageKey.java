package com.example.SpringBoot;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MessageKey extends ResponseEntity {

    public static MessageKey defaultMessage = new MessageKey("default message", HttpStatus.OK);

    public MessageKey(Object body, HttpStatus status) {
        super(body, status);
    }

}
