package com.healthcare.admin.controller;

import com.healthcare.admin.model.Admin;
import com.healthcare.admin.payload.LoginRequestAdmin;
import com.healthcare.admin.payload.SignUpRequestAdmin;
import com.healthcare.admin.repository.AdminRepository;
import com.healthcare.admin.security.services.AdminUserDetailsImpl;
import com.healthcare.admin.services.AdminService;
import com.healthcare.patient.model.Patient;
import com.healthcare.patient.security.jwt.JwtUtils;
import com.healthcare.patient.service.SequenceGeneratorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare/admin")
public class AdminController {
  @Autowired private SequenceGeneratorService sequenceGeneratorService;
  @Autowired private AdminRepository adminRepository;
  @Autowired private AuthenticationManager authenticationManager;
  @Autowired private PasswordEncoder encoder;
  @Autowired private JwtUtils jwtUtils;

  @PostMapping("/signup")
  public ResponseEntity<?> registerAdmin(
      @Valid @RequestBody SignUpRequestAdmin signUpRequestAdmin) {
    if (adminRepository.existsByEmail(signUpRequestAdmin.getEmail())) {
      return ResponseEntity.badRequest().body("Error: Email is already in use!");
    }

    Admin admin = new Admin();
    admin.setId(sequenceGeneratorService.generateSequence(Admin.SEQUENCE_NAME));
    admin.setUsername(signUpRequestAdmin.getUsername());
    admin.setEmail(signUpRequestAdmin.getEmail());
    admin.setPassword(encoder.encode(signUpRequestAdmin.getPassword()));
    admin.setRole("ADMIN");
    adminRepository.save(admin);
    return ResponseEntity.ok("Admin registered successfully!");
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateAdmin(
      @Valid @RequestBody LoginRequestAdmin loginRequestAdmin) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestAdmin.getEmail(), loginRequestAdmin.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtUtils.generateJwtToken(authentication);

    Object principal = authentication.getPrincipal();

    Long id = null;
    String username = null;
    String email = null;

    if (principal instanceof AdminUserDetailsImpl adminDetails) {
      id = adminDetails.getId();
      username = adminDetails.getUsername();
      email = adminDetails.getEmail();
    }

    return ResponseEntity.ok(
        "JWT: " + jwt + ", ID: " + id + ", Username: " + username + ", Email: " + email);
  }

  @Autowired private AdminService adminService;

  @GetMapping("/patients")
  public ResponseEntity<List<Patient>> getListOfPatients() {
    return new ResponseEntity<>(adminService.getListOfPatients(), HttpStatus.OK);
  }

  @GetMapping("/patients/{id}")
  public ResponseEntity<?> getPatientById(@PathVariable Long id) {

      Patient patient = adminService.getPatientById(id);

      if (patient == null) {
          return new ResponseEntity<>(patient, HttpStatus.NOT_FOUND);
      }
      else{
          return new ResponseEntity<>(patient, HttpStatus.OK);
      }
  }


  @PutMapping("/patientsUpdate/{id}")
  public ResponseEntity<String> updatePatient(@PathVariable Long id,@RequestBody Patient patientDetails) {

      try {
          Patient updatedPatient = adminService.updatePatient(id, patientDetails);

          if (updatedPatient != null) {
              return ResponseEntity.ok("Patient Details Updated Successfully!");
          } else {
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient Not Found!");
          }

      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                              .body("Cannot Update Patient Details!");
      }
  }

}
