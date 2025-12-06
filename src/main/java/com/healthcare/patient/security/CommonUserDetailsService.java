package com.healthcare.patient.security;

import com.healthcare.admin.model.Admin;
import com.healthcare.admin.repository.AdminRepository;
import com.healthcare.admin.security.services.AdminUserDetailsImpl;
import com.healthcare.patient.model.Patient;
import com.healthcare.patient.repository.PatientRepository;
import com.healthcare.patient.security.services.UserDetailsImpl;
import com.healthcare.provider.model.Provider;
import com.healthcare.provider.repository.ProviderRepository;
import com.healthcare.provider.security.ProviderUserDetailsImpl;
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
  private final ProviderRepository providerRepository;

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
    Provider provider = providerRepository.findByEmail(email).orElse(null);
    if (provider != null) {
      return ProviderUserDetailsImpl.build(provider);
    }

    throw new UsernameNotFoundException("User not found with email: " + email);
  }
}
