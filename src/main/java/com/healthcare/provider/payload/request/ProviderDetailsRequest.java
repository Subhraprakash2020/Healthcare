package com.healthcare.provider.payload.request;

import com.healthcare.provider.model.LevelOfTreatment;
import com.healthcare.provider.model.PatientAgeBracket;
import com.healthcare.provider.model.Practices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDetailsRequest {
  private Long providerId;

  private String clinicianName;
  private Practices practices;
  private LevelOfTreatment levelOfTreatment;
  private PatientAgeBracket patientAgeBracket;

  private Integer experienceYears;

  private String availabilityInWeek;
  private String availabilityTime;

  private Double consultingFee;

  private String aboutMe;

  private String city;
  private String state;
  private String zip;
}
