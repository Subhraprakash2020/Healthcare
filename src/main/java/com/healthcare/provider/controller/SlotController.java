package com.healthcare.provider.controller;

import com.healthcare.provider.model.ProvidersSlot;
import com.healthcare.provider.repository.ProviderSlotRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare/provider")
public class SlotController {
  @Autowired private ProviderSlotRepository providerSlotRepository;

  @GetMapping("/{providerId}/slots")
  public List<ProvidersSlot> getSlots(
      @PathVariable Long providerId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

    return providerSlotRepository.findByProviderIdAndDateOrderByStartTime(providerId, date);
  }
}
