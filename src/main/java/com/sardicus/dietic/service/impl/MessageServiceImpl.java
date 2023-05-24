package com.sardicus.dietic.service.impl;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteBatch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.sardicus.dietic.dto.FirestoreDto;
import com.sardicus.dietic.dto.LoginDto;
import com.sardicus.dietic.dto.MessageDto;
import com.sardicus.dietic.dto.RoomDto;
import com.sardicus.dietic.repo.UserRepo;
import com.sardicus.dietic.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    Firestore db = FirestoreClient.getFirestore();
    public String saveToUsers(FirestoreDto firestoreDto) throws ExecutionException, InterruptedException {
        String pictureUrl = "https://firebasestorage.googleapis.com/v0/b/dietic-chat.appspot.com/o/user.png?alt=media&token=d2ffc257-90bc-41d6-8c4d-cd7d655ed4a5";
        Timestamp timestamp = Timestamp.now();
        String encodePassword = passwordEncoder.encode(firestoreDto.getPassword());
        firestoreDto.setPassword(encodePassword);
        firestoreDto.setDate_time(timestamp);
        firestoreDto.setProfile_pic(pictureUrl);

       db.collection("Users").document(firestoreDto.getEmail()).set(firestoreDto);

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

         batch.commit();

    }
    public void registerInFirebase(LoginDto loginDto) throws FirebaseAuthException {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(loginDto.getEmail())
                .setPassword(loginDto.getPassword())
                .setUid(loginDto.getEmail())
                .setDisabled(false);

        auth.createUser(request);

    }

}
