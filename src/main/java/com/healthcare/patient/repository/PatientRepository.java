package com.healthcare.patient.repository;

import com.healthcare.patient.model.Patient;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, Long> {
  Optional<Patient> findByEmail(String email);

  Boolean existsByEmail(String email);

  Boolean existsByPassword(String password);
}
