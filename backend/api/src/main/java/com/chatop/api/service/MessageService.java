package com.chatop.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatop.api.model.Message;
import com.chatop.api.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

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
