package com.chatop.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.Rental;
import com.chatop.api.service.RentalService;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalservice;

    /* Get all rentals */
    @GetMapping("/rentals")
    public Iterable<Rental> getRentals() {
        return rentalservice.getRentals();
    }

    /* Get rental by id */
    @GetMapping("/rental/{id}")
    public Optional<Rental> getRental(final Long id) {
        return rentalservice.getRental(id);
    }
}
