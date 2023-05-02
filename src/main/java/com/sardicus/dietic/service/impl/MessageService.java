package com.sardicus.dietic.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.sardicus.dietic.dto.MessageDto;
import com.sardicus.dietic.dto.RoomDto;
import com.sardicus.dietic.dto.LoginDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MessageService {
    Firestore db = FirestoreClient.getFirestore();
    public String saveToUsers(LoginDto loginDto) throws ExecutionException, InterruptedException {

        ApiFuture<WriteResult> api = db.collection("Users").document(loginDto.getEmail()).set(loginDto);
       return "User added to firestore";
    }
    public void saveToRooms(RoomDto roomDto, MessageDto messageDto) throws ExecutionException, InterruptedException {
        WriteBatch batch = db.batch();

        DocumentReference roomRef = db.collection("Rooms").document();
        batch.set(roomRef, roomDto);

        DocumentReference messageRef = roomRef.collection("messages").document();
        batch.set(messageRef, messageDto);

        ApiFuture<List<WriteResult>> future = batch.commit();

    }
    public void registerInFirebase(LoginDto loginDto) throws FirebaseAuthException {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(loginDto.getEmail())
                .setPassword(loginDto.getPassword())
                .setDisabled(false);
        UserRecord userRecord = auth.createUser(request);
    }
}
