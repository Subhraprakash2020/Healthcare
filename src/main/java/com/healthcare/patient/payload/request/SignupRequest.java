package com.healthcare.patient.payload.request;

import com.healthcare.patient.model.Gender;
import com.healthcare.patient.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
  @NotBlank private String firstName;
  @NotBlank private String lastName;
  @NotNull private Integer age;
  @NotBlank private String address;
  @NotBlank private String phoneNumber;
  @NotBlank private String email;
  @NotBlank private String password;
  @NotNull private Gender gender;
  @NotNull private Status status;
}
