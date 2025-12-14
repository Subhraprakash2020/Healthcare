package com.healthcare.provider.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "providers")
@Component
public class Provider {

  @Transient public static final String SEQUENCE_NAME = "provider_sequence";

  @Id
  @NotNull
  @Indexed(unique = true)
  private Long id;

  @Indexed(unique = true)
  @NotBlank
  private String userId;

  @Size(max = 100)
  @NotBlank
  @Pattern(regexp = "^[A-Za-z\\s'-]+$", message = "First name contains invalid characters")
  private String firstName;

  @Size(max = 100)
  @NotBlank
  @Pattern(regexp = "^[A-Za-z\\s'-]+$", message = "Last name contains invalid characters")
  private String lastName;

  @NotBlank
  @Indexed(unique = true)
  @Size(max = 100)
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank
  @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number format")
  private String phone;

  @Size(max = 300)
  private String clinicAddress;

  @NotNull private Gender gender;

  @Builder.Default private Status status = Status.ACTIVE;

  @Builder.Default private LocalDateTime createdAt = LocalDateTime.now();

  @Builder.Default private LocalDateTime updatedAt = LocalDateTime.now();

  @Size(min = 12, max = 30, message = "Password must be more than 12 characters long")
  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12}$",
      message =
          "Password must be 12 characters long, contain at least one letter, one number, and one special character")
  private String passWord;

  private String role = "PROVIDER";
}
