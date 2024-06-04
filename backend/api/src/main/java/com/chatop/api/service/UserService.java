package com.chatop.api.service;

import org.springframework.stereotype.Service;

import com.chatop.api.dto.UserDto;
import com.chatop.api.mapper.UserMapper;
import com.chatop.api.model.UserEntity;
import com.chatop.api.repository.UserRepository;


@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* Get user by id */
    public UserDto getUserById(final Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow();
        UserDto userDto = UserMapper.INSTANCE.userEntityToUserDto(user);
        return userDto;
    }
}
