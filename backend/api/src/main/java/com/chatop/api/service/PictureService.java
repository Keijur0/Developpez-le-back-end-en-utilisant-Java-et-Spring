package com.chatop.api.service;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class PictureService {

    @Value("${pictures.path}")
    private String picsUploadPath;

    @Value("${pictures.db.path}")
    private String picsDbPath;

    /* Get picture to display on frontend */
    public Resource getPicture(String fileName) throws MalformedURLException {
        Path filePath = Paths.get(picsUploadPath).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        return resource;
    }

    /* Save a picture from frontend and defines path to save in DB */
    public String savePicture(MultipartFile picture) throws IOException {
        byte[] bytes = picture.getBytes();
        Path path = Paths.get(picsUploadPath + picture.getOriginalFilename());
        Files.write(path, bytes);
        String picPath = picsDbPath + picture.getOriginalFilename();
        return picPath;
 
}

}
