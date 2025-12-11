package com.healthcare.admin.services;

import com.healthcare.admin.model.Admin;
import com.healthcare.admin.repository.AdminRepository;
import com.healthcare.admin.security.services.AdminUserDetailsImpl;
import com.healthcare.patient.model.Patient;
import com.healthcare.patient.repository.PatientRepository;
import com.healthcare.patient.service.PatientService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService, UserDetailsService {

  @Autowired private AdminRepository adminRepository;

  @Autowired private PatientService patientService;

  @Autowired private PatientRepository patientRepository;

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
    return null; // Your logic
  }

  // This is the method Spring Security uses
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Admin admin =
        adminRepository
            .findByEmail(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("Admin Not Found with email: " + email));

    return AdminUserDetailsImpl.build(admin);
  }

  @Override
  public List<Patient> getListOfPatients() {
    return patientService.getAllPatients();
  }

  @Override
  public Patient getPatientById(Long id) {
    return patientRepository.findById(id).orElse(null);
  }

  @Override
  public Patient updatePatient(Long id, Patient patientDetails) {
    return patientService.updatePatient(id, patientDetails);
  }

  @Override
  public UserDetails loadAdminUserByUsername(String username) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'loadAdminUserByUsername'");
  }

  @Override
  public Patient deletePatient(Long id) {
    return patientService.deletePatient(id);
  }
}
