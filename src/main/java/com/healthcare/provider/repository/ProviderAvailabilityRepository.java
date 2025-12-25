package com.healthcare.provider.repository;

import com.healthcare.provider.model.ProviderAvailability;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProviderAvailabilityRepository
    extends MongoRepository<ProviderAvailability, String> {
  List<ProviderAvailability> findByProviderId(Long providerId);

  boolean existsByProviderIdAndDate(Long providerId, LocalDate Date);

  Optional<ProviderAvailability> findByIdAndProviderId(String id, Long providerId);

  List<ProviderAvailability> findByProviderIdAndDate(Long providerId, LocalDate date);
}
