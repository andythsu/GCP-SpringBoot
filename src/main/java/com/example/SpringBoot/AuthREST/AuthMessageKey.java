package com.example.SpringBoot;

import org.github.andythsu.GCP.Services.Error.MessageKey;

public class AuthMessageKey extends MessageKey {
    private static String tag = "AUTH-ERROR";
    public AuthMessageKey(int status, String message){
        super(status, tag, message);
    }

}
