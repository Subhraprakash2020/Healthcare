package com.healthcare.provider.service;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.payload.request.LoginRequestProvider;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ProviderServices {
  public void addProvider(Provider provider);

  public ResponseEntity<?> verify(LoginRequestProvider loginRequest);

  public List<Provider> getAllProviders();

  public Provider getProviderById(Long id);

  public Provider updateProvider(Long id, Provider providerDetails);

  public Provider deleteProvider(Long id);
}
