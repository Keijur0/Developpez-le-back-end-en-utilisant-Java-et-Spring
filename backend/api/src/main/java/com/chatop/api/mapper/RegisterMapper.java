package com.chatop.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.chatop.api.dto.RegisterDto;
import com.chatop.api.model.UserEntity;

@Mapper
public interface RegisterMapper {

    RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    UserEntity registerDtoToUserEntity(RegisterDto registerDto);

}
