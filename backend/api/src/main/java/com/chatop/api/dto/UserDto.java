package com.chatop.api.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private Date created_at;

    private Date updated_at;
    
}
