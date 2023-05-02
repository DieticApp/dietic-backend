package com.sardicus.dietic.service.impl;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseService {

    public void sendToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("data");

        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello, Firebase!");

        ref.setValueAsync(data);
    }
}