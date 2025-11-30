package com.healthcare.admin.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestAdmin {
  @NotBlank private String email;
  @NotBlank private String password;
}
