package com.ayed.recipe_sharing.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private Long id;
  private String email;
  private String fullName;
}
