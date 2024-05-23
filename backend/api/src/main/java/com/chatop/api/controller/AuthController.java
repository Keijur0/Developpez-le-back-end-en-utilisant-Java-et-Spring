package com.chatop.api.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.LoginDto;
import com.chatop.api.dto.MeDto;
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
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto) {
        if(authService.login(loginDto).get("token") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authService.login(loginDto));
        }
            return ResponseEntity.ok(authService.login(loginDto));

    }

    /* Register user */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserEntity userDto) {
        if(authService.register(userDto).get("token") == null) {
            return ResponseEntity.badRequest().body(authService.register(userDto));
        }
        return ResponseEntity.ok(authService.register(userDto));
    }

    /* Display "Me" page after Login and Register */
    @GetMapping("/me")
    public ResponseEntity<MeDto> me() {
        return ResponseEntity.ok(authService.me());
    }
}
