package com.healthcare.provider.controller;

import com.healthcare.provider.service.ProviderSlotGenerateService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

  @DeleteMapping("/delete/{availabilityId}")
  public ResponseEntity<?> deleteSlots(@PathVariable String availabilityId, Principal principal) {
    slotService.deleteSlotsByAvailabilityId(availabilityId, principal.getName());
    return ResponseEntity.ok("Slots deleted successfully");
  }

  @GetMapping("/{availabilityId}")
  public ResponseEntity<?> getSlots(@PathVariable String availabilityId, Principal principal) {
    String email = principal.getName();
    return ResponseEntity.ok(slotService.getSlotsByAvailabilityId(availabilityId, email));
  }

  //   @PutMapping("/update/{slotId}")
  //   public ResponseEntity<?> updateSlot(
  //       @PathVariable String slotId, Principal principal) {
  //     // String email = principal.getName();
  //     // // You would typically get the ProvidersSlot request from the request body
  //     // // slotService.updateSlotBySlotId(slotId, email, request);
  //     return ResponseEntity.ok("Slot updated successfully");
  //       }
}
