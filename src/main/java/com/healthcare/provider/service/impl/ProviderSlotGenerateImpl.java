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

    ProviderAvailability availability =
        providerAvailabilityRepository
            .findByIdAndProviderId(availabilityId, provider.getId())
            .orElseThrow(() -> new RuntimeException("Availability not found"));

    if (providerSlotRepository.existsByAvailabilityId(availabilityId)) {
      throw new RuntimeException("Slots already generated for this availability");
    }

    int slotDuration = availability.getSlotDuration();
    int capacity = availability.getCapacityPerSlot();

    if (slotDuration <= 0) {
      throw new IllegalArgumentException("Slot duration must be > 0");
    }

    LocalTime slotStart = availability.getStartTime();
    LocalTime availabilityEnd = availability.getEndTime();

    if (slotStart.isAfter(availabilityEnd)) {
      throw new IllegalArgumentException("Invalid availability time range");
    }

    final int MAX_SLOTS_PER_DAY = 50;

    List<ProvidersSlot> slots = new ArrayList<>();

    while (!slotStart.plusMinutes(slotDuration).isAfter(availabilityEnd)
        && slots.size() < MAX_SLOTS_PER_DAY) {

      ProvidersSlot slot = new ProvidersSlot();
      slot.setProviderId(provider.getId());
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

    boolean isDateProvided = (date != null);

    if (!isDateProvided) {
      date = today;
    }

    List<ProvidersSlot> slots =
        providerSlotRepository.findByProviderIdAndAvailabilityIdAndDateOrderByStartTime(
            providerId, availabilityId, date);

    if (date.equals(today)) {
      slots = slots.stream().filter(slot -> slot.getStartTime().isAfter(now)).toList();
    }

    return slots;
  }

  @Override
  public List<ProvidersSlot> getSlotForProvider(Long providerId, LocalDate date) {
    LocalDate today = LocalDate.now();
    LocalTime now = LocalTime.now();
    boolean isDateProvided = (date != null);
    if (!isDateProvided) {
      date = today;
    }
    List<ProvidersSlot> slots =
        providerSlotRepository.findByProviderIdAndDateOrderByStartTime(providerId, date);
    if (date.equals(today)) {
      slots = slots.stream().filter(slot -> slot.getStartTime().isAfter(now)).toList();
    }
    return slots;
  }
}
