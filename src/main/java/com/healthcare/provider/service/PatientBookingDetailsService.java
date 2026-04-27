package com.healthcare.provider.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PatientBookingDetailsService {
  Map<String, Object> getBookedPatientCountForProvider(String providerEmail, LocalDate date);

  List<Map<String, Object>> getBookedPatientDetailsForProvider(
      String providerEmail, LocalDate date);
}
