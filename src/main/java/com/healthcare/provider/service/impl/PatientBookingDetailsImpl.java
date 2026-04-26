package com.healthcare.provider.service.impl;

import com.healthcare.patient.model.Booking;
import com.healthcare.patient.model.Patient;
import com.healthcare.patient.repository.PatientRepository;
import com.healthcare.provider.model.BookingStatus;
import com.healthcare.provider.model.Provider;
import com.healthcare.provider.repository.PatientBookingDetailsRepository;
import com.healthcare.provider.repository.ProviderRepository;
import com.healthcare.provider.service.PatientBookingDetailsService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientBookingDetailsImpl implements PatientBookingDetailsService {
  @Autowired private ProviderRepository providerRepository;
  @Autowired private PatientBookingDetailsRepository patientBookingDetailsRepository;
  @Autowired private PatientRepository patientRepository;

  @Override
  public Map<String, Object> getBookedPatientCountForProvider(
      String providerEmail, LocalDate date) {
    Provider provider =
        providerRepository
            .findByEmail(providerEmail)
            .orElseThrow(() -> new RuntimeException("Provider not found"));

    long bookedPatientCount =
        patientBookingDetailsRepository.countByProviderIdAndBookingDateAndStatus(
            provider.getId(), date, BookingStatus.CONFIRMED);

    return Map.of(
        "Provider Id", provider.getId(), "date", date, "BookedPatientCount", bookedPatientCount);
  }

  @Override
  public List<Map<String, Object>> getBookedPatientDetailsForProvider(
      String providerEmail, LocalDate date) {
    Provider provider =
        providerRepository
            .findByEmail(providerEmail)
            .orElseThrow(() -> new RuntimeException("Provider not found"));

    List<Booking> bookings =
        patientBookingDetailsRepository.findByProviderIdAndBookingDateAndStatus(
            provider.getId(), date, BookingStatus.CONFIRMED);

    return bookings.stream()
        .map(
            booking -> {
              Patient patient =
                  patientRepository
                      .findById(booking.getPatientId())
                      .orElseThrow(() -> new RuntimeException("Patient not found"));

              Map<String, Object> map = new HashMap<>();
              map.put("bookingId", booking.getId());
              map.put("patientId", patient.getId());
              map.put("patientName", patient.getFirstName() + " " + patient.getLastName());
              map.put("age", patient.getAge());
              map.put("gender", patient.getGender());
              map.put("phoneNumber", patient.getPhoneNumber());
              map.put("email", patient.getEmail());
              map.put("address", patient.getAddress());
              map.put("status", booking.getStatus());
              map.put("bookingDate", booking.getBookingDate());
              map.put("bookingTime", booking.getBookingTime());
              return map;
            })
        .toList();
  }
}
