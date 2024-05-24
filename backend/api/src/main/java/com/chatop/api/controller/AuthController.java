package com.chatop.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.LoginDto;
import com.chatop.api.dto.UserDto;
import com.chatop.api.model.AuthResponse;
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
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    /* Register user */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserEntity userDto) {
        if(userDto.getEmail() == null || userDto.getName() == null || userDto.getPassword() == null) {
            Map<String, String> registerReponse = new HashMap<>();
            registerReponse.put("message", "At least one field is empty");
            return ResponseEntity.badRequest().body(registerReponse);
        }
        return ResponseEntity.ok(authService.register(userDto));
    }

    /* Display "Me" page after Login and Register */
    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        return ResponseEntity.ok(authService.me());
    }
}
