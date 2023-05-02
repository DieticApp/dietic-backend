package com.sardicus.dietic.service;


import com.sardicus.dietic.dto.LoginDto;
import com.sardicus.dietic.dto.RegisterDto;
import com.sardicus.dietic.response.JWTAuthResponse;

import java.util.concurrent.ExecutionException;

public interface AuthService {
    JWTAuthResponse login(LoginDto loginDto);

    JWTAuthResponse register(RegisterDto registerDto) throws ExecutionException, InterruptedException;
}