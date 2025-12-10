package com.healthcare.provider.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "providerAddress")
public class ProviderAddress {
  @Id private String id;
  private Long providerId;
  private String providerDetaisId;
  private String city;
  private String state;
  private String zip;
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updateAt;
}
