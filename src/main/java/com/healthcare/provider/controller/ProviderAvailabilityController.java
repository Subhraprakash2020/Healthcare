package com.healthcare.provider.controller;

import com.healthcare.provider.model.ProviderAvailability;
import com.healthcare.provider.service.ProviderAvailabilityService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare/provider/availability")
public class ProviderAvailabilityController {
  @Autowired private ProviderAvailabilityService availabilityService;

  @PostMapping("/add")
  public ResponseEntity<?> addAvailability(
      @RequestBody ProviderAvailability request, Principal principal) {
    String email = principal.getName();
    availabilityService.addAvailability(request, email);

    return ResponseEntity.ok("Availability added successfully");
  }

  @PutMapping("/{availabilityId}")
  public ResponseEntity<?> updateAvailability(
      @PathVariable String availabilityId,
      @RequestBody ProviderAvailability request,
      Principal principal) {

    availabilityService.updateAvailability(availabilityId, request, principal.getName());

    return ResponseEntity.ok("Availability updated successfully");
  }

  @DeleteMapping("/{availabilityId}")
  public ResponseEntity<?> deleteAvailability(
      @PathVariable String availabilityId,
      Principal principal,
      @RequestBody ProviderAvailability request) {

    String email = principal.getName();
    availabilityService.deleteAvailability(availabilityId, email);

    return ResponseEntity.ok("Availability deleted successfully");
  }
}
