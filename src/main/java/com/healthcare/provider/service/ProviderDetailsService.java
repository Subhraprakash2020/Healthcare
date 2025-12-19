package com.healthcare.provider.service;

import com.healthcare.provider.payload.request.ProviderDetailsRequest;
import org.springframework.http.ResponseEntity;

public interface ProviderDetailsService {

  ResponseEntity<?> addProviderDetails(ProviderDetailsRequest request);

  ResponseEntity<?> getProviderDetailsByProviderId(Long providerId);
}
