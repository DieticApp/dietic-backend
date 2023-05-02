package com.sardicus.dietic.controller;

import com.sardicus.dietic.dto.LoginDto;
import com.sardicus.dietic.dto.RegisterDto;
import com.sardicus.dietic.response.JWTAuthResponse;
import com.sardicus.dietic.service.AuthService;
import com.sardicus.dietic.service.impl.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MessageService messageService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto ){
        return new ResponseEntity<>( authService.login(loginDto) , HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<JWTAuthResponse> registerUser(@RequestBody RegisterDto registerDto) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }
}
