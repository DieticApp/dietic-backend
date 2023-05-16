package com.sardicus.dietic.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FirebaseConfig {
    @PostConstruct
    public void init() throws IOException {
        InputStream inputStream = FirebaseConfig.class.getClassLoader().getResourceAsStream("dietic-firebase.json");
        File tempFile = File.createTempFile("dietic-firebase", ".json");
        tempFile.deleteOnExit();

        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        FileInputStream serviceAccount = new FileInputStream(tempFile);
      FirebaseOptions options = new FirebaseOptions.Builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .build();
      FirebaseApp.initializeApp(options);
    }



    }


