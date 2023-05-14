package com.sardicus.dietic.service;


import com.google.firebase.auth.FirebaseAuthException;
import com.sardicus.dietic.dto.PasswordChangeRequest;
import com.sardicus.dietic.dto.UserDto;

public interface UserService {
    UserDto updatePicOfUser(Long userId, UserDto user);
    UserDto getUserById(Long userId);
    void changePassword(Long userId, PasswordChangeRequest passwordChangeRequest) throws FirebaseAuthException;


}
