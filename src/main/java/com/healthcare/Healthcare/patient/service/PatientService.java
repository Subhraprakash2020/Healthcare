package com.healthcare.Healthcare.patient.service;

import com.healthcare.Healthcare.patient.model.Patient;
import java.util.List;

public interface PatientService {
  Patient createPatient(Patient patient);

  List<Patient> getAllPatients();
}
