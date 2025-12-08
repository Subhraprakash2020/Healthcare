package com.healthcare.patient.controller;

import com.healthcare.patient.model.Patient;
import com.healthcare.patient.payload.request.LoginRequest;
import com.healthcare.patient.payload.request.SignupRequest;
import com.healthcare.patient.payload.response.JwtResponse;
import com.healthcare.patient.payload.response.MessageResponse;
import com.healthcare.patient.repository.PatientRepository;
import com.healthcare.patient.security.jwt.JwtUtils;
import com.healthcare.patient.security.services.UserDetailsImpl;
import com.healthcare.patient.service.SequenceGeneratorService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/healthcare/patient")
public class PatientController {
  @Autowired private PatientRepository patientRepository;

  @Autowired private SequenceGeneratorService sequenceGeneratorService;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired PasswordEncoder encoder;

  @Autowired JwtUtils jwtUtils;

  // Patient Controller
  @PostMapping("/patient")
  public Patient createPatient(@RequestBody Patient patient) {
    patient.setId(sequenceGeneratorService.generateSequence(Patient.SEQUENCE_NAME));
    return patientRepository.save(patient);
  }

  // Get All Patients
  @GetMapping("/patients")
  public List<Patient> getAllPatients() {
    return patientRepository.findAll();
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtUtils.generateJwtToken(authentication);

    // Use common interface, not direct casting!
    UserDetails principal = (UserDetails) authentication.getPrincipal();

    Long id = null;
    String username = principal.getUsername();
    String email = null;

    // Extract patient-specific fields only if principal is a Patient user
    if (principal instanceof UserDetailsImpl patientDetails) {
      id = patientDetails.getId();
      email = patientDetails.getEmail();
    }

    return ResponseEntity.ok(new JwtResponse(jwt, id, username, email));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (patientRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new patient's account
    Patient patient = new Patient();
    patient.setId(sequenceGeneratorService.generateSequence(Patient.SEQUENCE_NAME));
    patient.setFirstName(signUpRequest.getFirstName());
    patient.setLastName(signUpRequest.getLastName());
    patient.setAge(signUpRequest.getAge());
    patient.setAddress(signUpRequest.getAddress());
    patient.setPhoneNumber(signUpRequest.getPhoneNumber());
    patient.setEmail(signUpRequest.getEmail());
    patient.setGender(signUpRequest.getGender());
    patient.setRole("PATIENT");
    patient.setStatus(signUpRequest.getStatus());
    patient.setPassword(encoder.encode(signUpRequest.getPassword()));
    patient.setUpdatedAt(LocalDateTime.now());

    patientRepository.save(patient);

    return ResponseEntity.ok(new MessageResponse("Patient registered successfully!"));
  }
}
