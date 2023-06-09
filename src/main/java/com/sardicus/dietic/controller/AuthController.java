package com.sardicus.dietic.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.sardicus.dietic.dto.LoginDto;
import com.sardicus.dietic.dto.RegisterDto;
import com.sardicus.dietic.response.JWTAuthResponse;
import com.sardicus.dietic.service.AuthService;
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

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto ){
        return new ResponseEntity<>( authService.login(loginDto) , HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<JWTAuthResponse> registerUser(@RequestBody RegisterDto registerDto) throws ExecutionException, InterruptedException, FirebaseAuthException {
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }
}
