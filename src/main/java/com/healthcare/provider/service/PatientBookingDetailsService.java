package com.healthcare.provider.service;

import com.healthcare.patient.model.Patient;
import java.util.Optional;

public interface PatientBookingDetailsService {
  Optional<Patient> findById(Long id);
}
