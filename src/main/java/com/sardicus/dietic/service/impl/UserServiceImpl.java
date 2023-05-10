package com.sardicus.dietic.service.impl;

import com.sardicus.dietic.dto.UserDto;
import com.sardicus.dietic.entity.Appointment;
import com.sardicus.dietic.entity.User;
import com.sardicus.dietic.exception.ResourceNotFoundException;
import com.sardicus.dietic.repo.UserRepo;
import com.sardicus.dietic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper mapper;
    @Override
    public UserDto updatePicOfUser(Long userId, UserDto userDto) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id:", userId));

        user.setPicture(userDto.getPicture());
        User updatedUser = userRepo.save(user);

        return mapToDTO(updatedUser);
    }
    public UserDto mapToDTO(User user){
        return mapper.map(user,UserDto.class);
    }
}
