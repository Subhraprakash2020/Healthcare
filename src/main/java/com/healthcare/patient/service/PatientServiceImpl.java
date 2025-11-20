package com.healthcare.patient.service;

import com.healthcare.patient.model.Patient;
import com.healthcare.patient.repository.PatientRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
  @Autowired private PatientRepository patientRepository;

  @Override
  public Patient createPatient(Patient patient) {
    return patientRepository.save(patient);
  }

  @Override
  public List<Patient> getAllPatients() {
    return patientRepository.findAll();
  }
}
