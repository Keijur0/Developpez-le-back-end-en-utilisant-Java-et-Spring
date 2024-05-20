package com.chatop.api.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatop.api.model.Rental;
import com.chatop.api.repository.RentalRepository;

@Service
public class RentalService {

    private RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    /* Get rental by id */
    public Optional<Rental> getRental(final Long id) {
        return rentalRepository.findById(id);
    }

    /* Get all rentals */
    public Map<String, Iterable<Rental>> getRentals() {
        Iterable<Rental> allRentals =  rentalRepository.findAll();
        Map<String, Iterable<Rental>> rentalsResponse = new HashMap<>();
        rentalsResponse.put("rentals", allRentals);
        return rentalsResponse;
    }

    /* Create/Update rental */
    public Rental saveRental(Rental rental) {
        Rental savedRental = rentalRepository.save(rental);
        return savedRental;
    }

    /* Delete rental by id */
    public void deleteRental(final Long id) {
        rentalRepository.deleteById(id);
    }
}
