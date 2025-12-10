package com.healthcare.provider.repository;

import com.healthcare.provider.model.ProviderAddress;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProviderAddressRepository extends MongoRepository<ProviderAddress, String> {
  Optional<ProviderAddress> findByProviderId(Long providerId);

  boolean existsByProviderId(Long providerId);
}
