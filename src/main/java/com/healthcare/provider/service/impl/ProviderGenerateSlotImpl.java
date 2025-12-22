// package com.healthcare.provider.service.impl;

// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.util.ArrayList;
// import java.util.List;

// import com.healthcare.provider.model.ProviderAvailability;
// import com.healthcare.provider.model.ProvidersSlot;
// import com.healthcare.provider.model.SlotStatus;

// public class ProviderGenerateSlotImpl {

//     public List<ProviderSlot> generateSlots(
//         ProviderAvailability availability,
//         LocalDate date) {
//     List<ProviderSlot> slots = new ArrayList<>();

//     LocalTime time = availability.getStartTime();

//     while (time.isBefore(availability.getEndTime())) {

//         ProviderSlot slot = new ProviderSlot();
//         slot.setProviderId(availability.getProviderId());
//         slot.setDate(date);
//         slot.setStartTime(time);
//         slot.setEndTime(time.plusMinutes(availability.getSlotDuration()));
//         slot.setMaxCapacity(availability.getCapacityPerSlot());
//         slot.setBookedCount(0);
//         slot.setStatus(SlotStatus.AVAILABLE);

//         slots.add(slot);
//         time = time.plusMinutes(availability.getSlotDuration());
//     }
//     return slots;
// }

// }
