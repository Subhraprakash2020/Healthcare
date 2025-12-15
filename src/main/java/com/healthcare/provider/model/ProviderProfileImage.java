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

//   public String getproviderId() {
//     return providerId;
//   }

//   public void setId(String providerId) {
//     this.providerId = providerId;
//   }

//   public Long getPatientId() {
//     return patientId;
//   }

//   public void setPatientId(Long patientId) {
//     this.patientId = patientId;
//   }

//   public String getImageUrl() {
//     return imageUrl;
//   }

//   public void setImageUrl(String imageUrl) {
//     this.imageUrl = imageUrl;
//   }

//   public Date getUploadedAt() {
//     return uploadedAt;
//   }

//   public void setUploadedAt(Date uploadedAt) {
//     this.uploadedAt = uploadedAt;
//   }
  // public ProviderProfileImage(String id, Long patientId, String imageUrl, Date uploadedAt) {
  // 	super();
  // 	this.id = id;
  // 	this.patientId = patientId;
  // 	this.imageUrl = imageUrl;
  // 	this.uploadedAt = uploadedAt;
  // }
  // public ProviderProfileImage() {
  // 	super();
  // 	// TODO Auto-generated constructor stub
  // }

}
