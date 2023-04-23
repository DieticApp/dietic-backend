package com.sardicus.dietic.service;


import com.sardicus.dietic.dto.LoginDto;
import com.sardicus.dietic.dto.RegisterDto;
import com.sardicus.dietic.response.JWTAuthResponse;

public interface AuthService {
    JWTAuthResponse login(LoginDto loginDto);

    JWTAuthResponse register(RegisterDto registerDto);
}