package com.sardicus.dietic.service;


import com.sardicus.dietic.dto.UserDto;

public interface UserService {
    UserDto updatePicOfUser(Long userId, UserDto user);
    UserDto getUserById(Long userId);


}
