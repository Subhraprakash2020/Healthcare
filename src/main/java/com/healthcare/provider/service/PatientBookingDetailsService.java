package com.healthcare.provider.service;

import java.time.LocalDate;
import java.util.Map;

public interface PatientBookingDetailsService {
  Map<String, Object> getBookedPatientCountForProvider(String providerEmail, LocalDate date);
}
