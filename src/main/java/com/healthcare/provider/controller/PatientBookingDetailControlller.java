package com.healthcare.provider.controller;

import com.healthcare.provider.service.PatientBookingDetailsService;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare/providers/booking")
public class PatientBookingDetailControlller {
  @Autowired private PatientBookingDetailsService patientBookingDetailsService;

  @GetMapping("/count")
  public ResponseEntity<?> getBookedPatientCountForProvider(
      Principal principal, @RequestParam String date) {
    LocalDate bookingDate = LocalDate.parse(date);
    return ResponseEntity.ok(
        patientBookingDetailsService.getBookedPatientCountForProvider(
            principal.getName(), bookingDate));
  }
}
