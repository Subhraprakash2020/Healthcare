package com.healthcare.provider.controller;

import com.healthcare.provider.service.ProviderSlotGenerateService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare/provider/slot")
public class ProviderSlotController {
  @Autowired ProviderSlotGenerateService slotService;

  @PostMapping("/generate/{availabilityId}")
  public ResponseEntity<?> generateSlots(@PathVariable String availabilityId, Principal principal) {
    slotService.generateSlotsFromAvailability(availabilityId, principal.getName());

    return ResponseEntity.ok("Slots generated successfully");
  }
}
