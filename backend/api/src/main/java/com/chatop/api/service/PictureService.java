package com.chatop.api.service;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;


@Service
public class PictureService {

    @Value("${pictures.path}")
    private String pictureLocation;

    public Resource getPicture(String fileName) throws MalformedURLException {
        Path filePath = Paths.get(pictureLocation).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        return resource;
    }

}
