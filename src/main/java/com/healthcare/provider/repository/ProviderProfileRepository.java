package com.healthcare.provider.repository;

import com.healthcare.provider.model.ProviderProfileImage;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderProfileRepository extends MongoRepository<ProviderProfileImage, String> {

  Optional<ProviderProfileImage> findByProviderId(String providerId);
}
