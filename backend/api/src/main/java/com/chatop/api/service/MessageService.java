package com.chatop.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.api.model.Message;
import com.chatop.api.repository.MessageRepository;

import lombok.Data;

@Data
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    
    /* Get message by id */
    public Optional<Message> getMessage(final Long id) {
        return messageRepository.findById(id);
    }

    /* Get all messages */
    public Iterable<Message> getMessages() {
        return messageRepository.findAll();
    }

    /* Create/Update message */
    public Message saveMessage(Message message) {
        Message savedMessage = messageRepository.save(message);
        return savedMessage;
    }

    /* Delete message by id */
    public void deleteMessage(final Long id) {
        messageRepository.deleteById(id);
    }

}
