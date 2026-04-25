package com.healthcare.provider.service.impl;

import com.healthcare.patient.model.Patient;
import com.healthcare.patient.repository.PatientRepository;
import com.healthcare.provider.service.PatientBookingDetailsService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientBookingDetailsImpl implements PatientBookingDetailsService {
  @Autowired private PatientRepository patientRepository;

  @Override
  public Optional<Patient> findById(Long id) {
    return patientRepository.findById(id);
  }
}
