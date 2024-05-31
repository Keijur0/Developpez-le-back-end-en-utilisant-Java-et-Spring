package com.chatop.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.chatop.api.dto.MessageDto;
import com.chatop.api.model.Message;
import com.chatop.api.service.MappingService;

@Mapper(componentModel = "spring", uses = {MappingService.class})
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "user", source = "user_id", qualifiedByName = "userIdToUserEntity")
    @Mapping(target = "rental", source = "rental_id", qualifiedByName = "rentalIdToRental")
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    @Mapping(target = "id", ignore = true)
    Message messageDtoToMessage(MessageDto messageDto);

}
