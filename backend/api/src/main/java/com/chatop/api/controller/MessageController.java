package com.chatop.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.MessageDto;
import com.chatop.api.model.ResponseMsg;
import com.chatop.api.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Message Endpoint")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /* Sending a message */
    @Operation(
        description = "This endpoints saves a message sent by a user to the owner of a rental",
        summary = "Send a message to the owner of a rental",
        responses = {
            @ApiResponse(
                description = "Success: Message sent",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Unauthorized: Missing or invalid token",
                responseCode = "401"
            )
        }
    )    
    @PostMapping("/api/messages")
    public ResponseEntity<ResponseMsg> sendMessage(@Valid @RequestBody MessageDto msgDto) {
        ResponseMsg responseMsg = new ResponseMsg();
        messageService.saveMessage(msgDto);
        responseMsg.setMessage("Message sent with success");
        return ResponseEntity.ok(responseMsg);
    }
}
