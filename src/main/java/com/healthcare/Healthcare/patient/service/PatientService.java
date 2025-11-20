package com.healthcare.Healthcare.patient.service;

import java.util.List;

import com.healthcare.Healthcare.patient.model.Patient;

public interface PatientService {
    Patient createPatient(Patient patient);
    List<Patient> getAllPatients();
}
