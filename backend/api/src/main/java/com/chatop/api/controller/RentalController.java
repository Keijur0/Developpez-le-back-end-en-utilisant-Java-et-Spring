package com.chatop.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.api.dto.RentalDto;
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
    public ResponseEntity<Map<String, String>> saveRental(
        @RequestParam("name") String name,
        @RequestParam("surface") int surface,
        @RequestParam("price") int price,
        @RequestParam("picture") MultipartFile picture,
        @RequestParam("description") String description) {
            try {
                rentalService.saveRental(name, surface, price, picture, description);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map<String, String> rentalResponse = new HashMap<>();
            rentalResponse.put("message", "Rental created !");
            return ResponseEntity.ok(rentalResponse);
    }

    /* Update rental */
    @PutMapping("/api/rentals/{id}")
    public ResponseEntity<Map<String, String>> updateRental(
            @PathVariable final Long id, 
            @RequestParam String name, 
            @RequestParam int surface,
            @RequestParam int price,
            @RequestParam String description) {
                Map<String, String> rentalResponse = new HashMap<>();
                rentalResponse.put("message", "Rental updated !");
                return ResponseEntity.ok(rentalResponse);
    }

}
