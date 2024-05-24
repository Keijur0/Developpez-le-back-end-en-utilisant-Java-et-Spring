package com.chatop.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.MessageDto;
import com.chatop.api.service.MessageService;

@RestController
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/api/messages")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody MessageDto msgDto) {
        Map<String, String> messageResponse = new HashMap<>();
        if(msgDto.getRental_id() == null || msgDto.getMessage() == null) {
            messageResponse.put("message", "At least one field is empty");
            return ResponseEntity.badRequest().body(messageResponse);
        }
        messageService.saveMessage(msgDto);
        messageResponse.put("message", "Message sent with success");
        return ResponseEntity.ok(messageResponse);
    }
}
