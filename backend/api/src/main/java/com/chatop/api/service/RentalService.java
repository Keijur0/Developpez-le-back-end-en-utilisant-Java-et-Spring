package com.chatop.api.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatop.api.dto.RentalDto;
import com.chatop.api.model.Rental;
import com.chatop.api.model.UserEntity;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;

@Service
public class RentalService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RentalRepository rentalRepository;

    public RentalService(UserRepository userRepository, JwtService jwtService, RentalRepository rentalRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.rentalRepository = rentalRepository;
    }

    /* Convert a Rental object to a RentalDto object */
    private RentalDto toDto(Rental rental) {
        RentalDto rentalDto = new RentalDto();
        rentalDto.setId(rental.getId());
        rentalDto.setName(rental.getName());
        rentalDto.setSurface(rental.getSurface());
        rentalDto.setPrice(rental.getPrice());
        rentalDto.setPicture(rental.getPicture());
        rentalDto.setDescription(rental.getDescription());
        rentalDto.setOwnerId(rental.getUser().getId());
        rentalDto.setCreated_at(rental.getCreated_at());
        rentalDto.setUpdated_at(rental.getUpdated_at());
        return rentalDto;
    }

    /* Get rental by id */
    public RentalDto getRental(final Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        RentalDto rentalDto = toDto(rental);
        return rentalDto;
    }

    /* Get all rentals */
    public Map<String, Iterable<RentalDto>> getRentals() {
        Iterable<Rental> allRentals =  rentalRepository.findAll();
        List<RentalDto> allRentalsDto = new ArrayList<>();
        for (Rental rental : allRentals) {
            RentalDto rentalDto = toDto(rental);
            allRentalsDto.add(rentalDto);
        }
        Map<String, Iterable<RentalDto>> rentalsResponse = new HashMap<>();
        rentalsResponse.put("rentals", allRentalsDto);
        return rentalsResponse;
    }

    /* Create rental */
    public void saveRental(String authorization, String name, int surface, int price, String picture, String description) {

        /* Extracting email from token to fill ownerId column */
        String token = authorization.substring(7);
        String email = jwtService.extractUsername(token);
        UserEntity user = userRepository.findByEmail(email).orElseThrow();

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setPicture(picture);
        rental.setDescription(description);

        /* Getting current date and time */
        long timeStampInMillis = System.currentTimeMillis();
        Timestamp timeStampNow = new Timestamp(timeStampInMillis);
        rental.setCreated_at(timeStampNow);
        rental.setUpdated_at(timeStampNow);

        rentalRepository.save(rental);
    }

    /* Delete rental by id */
    public void deleteRental(final Long id) {
        rentalRepository.deleteById(id);
    }

    /* Update rental */
    public Rental updateRental(Long id, Rental rentalDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRental'");
    }
}
