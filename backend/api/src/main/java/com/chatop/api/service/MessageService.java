package com.chatop.api.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatop.api.dto.MessageDto;
import com.chatop.api.model.Message;
import com.chatop.api.repository.MessageRepository;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;
    
    public MessageService(MessageRepository messageRepository, RentalRepository rentalRepository,
            UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    /* Get message by id */
    public Optional<Message> getMessage(final Long id) {
        return messageRepository.findById(id);
    }

    /* Get all messages */
    public Iterable<Message> getMessages() {
        return messageRepository.findAll();
    }

    /* Create message */
    public void saveMessage(MessageDto msgDto) {
        Message msg = new Message();

        /* Building message */
        msg.setRental(rentalRepository.findById(msgDto.getRental_id()).orElseThrow());
        msg.setUser(userRepository.findById(msgDto.getUser_id()).orElseThrow());
        msg.setMessage(msgDto.getMessage());
        msg.setCreatedAt(new Date());
        msg.setUpdatedAt(new Date());

        /* Saving message */
        messageRepository.save(msg);
    }

    /* Delete message by id */
    public void deleteMessage(final Long id) {
        messageRepository.deleteById(id);
    }

}
