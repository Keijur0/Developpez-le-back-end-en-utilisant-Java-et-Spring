package com.chatop.api.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.Rental;
import com.chatop.api.service.RentalService;

@RestController
public class RentalController {

    private final RentalService rentalservice;

    public RentalController(RentalService rentalservice) {
        this.rentalservice = rentalservice;
    }

    /* Get all rentals */
    @GetMapping("/api/rentals")
    public ResponseEntity<Iterable<Rental>> getRentals() {
        return ResponseEntity.status(HttpStatus.OK).body(rentalservice.getRentals());
    }

    /* Get rental by id */
    @GetMapping("/api/rentals/{id}")
    public ResponseEntity<Optional<Rental>> getRental(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(rentalservice.getRental(id));
    }
}
