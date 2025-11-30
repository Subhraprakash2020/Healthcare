package com.healthcare.patient.security;

import com.healthcare.admin.model.Admin;
import com.healthcare.admin.repository.AdminRepository;
import com.healthcare.admin.security.services.AdminUserDetailsImpl;
import com.healthcare.patient.model.Patient;
import com.healthcare.patient.repository.PatientRepository;
import com.healthcare.patient.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonUserDetailsService implements UserDetailsService {

  private final AdminRepository adminRepository;
  private final PatientRepository patientRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Admin admin = adminRepository.findByEmail(email).orElse(null);
    if (admin != null) {
      return AdminUserDetailsImpl.build(admin);
    }

    Patient patient = patientRepository.findByEmail(email).orElse(null);
    if (patient != null) {
      return UserDetailsImpl.build(patient);
    }

    throw new UsernameNotFoundException("User not found with email: " + email);
  }
}
