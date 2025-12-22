package com.healthcare.provider.repository;

import com.healthcare.provider.model.Booking;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
  List<Booking> findByPatientId(long patientId);
}
