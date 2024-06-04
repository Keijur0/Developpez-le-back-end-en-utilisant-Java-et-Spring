package com.chatop.api.service;

import java.util.Date;
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

    /* Create message */
    public void saveMessage(MessageDto msgDto) {
        Message msg = messageMapper.messageDtoToMessage(msgDto);

        msg.setCreated_at(new Date());
        msg.setUpdated_at(new Date());

        /* Saving message */
        messageRepository.save(msg);
    }

}
