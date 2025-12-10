package com.healthcare.provider.controller;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.model.ProviderAddress;
import com.healthcare.provider.model.ProviderDetails;
import com.healthcare.provider.payload.request.ProviderDetailsRequest;
import com.healthcare.provider.repository.ProviderAddressRepository;
import com.healthcare.provider.repository.ProviderDetailsRepository;
import com.healthcare.provider.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("healthcare/providers/details")
public class ProviderDetailsController {
  @Autowired private ProviderRepository providerRepository;

  @Autowired private ProviderDetailsRepository providerDetailsRepository;

  @Autowired ProviderAddressRepository providerAddressRepository;

  @PostMapping("/add")
  public ResponseEntity<?> addDetails(@RequestBody ProviderDetailsRequest request) {

    Long providerId = request.getProviderId();

    Provider provider =
        providerRepository
            .findById(providerId)
            .orElseThrow(() -> new RuntimeException("Provider Not Found"));

    if (providerDetailsRepository.existsByProviderId(providerId)) {
      return ResponseEntity.badRequest()
          .body("Provider details already exist. Please update instead.");
    }

    if (providerAddressRepository.existsByProviderId(providerId)) {
      return ResponseEntity.badRequest()
          .body("Provider address already exists. Please update instead.");
    }

    ProviderAddress address = new ProviderAddress();
    address.setProviderId(provider.getId());
    address.setCity(request.getCity());
    address.setState(request.getState());
    address.setZip(request.getZip());
    providerAddressRepository.save(address);

    ProviderDetails details = new ProviderDetails();
    details.setProviderId(provider.getId());
    details.setProviderEmail(provider.getEmail());
    details.setProviderUserId(provider.getUserId());

    details.setClinicianName(request.getClinicianName());
    details.setPractices(request.getPractices());
    details.setLevelOfTreatment(request.getLevelOfTreatment());
    details.setPatientAgeBracket(request.getPatientAgeBracket());
    details.setExperienceYears(request.getExperienceYears());
    details.setAvailabilityInWeek(request.getAvailabilityInWeek());
    details.setAvailabilityTime(request.getAvailabilityTime());
    details.setConsultingFee(request.getConsultingFee());
    details.setAboutMe(request.getAboutMe());

    details.setAddressId(address.getId());

    providerDetailsRepository.save(details);

    return ResponseEntity.ok("Provider details added successfully.");
  }
}
