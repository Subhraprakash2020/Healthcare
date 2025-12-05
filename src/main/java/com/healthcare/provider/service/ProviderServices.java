package com.healthcare.provider.service;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.payload.request.LoginRequestProvider;
import org.springframework.http.ResponseEntity;

public interface ProviderServices {
  public void addProvider(Provider provider);

  public ResponseEntity<?> verify(LoginRequestProvider loginRequest);
}
