package com.chatop.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.UserDto;
import com.chatop.api.service.UserService;

@RestController
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable final Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
