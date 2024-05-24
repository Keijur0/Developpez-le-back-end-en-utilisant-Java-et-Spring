package com.chatop.api.service;

import org.springframework.stereotype.Service;

import com.chatop.api.dto.UserDto;
import com.chatop.api.model.UserEntity;
import com.chatop.api.repository.UserRepository;


@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* Convert UserEntity to UserDto */
    private UserDto toDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setCreated_at(user.getCreated_at());
        userDto.setUpdated_at(user.getUpdated_at());
        return userDto;
    }

    /* Get user by id */
    public UserDto getUserById(final Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow();
        UserDto userDto = toDto(user);
        return userDto;
    }
}
