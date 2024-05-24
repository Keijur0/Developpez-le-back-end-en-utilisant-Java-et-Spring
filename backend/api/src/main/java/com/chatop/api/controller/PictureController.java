package com.chatop.api.controller;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.service.PictureService;

@RestController
/* @RequestMapping("/api/backend/api/src/main/resources") */
public class PictureController {

    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/api/pictures/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getPicture(@PathVariable String fileName) {
        /* Checking image type */
        MediaType contentType = fileName.toLowerCase().endsWith("jpg") ? 
            MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
        try {
            if (pictureService.getPicture(fileName) != null) {
                return ResponseEntity.ok()
                    .contentType(contentType)
                    .body(pictureService.getPicture(fileName));
            } else {
                return ResponseEntity.notFound().build();
            }       
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
         
    }
}
