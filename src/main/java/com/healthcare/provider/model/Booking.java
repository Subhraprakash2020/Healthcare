package com.healthcare.provider.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookings")
public class Booking {
  @Id private String id;
  private long providerId;
  private long patientId;
  private String slotId;
  private LocalDate date;
  private LocalTime startTime;
  private BookingStatus status;
}
