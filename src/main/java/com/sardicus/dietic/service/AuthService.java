package com.sardicus.dietic.service;


import com.sardicus.dietic.dto.LoginDto;
import com.sardicus.dietic.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}