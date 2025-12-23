package com.healthcare.provider.service.impl;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.model.ProviderAvailability;
import com.healthcare.provider.model.ProvidersSlot;
import com.healthcare.provider.model.SlotStatus;
import com.healthcare.provider.repository.ProviderAvailabilityRepository;
import com.healthcare.provider.repository.ProviderRepository;
import com.healthcare.provider.repository.ProviderSlotRepository;
import com.healthcare.provider.service.ProviderSlotGenerateService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderSlotGenerateImpl implements ProviderSlotGenerateService {
  @Autowired ProviderRepository providerRepository;

  @Autowired ProviderAvailabilityRepository providerAvailabilityRepository;

  @Autowired ProviderSlotRepository providerSlotRepository;

  @Override
  public void generateSlotsFromAvailability(String availabilityId, String email) {
    Provider provider =
        providerRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Provider not found"));

    Long providerId = provider.getId();

    ProviderAvailability availability =
        providerAvailabilityRepository
            .findByIdAndProviderId(availabilityId, providerId)
            .orElseThrow(() -> new RuntimeException("Availability not found"));

    if (providerSlotRepository.existsByAvailabilityId(availabilityId)) {
      throw new RuntimeException("Slots already generated for this availability");
    }

    LocalTime slotStart = availability.getStartTime();
    LocalTime availabilityEnd = availability.getEndTime();

    int slotDuration = availability.getSlotDuration();
    int capacity = availability.getCapacityPerSlot();

    List<ProvidersSlot> slots = new ArrayList<>();

    while (slotStart.plusMinutes(slotDuration).compareTo(availabilityEnd) <= 0) {

      ProvidersSlot slot = new ProvidersSlot();
      slot.setProviderId(providerId);
      slot.setAvailabilityId(availability.getId());
      slot.setDate(availability.getDate());

      slot.setStartTime(slotStart);
      slot.setEndTime(slotStart.plusMinutes(slotDuration));

      slot.setMaxCapacity(capacity);
      slot.setBookedCount(0);
      slot.setStatus(SlotStatus.AVAILABLE);

      slots.add(slot);

      slotStart = slotStart.plusMinutes(slotDuration);
    }

    providerSlotRepository.saveAll(slots);
  }
}
