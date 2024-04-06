package com.ayed.recipe_sharing.dtos;

import com.ayed.recipe_sharing.entities.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
  @NotEmpty(message = "Firstname annot be empty or null.")
  private String firstname;

  @NotEmpty(message = "Lastname annot be empty or null.")
  private String lastname;

  @NotEmpty(message = "Email annot be empty or null.")
  private String email;

  @NotEmpty(message = "Password annot be empty or null.")
  @NotBlank(message = "Password cannot be blank.")
  @Size(min = 4, max = 32, message = "Password must be between 4 and 32 characters long.")
  private String password;
}
