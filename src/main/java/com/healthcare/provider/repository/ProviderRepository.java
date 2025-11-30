package com.healthcare.provider.repository;

import com.healthcare.provider.model.Provider;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends MongoRepository<Provider, Long> {

    Provider findByUsername(String username);

    
}
