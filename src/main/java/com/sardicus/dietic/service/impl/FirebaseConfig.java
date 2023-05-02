package com.sardicus.dietic.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseConfig {

    @PostConstruct
    public void initialize() throws IOException {
        InputStream serviceAccount = this.getClass().getResourceAsStream("dietic-firebase.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://dietic-chat.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }
}