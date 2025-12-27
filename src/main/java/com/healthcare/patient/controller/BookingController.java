package com.healthcare.patient.controller;

import com.healthcare.patient.model.Booking;
import com.healthcare.patient.service.BookingService;
import com.healthcare.provider.model.ProviderAvailability;
import com.healthcare.provider.model.ProvidersSlot;
import com.healthcare.provider.service.ProviderAvailabilityService;
import com.healthcare.provider.service.ProviderSlotGenerateService;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare/patient/booking")
public class BookingController {

  @Autowired private BookingService bookingService;
  @Autowired private ProviderSlotGenerateService slotService;
  @Autowired private ProviderAvailabilityService availabilityService;

  @PostMapping("/{slotId}")
  public ResponseEntity<?> bookSlot(@PathVariable String slotId, Principal principal) {
    bookingService.bookSlot(slotId, principal.getName());
    return ResponseEntity.ok("Booking confirmed");
  }

  @DeleteMapping("/{bookingId}")
  public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId, Principal principal) {

    bookingService.cancelBooking(bookingId, principal.getName());
    return ResponseEntity.ok("Booking cancelled");
  }

  @GetMapping
  public List<Booking> myBookings(Principal principal) {
    return bookingService.getMyBookings(principal.getName());
  }

  @GetMapping("/slot/{availabilityId}")
  public List<ProvidersSlot> getSlotsForPatient(
      @RequestParam Long providerId,
      @PathVariable String availabilityId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate date) {
    return slotService.getSlotsForPatient(providerId, availabilityId, date);
  }

  @GetMapping("/availability/{providerId}")
  public List<ProviderAvailability> getAllSlotsForProvider(
      @PathVariable Long providerId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate date) {
    return availabilityService.getAllAvailabilities(providerId, date);
  }
}
