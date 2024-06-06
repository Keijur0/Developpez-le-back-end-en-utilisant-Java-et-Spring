package com.chatop.api.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.api.dto.RentalDto;
import com.chatop.api.dto.RentalsDto;
import com.chatop.api.mapper.RentalMapper;
import com.chatop.api.model.Rental;
import com.chatop.api.model.UserEntity;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;

@Service
public class RentalService {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final PictureService pictureService;


    public RentalService(UserRepository userRepository, RentalRepository rentalRepository,
            PictureService pictureService) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
        this.pictureService = pictureService;
    }

    /* Get rental by id */
    public RentalDto getRental(final Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        RentalDto rentalDto = RentalMapper.INSTANCE.rentalToRentalDto(rental);
        return rentalDto;
    }

    /* Get all rentals */
    public RentalsDto getRentals() {
        Iterable<Rental> allRentals =  rentalRepository.findAll();
        ArrayList<RentalDto> allRentalsDto = new ArrayList<>();
        for (Rental rental : allRentals) {
            RentalDto rentalDto = RentalMapper.INSTANCE.rentalToRentalDto(rental);
            allRentalsDto.add(rentalDto);
        }
        /* Formatting response */
        RentalsDto rentalsDto = new RentalsDto();
        rentalsDto.setRentals(allRentalsDto);
        return rentalsDto;
    }

    /* Create rental */
    public void saveRental(String name, int surface, int price, MultipartFile picture, String description) throws IOException {

        /* Extracting email from security context */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow();

        try {
            /* Save the picture */
            String picPath = pictureService.savePicture(picture);

            /* Create new rental */
            Rental rental = new Rental();
            rental.setUser(user);
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setPicture(picPath);
            rental.setDescription(description);

            /* Getting current date and time */
            long timeStampInMillis = System.currentTimeMillis();
            Timestamp timeStampNow = new Timestamp(timeStampInMillis);
            rental.setCreated_at(timeStampNow);
            rental.setUpdated_at(timeStampNow);

            rentalRepository.save(rental);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Update rental */
    public void updateRental(Long id, String name, int surface, int price, String description) {
        /* Find original rental */
        Rental rental = rentalRepository.findById(id).orElseThrow();

        /* Compare values and update if they changed */
        if(name != rental.getName()) {
            rental.setName(name);
            rental.setUpdated_at(new Date());
        }
        if(surface != rental.getSurface()) {
            rental.setSurface(surface);
            rental.setUpdated_at(new Date());
        }
        if(price != rental.getPrice()) {
            rental.setPrice(price);
            rental.setUpdated_at(new Date());
        }
        if(description != rental.getDescription()) {
            rental.setDescription(description);
            rental.setUpdated_at(new Date());
        }

        /* Save changes */
        rentalRepository.save(rental);
    }

}
