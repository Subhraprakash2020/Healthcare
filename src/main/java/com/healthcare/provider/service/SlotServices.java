package com.healthcare.provider.service;

import com.healthcare.provider.model.ProviderAvailability;
import com.healthcare.provider.model.ProvidersSlot;
import com.healthcare.provider.model.SlotStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SlotServices {
  public List<ProvidersSlot> generateSlots(ProviderAvailability availability, LocalDate date) {
    List<ProvidersSlot> slots = new ArrayList<>();
    LocalTime time = availability.getStartTime();

    while (time.isBefore((availability.getEndTime()))) {
      ProvidersSlot slot = new ProvidersSlot();
      slot.setProviderId(availability.getProviderId());
      slot.setDate(date);
      slot.setStartTime(time);
      slot.setEndTime(time.plusMinutes(availability.getSlotDuration()));
      slot.setMaxCapacity(availability.getCapacityPerSlot());
      slot.setBookedCount(0);
      slot.setStatus(SlotStatus.AVAILABLE);

      slots.add(slot);
      time = time.plusMinutes(availability.getSlotDuration());
    }

    return slots;
  }
}
