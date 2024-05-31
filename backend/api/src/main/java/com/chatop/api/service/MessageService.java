package com.chatop.api.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatop.api.dto.MessageDto;
import com.chatop.api.mapper.MessageMapper;
import com.chatop.api.model.Message;
import com.chatop.api.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
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
        Message msg = messageMapper.messageDtoToMessage(msgDto);

/*         msg.setRental(rentalRepository.findById(msgDto.getRental_id()).orElseThrow());
        msg.setUser(userRepository.findById(msgDto.getUser_id()).orElseThrow());
        msg.setMessage(msgDto.getMessage()); */

        msg.setCreated_at(new Date());
        msg.setUpdated_at(new Date());

        /* Saving message */
        messageRepository.save(msg);
    }

    /* Delete message by id */
    public void deleteMessage(final Long id) {
        messageRepository.deleteById(id);
    }

}
