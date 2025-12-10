package com.healthcare.patient.repository;

import com.healthcare.patient.model.PatientProfileImage;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientProfileRepository extends MongoRepository<PatientProfileImage, Long> {
  Optional<PatientProfileImage> findByPatientId(Long patientId);
}
