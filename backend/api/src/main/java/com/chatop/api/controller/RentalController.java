package com.chatop.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.api.dto.RentalDto;
import com.chatop.api.dto.RentalsDto;
import com.chatop.api.model.ResponseMsg;
import com.chatop.api.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Rental Endpoints")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalservice) {
        this.rentalService = rentalservice;
    }

    /* Get all rentals */
    @Operation(
        description = "This endpoint returns all available rentals from the database",
        summary = "Get all rentals endpoint",
        responses = {
            @ApiResponse(
                description = "Success: List of all available rentals",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Unauthorized: Missing or invalid token",
                responseCode = "401"
            )
        }
    )
    @GetMapping("/api/rentals")
    public ResponseEntity<RentalsDto> getRentals() {
        return ResponseEntity.ok(rentalService.getRentals());
    }

    /* Get rental by id */
    @Operation(
        description = "This endpoint returns one rental, using its id",
        summary = "Get rental by id endpoint",
        responses = {
            @ApiResponse(
                description = "Success: Details of the rental",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Unauthorized: Missing or invalid token",
                responseCode = "401"
            )
        }
    )
    @GetMapping("/api/rentals/{id}")
    public ResponseEntity<RentalDto> getRental(@PathVariable final Long id) {
        return ResponseEntity.ok(rentalService.getRental(id));
    }

    /* Create rental */
    @Operation(
        description = "This endpoint is used to create a rental, with the information submitted in the form",
        summary = "Create a rental by filling a form",
        responses = {
            @ApiResponse(
                description = "Success: Creation of the rental",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Unauthorized: Missing or invalid token",
                responseCode = "401"
            )
        }
    )
    @PostMapping("/api/rentals")
    public ResponseEntity<ResponseMsg> saveRental(
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
            ResponseMsg responseMsg = new ResponseMsg();
            responseMsg.setMessage("Rental created!");
            return ResponseEntity.ok(responseMsg);
    }

    /* Update rental */
    @Operation(
        description = "This endpoint is used to update an existing rental, with the information submitted in the form",
        summary = "Update an existing rental by filling a form",
        responses = {
            @ApiResponse(
                description = "Success: Updating the rental with the new values",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Unauthorized: Missing or invalid token",
                responseCode = "401"
            )
        }
    )
    @PutMapping("/api/rentals/{id}")
    public ResponseEntity<ResponseMsg> updateRental(
            @PathVariable final Long id, 
            @RequestParam String name, 
            @RequestParam int surface,
            @RequestParam int price,
            @RequestParam String description) {
                rentalService.updateRental(id, name, surface, price, description);
                ResponseMsg responseMsg = new ResponseMsg();
                responseMsg.setMessage("Rental updated !");
                return ResponseEntity.ok(responseMsg);
    }

}
