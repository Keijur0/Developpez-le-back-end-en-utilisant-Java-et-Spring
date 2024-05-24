package com.chatop.api.controller;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
