package com.healthcare.provider.service;

import com.healthcare.provider.model.ProviderAvailability;

import java.time.LocalDate;
import java.util.List;

public interface ProviderAvailabilityService {

  void addAvailability(ProviderAvailability request, String email);

  void updateAvailability(String availabilityId, ProviderAvailability request, String email);

  void deleteAvailability(String availabilityId, String email);

  List<ProviderAvailability> getAvailability(String email, String availabilityId);

  List<ProviderAvailability> getAllAvailabilities(Long providerId, LocalDate date);
}
