package com.chatop.api.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.LoginDto;
import com.chatop.api.model.UserEntity;
import com.chatop.api.service.UserService;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    /* Login user */
    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Sucessfully logged in", HttpStatus.OK);
    }

    /* Register user */
    @PostMapping("/api/auth/register")
    public ResponseEntity<String> register(@RequestBody UserEntity userDto) {
        /* Check email (must be unique) */
        if(!userService.getUserByEmail(userDto.getEmail()).isEmpty()) {
            return new ResponseEntity<>("This email address is already used", HttpStatus.BAD_REQUEST);
        }
        /* Check fields */
        if(userDto.getName().isEmpty() || userDto.getEmail().isEmpty() || userDto.getPassword().isEmpty()) {
            return new ResponseEntity<>("Some fields are empty", HttpStatus.BAD_REQUEST);
        }

        /* Create new user */
        UserEntity user = new UserEntity();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());

        /* Getting current date and time */
        long timeStampInMillis = System.currentTimeMillis();
        Timestamp timeStampNow = new Timestamp(timeStampInMillis);
        user.setCreatedAt(timeStampNow);
        user.setUpdatedAt(timeStampNow);

        /* Save user in DB */
        userService.saveUser(user);
        return new ResponseEntity<>("Successfully Registered", HttpStatus.OK);

    }

}
