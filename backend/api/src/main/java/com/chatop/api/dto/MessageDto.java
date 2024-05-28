package com.chatop.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageDto {

    @NotBlank(message = "Message cannot be empty")
    private String message;

    @NotNull(message = "Rental id cannot be null")
    private Long rental_id;

    @NotNull(message = "User id cannot be null")
    private Long user_id;

}
