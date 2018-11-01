package com.example.SpringBoot.Error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class WebRequestException extends RuntimeException{

    private final Logger logger = LoggerFactory.getLogger(WebRequestException.class);

    private MessageKey messageKey;

    public WebRequestException(MessageKey messageKey){
        this.messageKey = messageKey;
    }

    public MessageKey getMessageKey(){
        return messageKey;
    }
}
