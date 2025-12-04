package com.healthcare.admin.services;

import com.healthcare.admin.model.Admin;
import com.healthcare.admin.repository.AdminRepository;
import com.healthcare.admin.security.services.AdminUserDetailsImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.healthcare.patient.model.Patient;
import com.healthcare.patient.service.PatientService;


@Service
public class AdminServiceImpl implements AdminService, UserDetailsService {
  @Autowired private AdminRepository adminRepository;

  @Override
  @Transactional
  public Admin createAdmin(Admin admin) {
    return adminRepository.save(admin);
  }

  @Override
  public List<Admin> getAllAdmins() {
    return adminRepository.findAll();
  }

  @Override
  public String getEmail() {
    // Implementation logic to retrieve email
    return null;
  }

  @Override
  public UserDetails loadAdminUserByUsername(String email) {
    return loadUserByUsername(email);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Admin admin =
        adminRepository
            .findByEmail(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("Admin Not Found with email: " + email));

    return AdminUserDetailsImpl.build(admin);
  }
  

  @Autowired private PatientService patientService;

  public List<Patient> getListOfPatients() {
    System.out.println("**Printing List of Patients in Admin Service**");
    return patientService.getAllPatients();
  }
}
