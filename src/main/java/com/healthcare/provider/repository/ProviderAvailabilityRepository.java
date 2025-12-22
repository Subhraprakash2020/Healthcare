package com.healthcare.provider.repository;

import com.healthcare.provider.model.ProviderAvailability;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProviderAvailabilityRepository
    extends MongoRepository<ProviderAvailability, String> {
  Optional<ProviderAvailability> findByProviderId(Long providerId);
}
