package com.healthcare.patient.service;

import com.healthcare.patient.model.Patient;
import com.healthcare.patient.payload.response.PatientProfileResponse;
import java.util.List;

public interface PatientService {
  Patient createPatient(Patient patient);

  List<Patient> getAllPatients();

  String getEmail();

  Patient getPatientById(long id);

  Patient updatePatient(Long id, Patient patientDetails);

  Patient deletePatient(Long id);

  Patient updatePatientDetails(String email, Patient patientDetails);

  PatientProfileResponse getPatientProfileResponse(Long patientId);
}
