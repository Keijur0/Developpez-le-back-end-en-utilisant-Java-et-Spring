package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.service.UserService;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;


}
