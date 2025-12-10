package com.healthcare.patient.model;

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
@Document(collection = "patient_profile_images")
public class PatientProfileImage {
  @Id private String id;
  private Long patientId;
  private String imageUrl;
  private Date uploadedAt = new Date();
}
