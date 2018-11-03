package com.example.SpringBoot.AuthREST;


//import org.github.andythsu.GCP.Services.Error.MessageKey;

import org.github.andythsu.GCP.Services.Error.MessageKey;

public class AuthMessageKey extends MessageKey {
    private static String tag = "AUTH-ERROR";
    public static final String TOKEN_EXPIRED = "Token is expired. Request a new one";
    public AuthMessageKey(int status, String message){
        super(status, tag, message);
    }

}
