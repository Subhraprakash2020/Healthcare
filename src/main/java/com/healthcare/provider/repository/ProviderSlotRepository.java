package com.healthcare.provider.repository;

import com.healthcare.provider.model.ProvidersSlot;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProviderSlotRepository extends MongoRepository<ProvidersSlot, String> {
  List<ProvidersSlot> findByProviderIdAndDateOrderByStartTime(Long providerId, LocalDate date);

  boolean existsByProviderId(Long providerId);

  boolean existsByProviderIdAndDate(Long providerId, LocalDate date);

  boolean existsByAvailabilityId(String availavilityId);
}
