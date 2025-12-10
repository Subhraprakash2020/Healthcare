package com.healthcare.patient.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

  @Value("${aws.s3.bucketName}")
  private String healthcarePatientProfileImagesBucket;

  @Autowired private S3Client s3Client;

  public String uploadPatientImage(MultipartFile file, String fileName) throws IOException {
    PutObjectRequest request =
        PutObjectRequest.builder()
            .bucket(healthcarePatientProfileImagesBucket)
            .key(fileName)
            .contentType(file.getContentType())
            .build();

    s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
    return "https://" + healthcarePatientProfileImagesBucket + ".s3.amazonaws.com/" + fileName;
  }
}
