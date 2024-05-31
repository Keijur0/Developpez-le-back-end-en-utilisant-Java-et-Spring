package com.chatop.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.chatop.api.dto.RentalDto;
import com.chatop.api.model.Rental;

@Mapper
public interface RentalMapper {

    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

    @Mapping(target = "owner_id", source = "user.id")
    RentalDto rentalToRentalDto(Rental rental);

}
