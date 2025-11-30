package com.healthcare.provider.controller;

import com.healthcare.patient.exception.ResourceNotFoundException;
import com.healthcare.provider.model.Provider;
import com.healthcare.provider.service.ProviderService;
import com.healthcare.provider.service.SequenceGeneratorService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare")
public class ProviderController {

  @Autowired ProviderService providerService;

  @Autowired
  @Qualifier("providerSequenceGeneratorService")
  SequenceGeneratorService sequenceGeneratorService;

  @RequestMapping("/providers")
  public ResponseEntity<List<Provider>> getProviders() {
    return new ResponseEntity<>(providerService.getAllProviders() , HttpStatus.OK);
  }

  @RequestMapping("/providers/{id}")
  public ResponseEntity<?> getProviderById(@PathVariable Long id) {
    Provider provider = providerService.getProviderById(id);
    if (provider != null) {
      return ResponseEntity.ok(provider);
    } else {
      throw new ResourceNotFoundException("Provider not found with id: " + id);
    }
  }

  @PostMapping("/provider")
  public void addProvider(@RequestBody Provider provider) {
    provider.setId(sequenceGeneratorService.generateSequence(Provider.SEQUENCE_NAME));
    providerService.addProvider(provider);
  }

  public void updateProvider(@RequestBody Provider provider) {
    // Implementation to update an existing provider
    providerService.updateProvider(provider);
  }

  @DeleteMapping("/provider/{id}")
  public void deleteProvider(@PathVariable Long id) {
    // Implementation to delete a provider by id
    providerService.deleteProvider(id);
  }


  @GetMapping("/csrf-token")
  public CsrfToken getCsrfToken(HttpServletRequest request) {
    // Implementation to get CSRF token

    return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
  }
}
