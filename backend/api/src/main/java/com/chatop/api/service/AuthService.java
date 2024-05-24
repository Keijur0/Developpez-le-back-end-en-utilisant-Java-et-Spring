package com.chatop.api.service;

import java.sql.Timestamp;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.api.dto.LoginDto;
import com.chatop.api.dto.UserDto;
import com.chatop.api.model.AuthResponse;
import com.chatop.api.model.UserEntity;
import com.chatop.api.repository.UserRepository;

@Service
public class AuthService {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    /* Register user */
    public AuthResponse register(UserEntity userDto) {
        /* Formatting return value for response */
        /* Map<String, String> registerReponse = new HashMap<>(); */

        /* Check email (must be unique) */
/*         if(!userRepository.findByEmail(userDto.getEmail()).isEmpty()) {
            registerReponse.put("message", "This email address is already used");
            return registerReponse;
        } */
        /* Check fields */
/*         if(userDto.getName().isEmpty() || userDto.getEmail().isEmpty() || userDto.getPassword().isEmpty()) {
            registerReponse.put("message", "Some fields are empty");
            return registerReponse;
        } */

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

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        /* Generating token */
        String token = jwtService.generateToken(userDetails);

        AuthResponse authResponse = new AuthResponse(token);

        return authResponse;
    }

    /* Login user */
    public AuthResponse login(LoginDto loginDto) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), 
                loginDto.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
        String token = jwtService.generateToken(userDetails);

        /* Formatting return value for response */
        
        AuthResponse authResponse = new AuthResponse(token);
        return authResponse;
    }

    public UserDto me() {
        /* Get auth info to get user's email */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setCreated_at(user.getCreated_at());
        userDto.setUpdated_at(user.getUpdated_at());
        return userDto;
    }

}
