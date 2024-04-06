package com.ayed.recipe_sharing.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RecipeDto {
  private Long id;

  @NotEmpty(message = "Title cannot be empty or null.")
  private String title;

  @NotNull(message = "UserId cannot be empty or null.")
  private Long userId;

  private String image;

  @NotEmpty(message = "Description cannot be empty or null.")
  private String description;

  @NotNull(message = "Vegetarian cannot be null.")
  private boolean vegetarian;

  private List<Long> likes;
}
