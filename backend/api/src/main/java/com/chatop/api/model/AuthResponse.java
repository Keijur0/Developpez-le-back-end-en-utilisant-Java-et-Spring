package com.chatop.api.model;

public class AuthResponse {

    String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    

}
