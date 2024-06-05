package com.chatop.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.UserDto;
import com.chatop.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User Endpoint")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* Get user by id */
    @Operation(
        description = "This endpoint returns the current authenticated user using its id",
        summary = "Get the current user using its id",
        responses = {
            @ApiResponse(
                description = "Success: Returns the current authenticated user's information",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Unauthorized: Missing or invalid token",
                responseCode = "401"
            )
        }
    )
    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable final Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
