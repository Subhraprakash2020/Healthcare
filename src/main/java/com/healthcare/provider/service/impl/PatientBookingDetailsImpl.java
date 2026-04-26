package com.healthcare.provider.service.impl;

import com.healthcare.provider.model.BookingStatus;
import com.healthcare.provider.model.Provider;
import com.healthcare.provider.repository.PatientBookingDetailsRepository;
import com.healthcare.provider.repository.ProviderRepository;
import com.healthcare.provider.service.PatientBookingDetailsService;

import java.time.LocalDate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientBookingDetailsImpl implements PatientBookingDetailsService {
  @Autowired private ProviderRepository providerRepository;
  @Autowired private PatientBookingDetailsRepository patientBookingDetailsRepository;

  @Override
  public Map<String, Object> getBookedPatientCountForProvider(String providerEmail, LocalDate date){
    Provider provider = providerRepository.findByEmail(providerEmail).orElseThrow(() -> new RuntimeException("Provider not found"));

    long bookedPatientCount = patientBookingDetailsRepository.countByProviderIdAndBookingDateAndStatus(provider.getId(), date, BookingStatus.CONFIRMED);

    return Map.of("Provider Id",provider.getId(), "date", date, "BookedPatientCount", bookedPatientCount);
  }
}
