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
@Document(collection = "providerDetails")
public class ProviderDetails {
  @Id private String id;
  private Long providerId;
  private String providerEmail;
  private String providerUserId;
  private String clinicianName;
  private Practices practices;
  private LevelOfTreatment levelOfTreatment;
  private PatientAgeBracket patientAgeBracket;
  private Integer experienceYears;
  private String availabilityInWeek;
  private String availabilityTime;
  private Double consultingFee;
  private String aboutMe;
  private String addressId;
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updateAt;
}
