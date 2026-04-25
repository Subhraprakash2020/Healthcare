package com.healthcare.provider.controller;

import com.healthcare.patient.service.BookingService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare/providers/booking")
public class ProviderBookingController {

  @Autowired private BookingService bookingService;

  @GetMapping("/count")
  public ResponseEntity<?> getTodayBookedPatientCount(Principal principal) {
    return ResponseEntity.ok(
        bookingService.getTodayBookedPatientCountForProvider(principal.getName()));
  }
}
