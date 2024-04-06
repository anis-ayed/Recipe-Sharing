package com.ayed.recipe_sharing.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {
  private String email;
  private String password;
}
