package com.healthcare.admin.controller;

import com.healthcare.admin.model.Admin;
import com.healthcare.admin.payload.LoginRequestAdmin;
import com.healthcare.admin.payload.SignUpRequestAdmin;
import com.healthcare.admin.repository.AdminRepository;
import com.healthcare.admin.security.services.AdminUserDetailsImpl;
import com.healthcare.patient.security.jwt.JwtUtils;
import com.healthcare.patient.service.SequenceGeneratorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare/admins")
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
}
