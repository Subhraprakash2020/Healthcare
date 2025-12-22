package com.healthcare.provider.controller;

import com.healthcare.provider.model.ProviderAvailability;
import com.healthcare.provider.model.ProvidersSlot;
import com.healthcare.provider.payload.request.SlotGenerateRequest;
import com.healthcare.provider.repository.ProviderAvailabilityRepository;
import com.healthcare.provider.repository.ProviderSlotRepository;
import com.healthcare.provider.service.SlotServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare/provider")
public class ProviderControllerBooking {
  @Autowired private ProviderAvailabilityRepository providerAvailabilityRepository;

  @Autowired private ProviderSlotRepository providerSlotRepository;

  @Autowired private SlotServices slotServices;

  @PostMapping("/availability")
  public ResponseEntity<?> saveAvailability(@RequestBody ProviderAvailability availability) {
    providerAvailabilityRepository.save(availability);
    return ResponseEntity.ok("Availability Saved");
  }

  @PostMapping("/slots/generate")
  public ResponseEntity<?> generateSlots(@RequestBody SlotGenerateRequest request) {
    ProviderAvailability availability =
        providerAvailabilityRepository
            .findByProviderId(request.getProviderId())
            .orElseThrow(() -> new RuntimeException("Availability not found"));

    List<ProvidersSlot> slots = slotServices.generateSlots(availability, request.getDate());

    providerSlotRepository.saveAll(slots);
    return ResponseEntity.ok(slots);
  }
}
