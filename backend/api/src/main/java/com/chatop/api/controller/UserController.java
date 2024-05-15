package com.chatop.api.controller;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.User;
import com.chatop.api.service.UserService;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /* Create a user */
    @PostMapping("api/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody User userDTO) {
        /* Check fields */
        if(userDTO.getName().isEmpty() || userDTO.getEmail().isEmpty() || userDTO.getPassword().isEmpty()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        
        /* Create a new user */
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        /* Getting current date and time */
        long timeStampInMillis = System.currentTimeMillis();
        Timestamp timeStampNow = new Timestamp(timeStampInMillis);
        user.setCreatedAt(timeStampNow);
        user.setUpdatedAt(timeStampNow);

        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.OK).body("Register sucessful!");
    }

    

}
