package com.healthcare.Healthcare.patient.service;

import com.healthcare.Healthcare.patient.model.Patient;
import com.healthcare.Healthcare.patient.repository.PatientRepository;
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
