package com.healthcare.patient.model;

import com.healthcare.provider.model.BookingStatus;
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
@Document(collection = "booking")
public class Booking {
  @Id private Long id;
  private Long providerId;
  private Long patientId;
  private String slotId;
  private BookingStatus status;
  private LocalDate bookingDate;
  private LocalTime bookingTime;
}
