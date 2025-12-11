package com.healthcare.provider.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderSearchRequest {
  private String clinicianName;
  private Practices practices;
  private LevelOfTreatment levelOfTreatment;
  private String zip;
}
