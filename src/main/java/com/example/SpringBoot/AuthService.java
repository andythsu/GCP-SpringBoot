package com.example.SpringBoot;

import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Component;

@Component
public class GoogleAuthService {
    public GoogleCredentials getCredentials(){
        return ComputeEngineCredentials.create();
    }
}
