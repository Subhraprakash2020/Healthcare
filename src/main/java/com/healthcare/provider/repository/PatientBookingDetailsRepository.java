package com.healthcare.provider.repository;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.healthcare.patient.model.Booking;
import com.healthcare.provider.model.BookingStatus;

public interface PatientBookingDetailsRepository extends MongoRepository<Booking, Long> {
   long countByProviderIdAndBookingDateAndStatus(
      Long providerId, LocalDate bookingDate, BookingStatus status);
}
