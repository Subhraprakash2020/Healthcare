package com.healthcare.provider.service;

import com.healthcare.provider.model.ProviderAvailability;

public interface ProviderAvailabilityService {

  void addAvailability(ProviderAvailability request, String email);

  void updateAvailability(String availabilityId, ProviderAvailability request, String email);

  void deleteAvailability(String availabilityId, String email);
}
