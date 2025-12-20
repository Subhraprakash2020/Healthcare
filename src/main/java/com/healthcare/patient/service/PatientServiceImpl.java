package com.healthcare.patient.service;

import com.healthcare.patient.model.Patient;
import com.healthcare.patient.model.PatientProfileImage;
import com.healthcare.patient.model.Status;
import com.healthcare.patient.payload.response.PatientProfileResponse;
import com.healthcare.patient.repository.PatientProfileRepository;
import com.healthcare.patient.repository.PatientRepository;
import com.healthcare.patient.security.services.UserDetailsImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService, UserDetailsService {
  @Autowired private PatientRepository patientRepository;

  @Autowired PasswordEncoder encoder;

  @Autowired PatientProfileRepository patientProfileRepository;

  @Override
  public Patient createPatient(Patient patient) {
    return patientRepository.save(patient);
  }

  @Override
  public List<Patient> getAllPatients() {
    return patientRepository.findAll();
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Patient patient =
        patientRepository
            .findByEmail(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with email: " + email));
    return UserDetailsImpl.build(patient);
  }

  @Override
  public String getEmail() {
    throw new UnsupportedOperationException("Unimplemented method 'getEmail'");
  }

  @Override
  public Patient getPatientById(long id) {
    return patientRepository.findById(id).orElse(null);
  }

  @Override
  public Patient updatePatient(Long id, Patient patientDetails) {
    Patient patient = patientRepository.findById(id).orElse(null);
    if (patient != null) {
      if (patientDetails.getFirstName() != null) {
        patient.setFirstName(patientDetails.getFirstName());
      }
      if (patientDetails.getLastName() != null) {
        patient.setLastName(patientDetails.getLastName());
      }
      if (patientDetails.getPhoneNumber() != null) {
        patient.setPhoneNumber(patientDetails.getPhoneNumber());
      }
      if (patientDetails.getAddress() != null) {
        patient.setAddress(patientDetails.getAddress());
      }
      if (patientDetails.getAge() != null) {
        patient.setAge(patientDetails.getAge());
      }
      if (patientDetails.getStatus() != null) {
        patient.setStatus(patientDetails.getStatus());
      }

      if (patientDetails.getPassword() != null && !patientDetails.getPassword().isEmpty()) {
        patient.setPassword(encoder.encode(patientDetails.getPassword()));
      }

      patient.setUpdatedAt(LocalDateTime.now());

      return patientRepository.save(patient);
    } else {
      return null;
    }
  }

  @Override
  public Patient deletePatient(Long id) {
    Patient patient =
        patientRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Patient Not Found with id: " + id));
    patient.setStatus(Status.INACTIVE);
    return patientRepository.save(patient);
  }

  @Override
  public Patient updatePatientDetails(String email, Patient patientDetails) {

    Patient patient =
        patientRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Patient Not Found with email: " + email));

    if (patientDetails.getFirstName() != null) {
      patient.setFirstName(patientDetails.getFirstName());
    }
    if (patientDetails.getLastName() != null) {
      patient.setLastName(patientDetails.getLastName());
    }
    if (patientDetails.getPhoneNumber() != null) {
      patient.setPhoneNumber(patientDetails.getPhoneNumber());
    }
    if (patientDetails.getAddress() != null) {
      patient.setAddress(patientDetails.getAddress());
    }
    if (patientDetails.getAge() != null) {
      patient.setAge(patientDetails.getAge());
    }
    if (patientDetails.getPassword() != null && !patientDetails.getPassword().isEmpty()) {
      patient.setPassword(encoder.encode(patientDetails.getPassword()));
    }

    patient.setUpdatedAt(LocalDateTime.now());

    return patientRepository.save(patient);
  }

  @Override
  public PatientProfileResponse getPatientProfileResponse(Long patientId) {
    Patient patient = getPatientById(patientId);
    PatientProfileImage profileImage =
        patientProfileRepository.findByPatientId(patientId).orElse(null);
    return new PatientProfileResponse(patient, profileImage);
  }
}
