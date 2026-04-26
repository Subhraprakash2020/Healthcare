package com.healthcare.patient.service;

import com.healthcare.patient.model.Booking;
import java.util.List;

public interface BookingService {
  void bookSlot(String slotId, String patientEmail);

  void cancelBooking(Long bookingId, String patientEmail);

  List<Booking> getMyBookings(String patientEmail);
}
