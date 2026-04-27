package com.healthcare.provider.repository;

import com.healthcare.patient.model.Booking;
import com.healthcare.provider.model.BookingStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientBookingDetailsRepository extends MongoRepository<Booking, Long> {
  long countByProviderIdAndBookingDateAndStatus(
      Long providerId, LocalDate bookingDate, BookingStatus status);

  List<Booking> findByProviderIdAndBookingDateAndStatus(
      Long providerId, LocalDate bookingDate, BookingStatus status);
}
