package com.healthcare.provider.service.impl;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.model.ProviderAvailability;
import com.healthcare.provider.model.ProvidersSlot;
import com.healthcare.provider.model.SlotStatus;
import com.healthcare.provider.repository.ProviderAvailabilityRepository;
import com.healthcare.provider.repository.ProviderRepository;
import com.healthcare.provider.repository.ProviderSlotRepository;
import com.healthcare.provider.service.ProviderSlotGenerateService;
import java.time.LocalDate;
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

  @Override
  public void deleteSlotsByAvailabilityId(String slotAvailabilityId, String email) {
    Provider provider =
        providerRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Provider not found"));

    Long providerId = provider.getId();

    ProvidersSlot slot =
        providerSlotRepository
            .findByIdAndProviderId(slotAvailabilityId, providerId)
            .orElseThrow(() -> new RuntimeException("Slot not found"));

    providerSlotRepository.delete(slot);
  }

  @Override
  public List<ProvidersSlot> getSlotsByAvailabilityId(String slotAvailabilityId, String email) {
    Provider provider =
        providerRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Provider not found"));
    long providerId = provider.getId();
    return providerSlotRepository.findByAvailabilityId(slotAvailabilityId).stream()
        .filter(slot -> slot.getProviderId() == providerId)
        .toList();
  }

  @Override
  public void updateSlotBySlotId(String slotId, String email, ProvidersSlot request) {
    Provider provider =
        providerRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Provider not found"));

    Long providerId = provider.getId();

    ProvidersSlot existingSlot =
        providerSlotRepository
            .findByIdAndProviderId(slotId, providerId)
            .orElseThrow(() -> new RuntimeException("Slot not found"));

    existingSlot.setStartTime(request.getStartTime());
    existingSlot.setEndTime(request.getEndTime());
    existingSlot.setMaxCapacity(request.getMaxCapacity());
    existingSlot.setStatus(request.getStatus());

    providerSlotRepository.save(existingSlot);
  }

  @Override
  public List<ProvidersSlot> getSlotsForPatient(
      Long providerId, String availabilityId, LocalDate date) {
    LocalDate today = LocalDate.now();
    LocalTime now = LocalTime.now();

    if (date == null) {
      date = today;
    }

    for (int i = 0; i < 30; i++) {
      LocalDate searchDate = date.plusDays(i);

      List<ProvidersSlot> slots =
          providerSlotRepository.findByProviderIdAndAvailabilityIdAndDateOrderByStartTime(
              providerId, availabilityId, searchDate);

      if (slots.isEmpty()) {
        continue; // try next day
      }

      // 🟢 If today → filter past time slots
      if (searchDate.equals(today)) {
        slots = slots.stream().filter(slot -> slot.getStartTime().isAfter(now)).toList();
      }

      if (!slots.isEmpty()) {
        return slots; // 👈 FIRST future slots found
      }
    }

    // // ❌ Past date → no slots
    // if (date.isBefore(today)) {
    //   return List.of();
    // }

    // List<ProvidersSlot> slots =
    //
    // providerSlotRepository.findByProviderIdAndAvailabilityIdAndDateOrderByStartTime(providerId,availabilityId, date);

    // // ✅ Future date → return all (AVAILABLE + FULL)
    // if (date.isAfter(today)) {
    //   return slots;
    // }

    // ✅ Today → return only future time slots
    return List.of();
  }
}
