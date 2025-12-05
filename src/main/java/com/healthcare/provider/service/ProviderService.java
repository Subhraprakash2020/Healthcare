package com.healthcare.provider.service;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.repository.ProviderRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

  @Autowired ProviderRepository providerRepository;

  public List<Provider> getAllProviders() {
    // Implementation to fetch all providers
    return providerRepository.findAll();
  }

  public Provider getProviderById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Provider id cannot be null");
    }
    return providerRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Provider not found with id: " + id));
  }

  public void addProvider(Provider provider) {
    // Implementation to add a new provider
    Provider nonNullProvider = Objects.requireNonNull(provider, "Provider cannot be null");
    providerRepository.save(nonNullProvider);
  }

  public void updateProvider(Provider provider) {
    // Implementation to update an existing provider
    Provider nonNullProvider = Objects.requireNonNull(provider, "Provider cannot be null");
    providerRepository.save(nonNullProvider);
  }

  public void deleteProvider(Long id) {
    // Implementation to delete a provider by id
    if (id == null) {
      throw new IllegalArgumentException("Provider id cannot be null");
    }
    providerRepository.deleteById(id);
  }
}
