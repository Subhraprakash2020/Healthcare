package com.healthcare.provider.service.impl;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.model.ProviderAvailability;
import com.healthcare.provider.repository.ProviderAvailabilityRepository;
import com.healthcare.provider.repository.ProviderRepository;
import com.healthcare.provider.repository.ProviderSlotRepository;
import com.healthcare.provider.service.ProviderAvailabilityService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderAvailabilityServiceImpl implements ProviderAvailabilityService {
  @Autowired private ProviderAvailabilityRepository providerAvailabilityRepository;

  @Autowired private ProviderSlotRepository providerSlotRepository;

  @Autowired private ProviderRepository providerRepository;

  @Override
  public void addAvailability(ProviderAvailability request, String email) {
    Provider provider =
        providerRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Provider not found"));

    Long providerId = provider.getId();

    if (!request.getStartTime().isBefore(request.getEndTime())) {
      throw new RuntimeException("Start time must be before end time");
    }

    List<ProviderAvailability> existingAvailabilities =
        providerAvailabilityRepository.findByProviderIdAndDate(providerId, request.getDate());

    for (ProviderAvailability existing : existingAvailabilities) {
      boolean overlaps =
          request.getStartTime().isBefore(existing.getEndTime())
              && request.getEndTime().isAfter(existing.getStartTime());

      if (overlaps) {
        throw new RuntimeException(
            "Availability overlaps with existing time slots:"
                + existing.getStartTime()
                + "-"
                + existing.getEndTime());
      }
    }

    ProviderAvailability availability = new ProviderAvailability();
    availability.setProviderId(providerId);
    availability.setDate(request.getDate());
    availability.setStartTime(request.getStartTime());
    availability.setEndTime(request.getEndTime());
    availability.setSlotDuration(request.getSlotDuration());
    availability.setCapacityPerSlot(request.getCapacityPerSlot());

    providerAvailabilityRepository.save(availability);
  }

  @Override
  public void updateAvailability(
      String availabilityId, ProviderAvailability request, String email) {
    Provider provider =
        providerRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Provider not found"));

    Long providerId = provider.getId();

    ProviderAvailability existing =
        providerAvailabilityRepository
            .findByIdAndProviderId(availabilityId, providerId)
            .orElseThrow(() -> new RuntimeException("Availability not found"));

    if (providerSlotRepository.existsByProviderIdAndDate(providerId, existing.getDate())) {
      throw new RuntimeException("Cannot update availability. Slots already generated.");
    }

    if (!request.getStartTime().isBefore(request.getEndTime())) {
      throw new RuntimeException("Invalid time range");
    }

    List<ProviderAvailability> sameDateAvailabilities =
        providerAvailabilityRepository.findByProviderIdAndDate(providerId, existing.getDate());

    for (ProviderAvailability other : sameDateAvailabilities) {
      if (other.getId().equals(existing.getId())) continue;

      boolean overlaps =
          request.getStartTime().isBefore(other.getEndTime())
              && request.getEndTime().isAfter(other.getStartTime());

      if (overlaps) {
        throw new RuntimeException("Updated time overlaps with existing availability");
      }
    }

    existing.setStartTime(request.getStartTime());
    existing.setEndTime(request.getEndTime());
    existing.setSlotDuration(request.getSlotDuration());
    existing.setCapacityPerSlot(request.getCapacityPerSlot());

    providerAvailabilityRepository.save(existing);
  }

  @Override
  public void deleteAvailability(String availabilityId, String email) {
    Provider provider =
        providerRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Provider not found"));

    Long providerId = provider.getId();

    ProviderAvailability availability =
        providerAvailabilityRepository
            .findByIdAndProviderId(availabilityId, providerId)
            .orElseThrow(() -> new RuntimeException("Availability not found"));

    if (providerSlotRepository.existsByProviderIdAndDate(providerId, availability.getDate())) {
      throw new RuntimeException("Cannot delete availability. Slots already generated.");
    }

    providerAvailabilityRepository.delete(availability);
  }

  @Override
  public List<ProviderAvailability> getAvailability(String email, String availabilityId) {
    Provider provider =
        providerRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Provider not found"));

    Long providerId = provider.getId();

    if (availabilityId != null && !availabilityId.isEmpty()) {
      ProviderAvailability availability =
          providerAvailabilityRepository
              .findByIdAndProviderId(availabilityId, providerId)
              .orElseThrow(() -> new RuntimeException("Availability not found"));
      return List.of(availability);
    } else {
      return providerAvailabilityRepository.findByProviderId(providerId);
    }
  }

  @Override
    public List<ProviderAvailability> getAllAvailabilities(Long providerId, LocalDate date) {
        LocalDate today = LocalDate.now();
    LocalDate startDate = (date != null) ? date : today;
    LocalTime nowTime = LocalTime.now();

    for (int i = 0; i < 7; i++) {

        LocalDate checkDate = startDate.plusDays(i);

        List<ProviderAvailability> availabilities =
            providerAvailabilityRepository
                .findByProviderIdAndDateOrderByStartTime(providerId, checkDate);

        if (availabilities.isEmpty()) {
            continue;
        }

        if (checkDate.equals(today)) {
            availabilities.removeIf(
                avail -> !avail.getEndTime().isAfter(nowTime)
            );
        }

        if (!availabilities.isEmpty()) {
            return availabilities;
        }
    }

    return List.of();
    }
}
