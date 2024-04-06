package com.ayed.recipe_sharing.services.recipe;

import com.ayed.recipe_sharing.dtos.RecipeDto;
import com.ayed.recipe_sharing.dtos.RecipeResponseDto;
import com.ayed.recipe_sharing.entities.Recipe;
import java.util.List;

public interface RecipeService {
  List<RecipeResponseDto> getAllRecipes();

  RecipeResponseDto createRecipe(Long userId, RecipeDto recipeDto);

  RecipeResponseDto updateRecipe(Long recipeId, RecipeDto recipeDto);

  void deleteRecipeById(Long recipeId);

  Recipe getRecipeById(Long recipeId);

  RecipeResponseDto likeRecipe(Long recipeId, Long userId);

  List<RecipeResponseDto> getRecipesByTitle(String title);
}
