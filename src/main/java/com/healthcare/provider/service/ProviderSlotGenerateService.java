package com.healthcare.provider.service;

import com.healthcare.provider.model.ProvidersSlot;
import java.time.LocalDate;
import java.util.List;

public interface ProviderSlotGenerateService {
  void generateSlotsFromAvailability(String availabilityId, String email);

  void deleteSlotsByAvailabilityId(String slotAvailabilityId, String email);

  //   void updateSlotsByAvailabilityId(String slotAvailabilityId, String email, ProvidersSlot
  // request);
  void updateSlotBySlotId(String slotId, String email, ProvidersSlot request);

  List<ProvidersSlot> getSlotsByAvailabilityId(String slotAvailabilityId, String email);

  List<ProvidersSlot> getSlotsForPatient(String availabilityId, LocalDate date);
}
