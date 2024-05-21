package com.chatop.api.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.RentalDto;
import com.chatop.api.model.Rental;
import com.chatop.api.service.RentalService;

@RestController
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalservice) {
        this.rentalService = rentalservice;
    }

    /* Get all rentals */
    @GetMapping("/api/rentals")
    public ResponseEntity<Map<String, Iterable<RentalDto>>> getRentals() {
        return ResponseEntity.ok(rentalService.getRentals());
    }

    /* Get rental by id */
    @GetMapping("/api/rentals/{id}")
    public ResponseEntity<RentalDto> getRental(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(rentalService.getRental(id));
    }

    /* Create rental */
    @PostMapping("/api/rentals")
    public ResponseEntity<String> saveRental(
        @RequestHeader("Authorization") String authorization,
        @RequestParam("name") String name,
        @RequestParam("surface") int surface,
        @RequestParam("price") int price,
        @RequestParam("picture") String picture,
        @RequestParam("description") String description) {
            rentalService.saveRental(authorization, name, surface, price, picture, description);
            return ResponseEntity.ok("Rental created !");
    }

    /* Update rental */
    @PutMapping("/api/rentals/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable final Long id, @RequestBody Rental rentalDto) {
        return ResponseEntity.ok(rentalService.updateRental(id, rentalDto));
    }

}
