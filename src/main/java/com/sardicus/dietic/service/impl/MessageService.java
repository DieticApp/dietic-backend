package com.sardicus.dietic.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.sardicus.dietic.dto.MessageDto;
import com.sardicus.dietic.dto.RoomDto;
import com.sardicus.dietic.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final PasswordEncoder passwordEncoder;

    Firestore db = FirestoreClient.getFirestore();
    public String saveToUsers(LoginDto loginDto) throws ExecutionException, InterruptedException {
        String encodePassword = passwordEncoder.encode(loginDto.getPassword());
        loginDto.setPassword(encodePassword);
        ApiFuture<WriteResult> api = db.collection("Users").document(loginDto.getEmail()).set(loginDto);
       return "User added to firestore";
    }
    public void changePassword(String email, String newPassword) throws FirebaseAuthException {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        UserRecord userRecord = firebaseAuth.getUserByEmail(email);

        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(userRecord.getUid())
                .setPassword(newPassword);

        firebaseAuth.updateUser(request);

        DocumentReference userRef = db.collection("Users").document(email);
        String encodedPassword = passwordEncoder.encode(newPassword);
        userRef.update("password", encodedPassword);
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
                .setUid(loginDto.getEmail())
                .setDisabled(false);
        UserRecord userRecord = auth.createUser(request);


    }

}
