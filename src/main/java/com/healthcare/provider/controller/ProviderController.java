package com.healthcare.provider.controller;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.payload.request.LoginRequestProvider;
import com.healthcare.provider.repository.ProviderRepository;
import com.healthcare.provider.service.ProviderServices;
import com.healthcare.provider.service.SequenceGeneratorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare")
public class ProviderController {

  private final ProviderServices providerService;
  @Autowired ProviderRepository providerRepository;

  // FIX: Removed manual instantiation (new ProviderServiceImpl()) and used constructor injection.
  // Spring will now inject the ProviderServiceImpl bean, which will have its dependencies (like
  // AuthenticationManager) properly injected.
  @Autowired
  public ProviderController(ProviderServices providerService) {
    this.providerService = providerService;
  }

  @Autowired
  @Qualifier("providerSequenceGeneratorService")
  SequenceGeneratorService sequenceGeneratorService;

  // This method work is for provider registration
  @PostMapping("/providers/SignUp")
  public ResponseEntity<?> providerRegistration(@RequestBody Provider provider) {
    if (providerRepository.existsByEmail(provider.getEmail())) {
      return ResponseEntity.badRequest().body("Error: Email is already in use!");
    }
    provider.setId(sequenceGeneratorService.generateSequence(Provider.SEQUENCE_NAME));
    providerService.addProvider(provider);
    return ResponseEntity.ok("Provider registered successfully!");
  }

  @PostMapping("/providers/login")
  public ResponseEntity<?> loginProvider(@Valid @RequestBody LoginRequestProvider loginrequest) {
    System.out.println("In loginProvider method");
    // Implementation for provider login
    return providerService.verify(loginrequest);
  }
}
