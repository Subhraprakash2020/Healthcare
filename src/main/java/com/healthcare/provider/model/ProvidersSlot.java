package com.healthcare.provider.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "provider_slot")
@CompoundIndex(
    name = "unique_provider_date_time",
    def = "{'providerId':1,'date':1,'startTime':1}",
    unique = true)
public class ProvidersSlot {
  @Id private String id;
  private long providerId;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private int maxCapacity;
  private int bookedCount;
  private SlotStatus status;
}
