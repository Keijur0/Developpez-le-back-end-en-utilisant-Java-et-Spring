package com.chatop.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /* Create/Update user */
    public User saveUser (User user) {
        User createdUser = userRepository.save(user);
        return createdUser;
    }

    /* Get user by id */
    public Optional<User> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    /* Delete user by id */
    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    /* Get all users */
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    /* Get user by email */
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }
}