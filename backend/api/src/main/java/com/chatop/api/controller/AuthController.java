package com.chatop.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.LoginDto;
import com.chatop.api.model.UserEntity;
import com.chatop.api.service.AuthService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /* Login user */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok("token: "+authService.login(loginDto).getToken());
    }

    /* Register user */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity userDto) {
        return ResponseEntity.ok("token :"+authService.register(userDto).getToken());
    }

    /* Display "Me" page after Login and Register */
    @GetMapping("/me")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("welcome on me page");
    }
}
