package com.healthcare.patient.controller;

import com.healthcare.patient.model.Patient;
import com.healthcare.patient.model.PatientProfileImage;
import com.healthcare.patient.repository.PatientProfileRepository;
import com.healthcare.patient.repository.PatientRepository;
import com.healthcare.patient.service.PatientProfileService;
import com.healthcare.patient.service.S3Service;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/healthcare/patient/profile-image")
public class PatientProfileImageController {
  @Autowired private S3Service s3Service;

  @Autowired private PatientRepository patientRepository;

  @Autowired private PatientProfileRepository profileRepository;

  @PostMapping("/upload-profile")
  @PreAuthorize("hasRole('PATIENT')")
  public ResponseEntity<?> uploadProfile(
      @RequestParam("image") MultipartFile image, Principal principal) {
    try {
      String email = principal.getName();

      Patient patient =
          patientRepository
              .findByEmail(email)
              .orElseThrow(() -> new RuntimeException("Patient not found"));

      // Create file name for S3
      String fileName = "patient-profile/" + patient.getId() + "_" + image.getOriginalFilename();

      // Upload to S3
      String imageUrl = s3Service.uploadPatientImage(image, fileName);

      // Get existing profile or create new
      PatientProfileImage profile =
          profileRepository.findByPatientId(patient.getId()).orElse(new PatientProfileImage());

      profile.setPatientId(patient.getId());
      profile.setImageUrl(imageUrl);

      profileRepository.save(profile);

      return ResponseEntity.ok(
          Map.of("message", "Profile image uploaded successfully", "imageUrl", imageUrl));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body(Map.of("message", "Error uploading profile image"));
    }
  }

  @GetMapping("/profile")
  @PreAuthorize("hasRole('PATIENT')")
  public ResponseEntity<?> getProfile(Principal principal) {

    String email = principal.getName();
    Patient patient =
        patientRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Patient not found"));

    PatientProfileImage profile = profileRepository.findByPatientId(patient.getId()).orElse(null);

    return ResponseEntity.ok(
        Map.of(
            "patientId", patient.getId(),
            "email", patient.getEmail(),
            "firstName", patient.getFirstName(),
            "lastName", patient.getLastName(),
            "imageUrl", profile != null ? profile.getImageUrl() : null));
  }

  @Autowired private PatientProfileService patientProfileSerice;

  @DeleteMapping("/delete-image")
  @PreAuthorize("hasRole('PATIENT')")
  public ResponseEntity<?> removeProfileImage(Principal principal) {
    try {
      patientProfileSerice.removeProfileImage(principal);
      return ResponseEntity.ok(Map.of("message", "Profile image removed successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("message", "Error removing profile image"));
    }
  }
}
