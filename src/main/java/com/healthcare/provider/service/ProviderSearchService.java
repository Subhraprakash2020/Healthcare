package com.healthcare.provider.service;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.model.ProviderAddress;
import com.healthcare.provider.model.ProviderDetails;
import com.healthcare.provider.model.ProviderSearchRequest;
import com.healthcare.provider.repository.ProviderAddressRepository;
import com.healthcare.provider.repository.ProviderDetailsRepository;
import com.healthcare.provider.repository.ProviderRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderSearchService {
  @Autowired private ProviderDetailsRepository providerDetailsRepository;

  @Autowired private ProviderAddressRepository providerAddressRepository;

  @Autowired private ProviderRepository providerRepository;

  public List<Map<String, Object>> searchProviders(ProviderSearchRequest request) {
    List<ProviderDetails> detailsList;

    if (request.getClinicianName() == null
        && request.getLevelOfTreatment() == null
        && request.getPractices() == null
        && request.getZip() == null) {
      detailsList = providerDetailsRepository.findAll();
    } else {
      detailsList = providerDetailsRepository.findAll();

      if (request.getClinicianName() != null) {
        detailsList =
            detailsList.stream()
                .filter(
                    d ->
                        d.getClinicianName()
                            .toLowerCase()
                            .contains(request.getClinicianName().toLowerCase()))
                .toList();
      }

      if (request.getPractices() != null) {
        detailsList =
            detailsList.stream().filter(d -> d.getPractices() == request.getPractices()).toList();
      }

      if (request.getLevelOfTreatment() != null) {
        detailsList =
            detailsList.stream()
                .filter(d -> d.getLevelOfTreatment() == request.getLevelOfTreatment())
                .toList();
      }

      if (request.getZip() != null) {
        List<ProviderAddress> address = providerAddressRepository.findByZip(request.getZip());
        Set<Long> providerId =
            address.stream().map(ProviderAddress::getProviderId).collect(Collectors.toSet());

        detailsList =
            detailsList.stream().filter(d -> providerId.contains(d.getProviderId())).toList();
      }
    }

    List<Map<String, Object>> response = new ArrayList<>();

    for (ProviderDetails details : detailsList) {
      Provider provider = providerRepository.findById(details.getProviderId()).orElse(null);
      ProviderAddress address =
          providerAddressRepository.findByProviderId(details.getProviderId()).stream()
              .findFirst()
              .orElse(null);

      Map<String, Object> map = new HashMap<>();
      map.put("provider", provider);
      map.put("details", details);
      map.put("address", address);

      response.add(map);
    }

    return response;
  }
}
