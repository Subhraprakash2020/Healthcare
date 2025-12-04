package com.healthcare.admin.services;

import com.healthcare.admin.model.Admin;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import com.healthcare.patient.model.Patient;


public interface AdminService {
  Admin createAdmin(Admin admin);

  List<Admin> getAllAdmins();

  String getEmail();

  public UserDetails loadAdminUserByUsername(String username);

  List<Patient> getListOfPatients();
}
