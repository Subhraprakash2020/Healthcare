package com.healthcare.provider.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class ProviderS3Service {

  @Autowired
  @Qualifier("providerS3Client")
  private S3Client s3Client;

  @Value("${aws.s3.bucketName}")
  private String bucketName;

  public String uploadFile(MultipartFile file, String fileName) throws IOException {
    // Implementation for uploading file to S3
    s3Client.putObject(
        PutObjectRequest.builder().bucket(bucketName).key(fileName).contentType(file.getContentType()).build(),
        RequestBody.fromBytes(file.getBytes()));
    return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
  }

  public byte[] downloadFile(String key) {
    // Implementation for downloading file from S3
    ResponseBytes<GetObjectResponse> objectABytes =
        s3Client.getObjectAsBytes(builder -> builder.bucket(bucketName).key(key));
    return objectABytes.asByteArray();
  }
}
