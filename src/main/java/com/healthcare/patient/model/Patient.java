package com.healthcare.patient.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "patients")
public class Patient implements Persistable<Long> {
  @Transient public static final String SEQUENCE_NAME = "patients_sequence";

  @Id private long id;

  @NotBlank
  @Size(max = 100)
  @Indexed(unique = true)
  private String firstName;

  @NotBlank
  @Size(max = 100)
  @Indexed(unique = true)
  private String lastName;

  @NotNull
  @Indexed(unique = true)
  private Integer age;

  @NotBlank
  @Size(max = 200)
  @Indexed(unique = true)
  private String address;

  @NotBlank
  @Size(max = 100)
  @Indexed(unique = true)
  private String phoneNumber;

  @NotBlank
  @Size(max = 100)
  @Indexed(unique = true)
  private String email;

  @NotNull
  @Indexed(unique = true)
  private Gender gender;

  @NotBlank
  @Size(max = 100)
  @Indexed(unique = true)
  private String password;

  private String role;

  @NotNull
  @Indexed(unique = true)
  private Status status;

  @CreatedDate private Date createdAt;

  public Patient(String email, String password) {
    this.email = email;
    this.password = password;
  }

  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  @Transient
  public boolean isNew() {
    return this.createdAt == null;
  }

  @CreatedDate private LocalDateTime updatedAt;
}
