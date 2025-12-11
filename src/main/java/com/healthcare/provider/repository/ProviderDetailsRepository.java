package com.healthcare.provider.repository;

import com.healthcare.provider.model.LevelOfTreatment;
import com.healthcare.provider.model.Practices;
import com.healthcare.provider.model.ProviderDetails;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProviderDetailsRepository extends MongoRepository<ProviderDetails, String> {
  Optional<ProviderDetails> findByProviderId(Long providerId);

  boolean existsByProviderId(Long providerId); // FIXED

  List<ProviderDetails> findByPractices(Practices practices);

  List<ProviderDetails> findByLevelOfTreatment(LevelOfTreatment levelOfTreatment);

  List<ProviderDetails> findByClinicianName(String clinicName);
}
