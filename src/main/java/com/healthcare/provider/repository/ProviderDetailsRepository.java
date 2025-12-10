package com.healthcare.provider.repository;

import com.healthcare.provider.model.ProviderDetails;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProviderDetailsRepository extends MongoRepository<ProviderDetails, String> {
  Optional<ProviderDetails> findByProviderId(Long providerId);

  boolean existsByProviderId(Long providerId); // FIXED
}
