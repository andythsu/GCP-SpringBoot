package com.example.SpringBoot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class WebRequestError extends Throwable {
    public WebRequestError(MessageKey message) {
        super(message.toString());
    }


}
