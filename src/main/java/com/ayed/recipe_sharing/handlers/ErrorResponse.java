package com.ayed.recipe_sharing.handlers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
  private String source;
  private String message;
}
