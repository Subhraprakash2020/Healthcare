package com.healthcare.patient.service.impl;

import com.healthcare.patient.model.Booking;
import com.healthcare.patient.model.Patient;
import com.healthcare.patient.repository.BookingRepository;
import com.healthcare.patient.repository.PatientRepository;
import com.healthcare.patient.service.BookingService;
import com.healthcare.patient.service.SequenceGeneratorService;
import com.healthcare.provider.model.BookingStatus;
import com.healthcare.provider.model.ProvidersSlot;
import com.healthcare.provider.model.SlotStatus;
import com.healthcare.provider.repository.ProviderSlotRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingServiceImpl implements BookingService {
  @Autowired private MongoTemplate mongoTemplate;

  @Autowired private BookingRepository bookingRepository;

  @Autowired private ProviderSlotRepository slotRepository;

  @Autowired private PatientRepository patientRepository;

  @Autowired private SequenceGeneratorService sequenceGenerator;

  public void saveBooking(Booking booking) {

    booking.setId(sequenceGenerator.generateSequence("booking_sequence"));

    bookingRepository.save(booking);
  }

  @Override
  @Transactional
  public void bookSlot(String slotId, String patientEmail) {

    Patient patient =
        patientRepository
            .findByEmail(patientEmail)
            .orElseThrow(() -> new RuntimeException("Patient Not found"));

    Long patientId = patient.getId();

    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(slotId));
    query.addCriteria(Criteria.where("status").is(SlotStatus.AVAILABLE));

    query.addCriteria(
        Criteria.expr(ComparisonOperators.Lt.valueOf("bookedCount").lessThanValue("maxCapacity")));

    Update update = new Update().inc("bookedCount", 1);

    ProvidersSlot slot =
        mongoTemplate.findAndModify(
            query, update, FindAndModifyOptions.options().returnNew(true), ProvidersSlot.class);

    if (slot == null) {
      throw new RuntimeException("Slot is full or unavailable");
    }

    if (slot.getBookedCount() == slot.getMaxCapacity()) {
      slot.setStatus(SlotStatus.FULL);
      mongoTemplate.save(slot);
    }

    Booking booking = new Booking();
    booking.setId(sequenceGenerator.generateSequence("booking_sequence"));
    booking.setProviderId(slot.getProviderId());
    booking.setPatientId(patientId);
    booking.setSlotId(slotId);
    booking.setBookingDate(slot.getDate());
    booking.setBookingTime(slot.getStartTime());
    booking.setStatus(BookingStatus.CONFIRMED);
    bookingRepository.save(booking);
  }

  @Override
  @Transactional
  public void cancelBooking(Long bookingId, String patientEmail) {
    Patient patient =
        patientRepository
            .findByEmail(patientEmail)
            .orElseThrow(() -> new RuntimeException("Patient Not Found"));

    Long patientId = patient.getId();

    Booking booking =
        bookingRepository
            .findByIdAndPatientId(bookingId, patientId)
            .orElseThrow(() -> new RuntimeException("Booking Not Found"));

    if (booking.getStatus() == BookingStatus.CANCELLED) {
      return;
    }

    booking.setStatus(BookingStatus.CANCELLED);
    bookingRepository.save(booking);

    Query query = new Query(Criteria.where("_id").is(booking.getSlotId()));

    Update update = new Update().inc("bookedCount", -1).set("status", SlotStatus.AVAILABLE);

    mongoTemplate.updateFirst(query, update, ProvidersSlot.class);
  }

  @Override
  public List<Booking> getMyBookings(String patientEmail) {
    Patient patient =
        patientRepository
            .findByEmail(patientEmail)
            .orElseThrow(() -> new RuntimeException("Patient not found"));

    return bookingRepository.findByPatientId(patient.getId());
  }
}
