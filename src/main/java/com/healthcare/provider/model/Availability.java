package com.healthcare.provider.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Availability")
public class Availability {
  private Long id;
  private DayOfWeek dayOfWeek;
  private LocalTime startTime;
  private LocalTime endTime;
  @Builder.Default private Boolean active = true;
  private Provider provider;
}
