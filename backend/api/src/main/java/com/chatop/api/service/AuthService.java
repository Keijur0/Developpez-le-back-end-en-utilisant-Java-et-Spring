package com.chatop.api.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.api.dto.LoginDto;
import com.chatop.api.dto.MeDto;
import com.chatop.api.model.UserEntity;
import com.chatop.api.repository.UserRepository;

@Service
public class AuthService {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /* Register user */
    public Map<String, String> register(UserEntity userDto) {
        /* Formatting return value for response */
        Map<String, String> registerReponse = new HashMap<>();

        /* Check email (must be unique) */
        if(!userRepository.findByEmail(userDto.getEmail()).isEmpty()) {
            registerReponse.put("message", "This email address is already used");
            return registerReponse;
        }
        /* Check fields */
        if(userDto.getName().isEmpty() || userDto.getEmail().isEmpty() || userDto.getPassword().isEmpty()) {
            registerReponse.put("message", "Some fields are empty");
            return registerReponse;
        }

        /* Create new user */
        UserEntity user = new UserEntity();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());

        /* Getting current date and time */
        long timeStampInMillis = System.currentTimeMillis();
        Timestamp timeStampNow = new Timestamp(timeStampInMillis);
        user.setCreated_at(timeStampNow);
        user.setUpdated_at(timeStampNow);

        /* Save user in DB */
        userRepository.save(user);

        /* Generating token */
        String token = jwtService.generateToken(user);



        registerReponse.put("token", token);

        return registerReponse;
    }

    /* Login user */
    public Map<String, String> login(LoginDto loginDto) {
        Map<String, String> loginResponse = new HashMap<>();

        /* Checking if both fields have been filled */
        if(loginDto.getEmail() == null && loginDto.getPassword() == null) {
            loginResponse.put("message", "error");
            return loginResponse;
        }
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), 
                loginDto.getPassword())
        );

        /* Checking if user is matching with db */
        UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);

        /* Formatting return value for response */
        
        loginResponse.put("token", token);
        return loginResponse;
    }

    public MeDto me() {
        /* Get auth info to get user's email */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        MeDto meDto = new MeDto();
        meDto.setName(user.getName());
        meDto.setEmail(user.getEmail());
        meDto.setCreated_at(user.getCreated_at());
        meDto.setUpdated_at(user.getUpdated_at());
        return meDto;
    }

}
