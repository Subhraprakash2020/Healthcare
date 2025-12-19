package com.healthcare.provider.service.impl;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.model.ProviderAddress;
import com.healthcare.provider.model.ProviderDetails;
import com.healthcare.provider.model.ProviderProfileImage;
import com.healthcare.provider.payload.request.ProviderDetailsRequest;
import com.healthcare.provider.repository.ProviderAddressRepository;
import com.healthcare.provider.repository.ProviderDetailsRepository;
import com.healthcare.provider.repository.ProviderProfileRepository;
import com.healthcare.provider.repository.ProviderRepository;
import com.healthcare.provider.service.ProviderDetailsService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProviderDetailsServiceImpl implements ProviderDetailsService {
  @Autowired private ProviderRepository providerRepository;

  @Autowired private ProviderDetailsRepository providerDetailsRepository;

  @Autowired private ProviderAddressRepository providerAddressRepository;

  @Autowired private ProviderProfileRepository providerProfileRepository;

  @Override
  public ResponseEntity<?> addProviderDetails(ProviderDetailsRequest request) {

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
    address.setProviderId(providerId);
    address.setCity(request.getCity());
    address.setState(request.getState());
    address.setZip(request.getZip());
    providerAddressRepository.save(address);

    ProviderDetails details = new ProviderDetails();
    details.setProviderId(providerId);
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

  @Override
  public ResponseEntity<?> getProviderDetailsByProviderId(Long providerId) {

    List<ProviderDetails> detailsList = providerDetailsRepository.findByProviderId(providerId);

    if (detailsList.isEmpty()) {
      throw new RuntimeException("Provider details not found");
    }
    ProviderDetails details = detailsList.get(0);

    List<ProviderAddress> addressList = providerAddressRepository.findByProviderId(providerId);

    if (addressList.isEmpty()) {
      throw new RuntimeException("Provider address not found");
    }
    ProviderAddress address = addressList.get(0);

    Optional<ProviderProfileImage> profileImage =
        providerProfileRepository.findByProviderId(providerId);

    Map<String, Object> response = new HashMap<>();
    response.put("providerDetails", details);
    response.put("providerAddress", address);
    response.put("providerProfileImage", profileImage.orElse(null));

    return ResponseEntity.ok(response);
  }
}
