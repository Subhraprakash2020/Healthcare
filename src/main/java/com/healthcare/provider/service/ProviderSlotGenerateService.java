package com.healthcare.provider.service;

public interface ProviderSlotGenerateService {
  void generateSlotsFromAvailability(String availabilityId, String email);
}
