package com.healthcare.admin.repository;

import com.healthcare.admin.model.Admin;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, Long> {
  Optional<Admin> findByEmail(String email);

  Boolean existsByEmail(String email);

  Boolean existsByPassword(String password);
}
