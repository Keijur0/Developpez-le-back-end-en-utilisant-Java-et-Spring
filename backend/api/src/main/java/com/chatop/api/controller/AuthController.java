package com.chatop.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.LoginDto;
import com.chatop.api.dto.RegisterDto;
import com.chatop.api.dto.UserDto;
import com.chatop.api.model.AuthResponse;
import com.chatop.api.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /* Login user */
    @PostMapping("/login")
    @Operation(
        description = "This endpoint is used to authenticate an existing user, using his credentials, and sends him an authentication token if it is successful",  
        summary = "Authenticate an existing user using his credentials",
        responses = {
            @ApiResponse(
                description = "Success: User authenticated, sends an authentication token",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Unauthorized: Missing or invalid token",
                responseCode = "401"
            )
        }
    )        
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    /* Register user */
    @Operation(
        description = "This endpoint is used to save a new user in the database by filling a form, and returns him a token if it is successful",
        summary = "Save a new user in the database by filling the register form",
        responses = {
            @ApiResponse(
                description = "Success: New user saved, sending a Jwt token to the user",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Bad Request: One of the fields has not been filled",
                responseCode = "400"
            )
        }
    )        
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        /* Checking if any field is empty */
/*         if(registerDto.getEmail() == null || registerDto.getName() == null || registerDto.getPassword() == null) {
            ResponseMsg responseMsg = new ResponseMsg();
            responseMsg.setMessage("At least one field is empty");
            return ResponseEntity.badRequest().body(responseMsg);
        } */
        return ResponseEntity.ok(authService.register(registerDto));
    }

    /* Display "Me" page after Login and Register */
    @Operation(
        description = "Get the current authenticated user info to display them on /me page, using the security context",
        summary = "This endpoint returns the info of the current authenticated user, using the security context",
        responses = {
            @ApiResponse(
                description = "Success: Details of the current user",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Unauthorized: Missing or invalid token",
                responseCode = "401"
            )
        }
    )    
    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        return ResponseEntity.ok(authService.me());
    }
}
