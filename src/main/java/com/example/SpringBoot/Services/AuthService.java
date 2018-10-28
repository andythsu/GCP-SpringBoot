package com.example.SpringBoot.Services;

import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Component;

//@Component
public class AuthService {
    public GoogleCredentials getCredentials(){
        return ComputeEngineCredentials.create();
    }
}
