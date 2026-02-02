package com.healthcare.patient.service;

import com.healthcare.patient.model.Patient;
import com.healthcare.patient.model.PatientProfileImage;
import com.healthcare.patient.repository.PatientProfileRepository;
import com.healthcare.patient.repository.PatientRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PatientProfileServiceImpl implements PatientProfileService {

  @Autowired private PatientRepository patientRepository;

  @Autowired private PatientProfileRepository profileRepository;

  @Override
  public void removeProfileImage(Principal principal) {
    String email = principal.getName();

    Patient patient =
        patientRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Patient not found"));
    System.out.println("Patient Email Is : " + patient.getEmail());

    PatientProfileImage profile =
        profileRepository
            .findByPatientId(patient.getId())
            .orElseThrow(() -> new RuntimeException("Profile Image not found"));

    profile.setImageUrl(null);
    profileRepository.save(profile);
  }
}
