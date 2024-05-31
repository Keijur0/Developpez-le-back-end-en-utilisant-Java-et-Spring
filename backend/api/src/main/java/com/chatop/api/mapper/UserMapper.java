package com.chatop.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chatop.api.dto.UserDto;
import com.chatop.api.model.UserEntity;

@Mapper
public interface UserMapper {

        UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

        UserDto userEntityToUserDto(UserEntity user);

}
