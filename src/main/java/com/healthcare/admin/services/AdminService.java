package com.healthcare.admin.services;

import com.healthcare.admin.model.Admin;
import com.healthcare.patient.model.Patient;
import com.healthcare.provider.model.Provider;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminService {
  Admin createAdmin(Admin admin);

  List<Admin> getAllAdmins();

  String getEmail();

  public UserDetails loadAdminUserByUsername(String username);

  List<Patient> getListOfPatients();

  Patient updatePatient(Long id, Patient patientDetails);

  Patient getPatientById(Long id);

  Patient deletePatient(Long id);

  List<Provider> getListOfProviders();

  Provider getProviderById(Long id);

  Provider updateProvider(Long id, Provider providerDetails);

  Provider deleteProvider(Long id);
}
