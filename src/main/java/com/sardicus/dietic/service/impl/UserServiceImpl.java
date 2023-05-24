package com.sardicus.dietic.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.sardicus.dietic.dto.PasswordChangeRequest;
import com.sardicus.dietic.dto.UserDto;
import com.sardicus.dietic.entity.User;
import com.sardicus.dietic.exception.InvalidPasswordException;
import com.sardicus.dietic.exception.ResourceNotFoundException;
import com.sardicus.dietic.repo.UserRepo;
import com.sardicus.dietic.service.MessageService;
import com.sardicus.dietic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final MessageService messageService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDto updatePicOfUser(Long userId, UserDto userDto) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id:", userId));

        user.setPicture(userDto.getPicture());
        User updatedUser = userRepo.save(user);

        return mapToDTO(updatedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id:", userId));
        return mapToDTO(user);
    }

    @Override
    public void changePassword(Long userId, PasswordChangeRequest passwordChangeRequest) throws FirebaseAuthException {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id:", userId));

        if (!passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }
        String newPassword = passwordChangeRequest.getNewPassword();
        String encodedPassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());

        user.setPassword(encodedPassword);
         userRepo.save(user);
         messageService.changePassword(user.getEmail(),newPassword);
    }

    public UserDto mapToDTO(User user){
        return mapper.map(user,UserDto.class);
    }
}
