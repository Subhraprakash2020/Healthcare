package com.healthcare.provider.exception;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

  private LocalDateTime timestamp;
  private int status; // HTTP status code
  private String error; // Short error (e.g., "Bad Request")
  private String message; // Detailed message
  private String path; // Requested path
  private List<String> details; // Optional: validation errors, etc.
}
