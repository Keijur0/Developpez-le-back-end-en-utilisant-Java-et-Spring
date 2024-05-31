package com.chatop.api.service;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.chatop.api.model.Rental;
import com.chatop.api.model.UserEntity;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;

@Component
public class MappingService {

    private final UserRepository userRepository;

    private final RentalRepository rentalRepository;

    public MappingService(UserRepository userRepository, RentalRepository rentalRepository) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    @Named("userIdToUserEntity")
    public UserEntity userIdToUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Named("rentalIdToRental")
    public Rental rentalIdToRental(Long id) {
        return rentalRepository.findById(id).orElseThrow();
    }
}
