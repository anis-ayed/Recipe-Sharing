package com.ayed.recipe_sharing.services.recipe;

import com.ayed.recipe_sharing.dtos.RecipeDto;
import com.ayed.recipe_sharing.entities.Recipe;
import java.util.List;

public interface RecipeService {
  List<RecipeDto> getAllRecipes();

  RecipeDto createRecipe(Long userId, RecipeDto recipeDto);

  RecipeDto updateRecipe(Long recipeId, RecipeDto recipeDto);

  void deleteRecipeById(Long recipeId);

  Recipe getRecipeById(Long recipeId);

  RecipeDto likeRecipe(Long recipeId, Long userId);
}
