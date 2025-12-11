package com.healthcare.provider.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.healthcare.provider.service.S3Service;

@RestController("/healthcare/provider")
public class ProviderImageUploadingController {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/uploadProviderImage")
    public ResponseEntity<String> uploadProviderImage(@RequestParam("file") MultipartFile file) throws IOException {
        s3Service.uploadFile(file);
        return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
    }

}
