package com.healthcare.provider.repository;

import com.healthcare.provider.model.ProvidersSlot;
import com.healthcare.provider.model.SlotStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProviderSlotRepository extends MongoRepository<ProvidersSlot, String> {
  List<ProvidersSlot> findByProviderIdAndDateOrderByStartTime(Long providerId, LocalDate date);

  boolean existsByProviderId(Long providerId);

  boolean existsByProviderIdAndDate(Long providerId, LocalDate date);

  boolean existsByAvailabilityId(String availavilityId);

  boolean existsById(String id);

  Optional<ProvidersSlot> findByIdAndProviderId(String id, Long providerId);

  List<ProvidersSlot> findByAvailabilityId(String availabilityId);

  Optional<ProvidersSlot> findByIdAndStatus(String id, SlotStatus status);

  List<ProvidersSlot> findByProviderIdAndAvailabilityIdAndDateOrderByStartTime(
      Long providerId, String availabilityId, LocalDate date);
}
