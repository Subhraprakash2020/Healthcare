package com.healthcare.provider.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "provider_profile_images")
public class ProviderProfileImage {
  @Id private String id;
  private String providerId;
  private String imageUrl;
  private Date uploadedAt = new Date();
}
