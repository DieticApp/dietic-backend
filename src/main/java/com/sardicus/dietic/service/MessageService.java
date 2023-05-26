package com.sardicus.dietic.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.sardicus.dietic.dto.*;

import java.util.concurrent.ExecutionException;

public interface MessageService {
     String saveToUsers(FirestoreDto firestoreDto) throws ExecutionException, InterruptedException;
     void changePassword(String email, String newPassword) throws FirebaseAuthException;
     void saveToRooms(RoomDto roomDto, MessageDto messageDto) throws ExecutionException, InterruptedException;
     void registerInFirebase(LoginDto loginDto) throws FirebaseAuthException;
     void deletePatient(PatientDto patientDto) throws FirebaseAuthException;
}
