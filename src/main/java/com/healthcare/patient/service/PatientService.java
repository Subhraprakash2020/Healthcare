package com.healthcare.patient.service;

import com.healthcare.patient.model.Patient;
import java.util.List;

public interface PatientService {
  Patient createPatient(Patient patient);

  List<Patient> getAllPatients();
}
