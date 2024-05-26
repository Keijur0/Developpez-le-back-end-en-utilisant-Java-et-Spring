package com.chatop.api.controller;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.service.PictureService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Picture Endpoint")
public class PictureController {

    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Operation(
        description = "Get the picture corresponding to the rental",
        summary = "This endpoint returns the picture of a rental",
        responses = {
            @ApiResponse(
                description = "Success: Returns the picture URL to the client",
                responseCode = "200"
            ),
            @ApiResponse(
                description = "Not Found: The picture does not exist on the server, or is not readable",
                responseCode = "404"
            ),
            @ApiResponse(
                description = "Bad Request: The URL is incorrect or could not be parsed",
                responseCode = "400"
            )
        }
    )    
    @GetMapping("/api/pictures/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> getPicture(@PathVariable String fileName) throws MalformedURLException {

        Resource resource = pictureService.getPicture(fileName);
        try {
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename())
                    .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }       
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
         
    }
}
