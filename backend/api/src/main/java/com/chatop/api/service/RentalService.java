package com.chatop.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.api.model.Rental;
import com.chatop.api.repository.RentalRepository;

import lombok.Data;

@Data
@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    /* Get rental by id */
    public Optional<Rental> getRental(final Long id) {
        return rentalRepository.findById(id);
    }

    /* Get all rentals */
    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
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
