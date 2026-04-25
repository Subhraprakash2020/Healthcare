package com.healthcare.patient.repository;

import com.healthcare.patient.model.Booking;
import com.healthcare.provider.model.BookingStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, Long> {
  List<Booking> findByPatientId(Long patientId);

  Optional<Booking> findByIdAndPatientId(Long id, Long patientId);

  long countByProviderIdAndBookingDateAndStatus(
      Long providerId, LocalDate bookingDate, BookingStatus status);
}
