package com.ayed.recipe_sharing.dtos;

import com.ayed.recipe_sharing.entities.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private Long id;

  @NotEmpty(message = "Firstname annot be empty or null.")
  private String firstname;

  @NotEmpty(message = "Lastname annot be empty or null.")
  private String lastname;

  @NotEmpty(message = "Email annot be empty or null.")
  private String email;

  @NotNull(message = "Role annot be null.")
  private Role role;
}
