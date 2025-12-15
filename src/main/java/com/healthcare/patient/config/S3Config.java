package com.healthcare.patient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
  @Value("${aws.accessKey}")
  @Autowired
  private String accessKey;

  @Value("${aws.secretKey}")
  @Autowired
  private String secretKey;

  @Value("${aws.region}")
  private String region;

  @Bean(name = "patientS3Client")
  public S3Client s3Client() {
    AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

    return S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
        .build();
  }
}
