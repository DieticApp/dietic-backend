package com.sardicus.dietic.controller;

import com.sardicus.dietic.dto.UserDto;
import com.sardicus.dietic.repo.UserRepo;
import com.sardicus.dietic.service.UserService;
import lombok.RequiredArgsConstructor;
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

}
