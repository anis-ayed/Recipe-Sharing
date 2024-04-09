package com.ayed.recipe_sharing.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecipeResponseDto {
  private Long id;
  private String title;

  private UserDto user;

  private String image;

  private String description;

  private boolean vegetarian;

  private List<Long> likes;
}
