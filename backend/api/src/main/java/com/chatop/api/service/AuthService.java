package com.chatop.api.service;

import java.sql.Timestamp;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.api.dto.LoginDto;
import com.chatop.api.model.AuthenticationResponse;
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
    public AuthenticationResponse register(UserEntity userDto) {
        /* Check email (must be unique) */
        if(!userRepository.findByEmail(userDto.getEmail()).isEmpty()) {
            return null;
            /* return new ResponseEntity<>("This email address is already used", HttpStatus.BAD_REQUEST); */
        }
        /* Check fields */
        if(userDto.getName().isEmpty() || userDto.getEmail().isEmpty() || userDto.getPassword().isEmpty()) {
            return null;
            /* return new ResponseEntity<>("Some fields are empty", HttpStatus.BAD_REQUEST); */
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
        userRepository.save(user);

        /* Generating token */
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    /* Login user */
    public AuthenticationResponse login(LoginDto userDto) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userDto.getEmail(), 
                userDto.getPassword())
        );

        UserEntity user = userRepository.findByEmail(userDto.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

}
