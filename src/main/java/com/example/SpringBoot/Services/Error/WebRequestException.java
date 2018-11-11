package com.example.SpringBoot.Services.Error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


public class WebRequestException extends RuntimeException{

    private final Logger logger = LoggerFactory.getLogger(WebRequestException.class);

    private MessageKey messageKey;
    private String[] errorProperties;

    public WebRequestException(MessageKey messageKey){
        this.messageKey = messageKey;
    }

    public WebRequestException(MessageKey messageKey, Object... objects){
        this.messageKey = messageKey;
        String[] errorProperties = new String[objects.length];
        for (int i=0; i<errorProperties.length; i++){
            errorProperties[i] = objects[i].toString();
        }
        this.errorProperties = errorProperties;
    }

    public MessageKey getMessageKey(){
        return messageKey;
    }

    public String[] getErrorProperties(){
        return errorProperties;
    }
}
