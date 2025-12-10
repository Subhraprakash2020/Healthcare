package com.healthcare.provider.repository;

import com.healthcare.provider.model.Provider;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends MongoRepository<Provider, Long> {
  Optional<Provider> findByEmail(String email);

  Boolean existsByEmail(String email);

  Boolean existsByPassWord(String passWord);

  Optional<Provider> findById(Long id);
}
