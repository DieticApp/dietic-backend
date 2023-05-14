package com.sardicus.dietic.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.sardicus.dietic.dto.PasswordChangeRequest;
import com.sardicus.dietic.dto.UserDto;
import com.sardicus.dietic.repo.UserRepo;
import com.sardicus.dietic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepo userRepo;
    @PutMapping("/updatePicture")
    public UserDto updatePicOfUser(@AuthenticationPrincipal UserDetails user, @RequestBody UserDto userDto){
       Long userId = userRepo.findByEmail(user.getUsername()).get().getId();
        return userService.updatePicOfUser(userId,userDto);
    }
    @GetMapping("/getPicture")
    public UserDto getPicture(@AuthenticationPrincipal UserDetails user){
        Long userId = userRepo.findByEmail(user.getUsername()).get().getId();
        return userService.getUserById(userId);
    }
    @PostMapping("/change")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest,
                                                 @AuthenticationPrincipal UserDetails user) throws FirebaseAuthException {
        Long userId = userRepo.findByEmail(user.getUsername()).get().getId();
        userService.changePassword(userId, passwordChangeRequest);
        return ResponseEntity.ok("Password changed successfully");
    }

}
