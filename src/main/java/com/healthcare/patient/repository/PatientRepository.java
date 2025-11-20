package com.healthcare.patient.repository;

import com.healthcare.patient.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, Long> {}
