package com.chatop.api.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RentalDto {

    private Long id;
    
    private String name;

    private Integer surface;

    private Integer price;

    private String picture;

    private String description;

    private Long ownerId;

    private Date created_at;

    private Date updated_at;

}
