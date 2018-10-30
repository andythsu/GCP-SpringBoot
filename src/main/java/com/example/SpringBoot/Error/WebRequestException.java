package com.example.SpringBoot.Error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;


public class WebRequestException extends RuntimeException {

    private final Logger logger = LoggerFactory.getLogger(WebRequestException.class);

    private int status;
    private final String key;
    private final String[] properties;
    private String defaultMessage;

    public WebRequestException(final int status, final MessageKey messageKey) {
        this(status, messageKey, new Object[]{});
    }

    public WebRequestException(MessageKey messageKey){
        this(messageKey.getStatus(), messageKey, new Object[]{});
    }

    public WebRequestException(final int status, final MessageKey messageKey, Object... parameters) {
        this.status = status;
        try {
            defaultMessage = MessageFormat.format(messageKey.getDefaultMessage(), parameters);
        } catch (final Exception e) {
            defaultMessage = messageKey.getDefaultMessage();
            logger.error("Error formatting message with key: {}.", messageKey.getTag(), e);
        }
        properties = new String[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            properties[i] = parameters[i] == null ? null : parameters[i].toString();
        }

        key = messageKey.getTag();
    }


    @Override
    public String getMessage() {
        return new StringBuilder()
                .append("Status: ").append(status)
                .append(" - ")
                .append("Key: ").append(key)
                .append(" - ")
                .append("Default Message: ").append(defaultMessage)
                .toString();
    }

    public int getStatus() {
        return status;
    }

    public String getKey() {
        return key;
    }

    public String[] getProperties() {
        return properties;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }


}
