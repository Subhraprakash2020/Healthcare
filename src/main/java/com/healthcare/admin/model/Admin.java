package com.healthcare.admin.model;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "admins")
public class Admin {
  @Transient public static final String SEQUENCE_NAME = "admins_sequence";

  @Id private long id;

  @NotBlank private String username;

  @NotBlank private String email;

  @NotBlank private String password;

  private Date createdAt = new Date();
}
