package com.ayed.recipe_sharing.dtos;

import com.ayed.recipe_sharing.entities.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
  private String token;
  private Role role;
  private Long userId;
}
