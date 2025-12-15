package com.healthcare.provider.controller;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.model.ProviderProfileImage;
import com.healthcare.provider.repository.ProviderProfileRepository;
import com.healthcare.provider.repository.ProviderRepository;
import com.healthcare.provider.service.ProviderS3Service;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/healthcare/provider")
public class ProviderImageUploadingController {

  @Autowired private ProviderS3Service s3Service;
  @Autowired private ProviderRepository providerRepository;

  @Autowired private ProviderProfileRepository profileRepository;

  @PostMapping("/uploadProviderImage")
  @PreAuthorize("hasRole('PROVIDER')")
  public ResponseEntity<Map<String, String>> uploadProviderImage(
      @RequestParam("file") MultipartFile file, Principal principal) throws IOException {

    try {
      String email = principal.getName();
      System.out.println("Uploading image for provider: " + email);
      Provider provider =
          providerRepository
              .findByEmail(email)
              .orElseThrow(() -> new RuntimeException("Provider not found"));

      String fileName =
          "provider-profile/" + provider.getUserId() + "_" + file.getOriginalFilename();
      String imageUrl = s3Service.uploadFile(file, fileName);

      ProviderProfileImage profile =
          profileRepository
              .findByProviderId(provider.getUserId())
              .orElse(new ProviderProfileImage());

      profile.setProviderId(provider.getUserId());
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
  @PreAuthorize("hasRole('PROVIDER')")
  public ResponseEntity<?> getProfile(Principal principal) {

    String email = principal.getName();
    Provider provider =
        providerRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Patient not found"));

    ProviderProfileImage profile = profileRepository.findByProviderId(provider.getUserId()).orElse(null);

    return ResponseEntity.ok(
        Map.of(
            "providerId", provider.getUserId(),
            "email", provider.getEmail(),
            "firstName", provider.getFirstName(),
            "lastName", provider.getLastName(),
            "imageUrl", profile != null ? profile.getImageUrl() : null));
  }
}
