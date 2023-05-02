package com.sardicus.dietic.config;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.sardicus.dietic.dto.LoginDto;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseConfig {
    @PostConstruct
    public void init() throws IOException {
        FileInputStream serviceAccount = new FileInputStream( "./dietic-firebase.json");
      FirebaseOptions options = new FirebaseOptions.Builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .build();
      FirebaseApp.initializeApp(options);
    }



    }


