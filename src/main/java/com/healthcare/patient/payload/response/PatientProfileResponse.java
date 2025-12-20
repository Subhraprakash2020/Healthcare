package com.healthcare.patient.payload.response;

import com.healthcare.patient.model.Patient;
import com.healthcare.patient.model.PatientProfileImage;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientProfileResponse {
  private Patient patient;
  private PatientProfileImage patientProfileImage;
}
