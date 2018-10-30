package com.example.SpringBoot.Error;

import java.net.HttpURLConnection;

public class MessageKey {

    private final String tag;
    private final String defaultMessage;
    private final int status;

    public static final MessageKey INVALID_PARAM = Builder()
            .buildTag(MessageKeyTags.INVALID_PARAM)
            .buildMessage("Invalid Parameter")
            .buildStatus(HttpURLConnection.HTTP_BAD_REQUEST)
            .build();

    public static final MessageKey INVALID_JSON = Builder()
            .buildTag(MessageKeyTags.INVALID_JSON)
            .buildMessage("Invalid JSON format")
            .buildStatus(HttpURLConnection.HTTP_BAD_REQUEST)
            .build();


    public static class MessageKeyTags {
        public static final String DATA_ERROR = "DATA-ERROR";
        public static final String INVALID_JSON = "INVALID-JSON";
        public static final String INVALID_PARAM = "INVALID-PARAM";
        public static final String NETWORK_ERROR = "NETWORK-ERROR";
    }


    public static class Builder {
        private String tag;
        private String defaultMessage;
        private int status = -1;

        public Builder buildTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder buildStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder buildMessage(String message) {
            this.defaultMessage = message;
            return this;
        }

        public MessageKey build() {
            return new MessageKey(this.status, this.tag, this.defaultMessage);
        }
    }

    public static Builder Builder(){
        return new Builder();
    }

    private MessageKey(final int status, final String tag, final String defaultMessage) {
        this.tag = tag;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }


    public String getTag() {
        return tag;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public int getStatus() {
        return status;
    }

}
