package com.healthcare.Healthcare.patient.repository;

import com.healthcare.Healthcare.patient.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, Long> {}
