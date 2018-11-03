package Services.Error;

import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;

@Component
public class MessageKey {

    private static int status = 500;
    private static String tag = "GENERIC-TAG";
    private static String defaultMessage = "GENERIC-MESSAGE";

    public static final MessageKey INVALID_PARAM = new MessageKey()
            .tag(MessageKeyTags.INVALID_PARAM)
            .message("Invalid Parameter")
            .status(HttpURLConnection.HTTP_BAD_REQUEST);

    public static final MessageKey INVALID_JSON = new MessageKey()
            .tag(MessageKeyTags.INVALID_JSON)
            .message("Invalid JSON format")
            .status(HttpURLConnection.HTTP_BAD_REQUEST);

    public static final MessageKey DATA_FETCH_ERROR = new MessageKey()
            .tag(MessageKeyTags.DATA_ERROR)
            .message("Error Fetching Data")
            .status(HttpURLConnection.HTTP_INTERNAL_ERROR);

    public static final MessageKey NETWORK_ERROR = new MessageKey()
            .tag(MessageKeyTags.NETWORK_ERROR)
            .message("Network Error")
            .status(HttpURLConnection.HTTP_GATEWAY_TIMEOUT);

    public static final MessageKey UNAUTHORIZED = new MessageKey()
            .tag(MessageKeyTags.CREDENTIAL_ERROR)
            .message("Invalid Credentials")
            .status(HttpURLConnection.HTTP_UNAUTHORIZED);

    public static final MessageKey INVALID_TOKEN = new MessageKey()
            .tag(MessageKeyTags.TOKEN_ERROR)
            .message("Invalid token")
            .status(HttpURLConnection.HTTP_UNAUTHORIZED);

    public static final MessageKey EXPIRED_TOKEN = new MessageKey()
            .tag(MessageKeyTags.TOKEN_ERROR)
            .message("Token has expired. Request a new one")
            .status(HttpURLConnection.HTTP_UNAUTHORIZED);


    public static class MessageKeyTags {
        public static final String DATA_ERROR = "DATA-ERROR";
        public static final String INVALID_JSON = "INVALID-JSON";
        public static final String INVALID_PARAM = "INVALID-PARAM";
        public static final String NETWORK_ERROR = "NETWORK-ERROR";
        public static final String CREDENTIAL_ERROR = "CREDENTIAL-ERROR";
        public static final String TOKEN_ERROR = "TOKEN-ERROR";
        public static final String RUN_TIME_ERROR = "RUN-TIME-ERROR";
    }

    public MessageKey(final int status, final String tag, final String defaultMessage) {
        this.status = status;
        this.tag = tag;
        this.defaultMessage = defaultMessage;
    }

    public MessageKey(){ }

    public MessageKey tag(String tag){
        this.tag = tag;
        return this;
    }

    public MessageKey status(int status){
        this.status = status;
        return this;
    }

    public MessageKey message(String defaultMessage){
        this.defaultMessage = defaultMessage;
        return this;
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
