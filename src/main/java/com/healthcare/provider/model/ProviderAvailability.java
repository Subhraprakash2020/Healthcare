package com.healthcare.provider.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "provider_availability")
public class ProviderAvailability {
  @Id private String id;
  private Long providerId;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private int slotDuration;
  private int capacityPerSlot;
}
