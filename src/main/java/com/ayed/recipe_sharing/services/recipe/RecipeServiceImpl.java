package com.ayed.recipe_sharing.services.recipe;

import com.ayed.recipe_sharing.dtos.RecipeDto;
import com.ayed.recipe_sharing.dtos.RecipeResponseDto;
import com.ayed.recipe_sharing.entities.Recipe;
import com.ayed.recipe_sharing.entities.User;
import com.ayed.recipe_sharing.exceptions.ResourceNotFoundException;
import com.ayed.recipe_sharing.repositories.RecipeRepository;
import com.ayed.recipe_sharing.services.user.UserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
  private final RecipeRepository recipeRepository;
  private final UserService userService;

  public List<RecipeResponseDto> getAllRecipes() {
    return recipeRepository.findAll().stream().map(Recipe::getRecipeResponseDto).toList();
  }

  public RecipeResponseDto createRecipe(Long userId, RecipeDto recipeDto) {
    User user = userService.getUserById(userId);
    Recipe newRecipe =
        Recipe.builder()
            .title(recipeDto.getTitle())
            .image(recipeDto.getImage())
            .createdAt(LocalDateTime.now())
            .description(recipeDto.getDescription())
            .vegetarian(recipeDto.isVegetarian())
            .user(user)
            .build();
    return recipeRepository.save(newRecipe).getRecipeResponseDto();
  }

  public RecipeResponseDto updateRecipe(Long recipeId, RecipeDto recipeDto) {
    Recipe updatedRecipe = getRecipeById(recipeId);
    updatedRecipe.setDescription(recipeDto.getDescription());
    updatedRecipe.setTitle(recipeDto.getTitle());
    updatedRecipe.setVegetarian(recipeDto.isVegetarian());
    if (recipeDto.getImage() != null) updatedRecipe.setImage(recipeDto.getImage());
    return recipeRepository.save(updatedRecipe).getRecipeResponseDto();
  }

  public void deleteRecipeById(Long recipeId) {
    recipeRepository.delete(getRecipeById(recipeId));
  }

  public RecipeResponseDto likeRecipe(Long recipeId, Long userId) {
    User user = userService.getUserById(userId);
    Recipe recipe = getRecipeById(recipeId);
    if (recipe.getLikes().contains(user.getId())) {
      recipe.getLikes().remove(user.getId());
    } else {
      recipe.getLikes().add(user.getId());
    }
    return recipeRepository.save(recipe).getRecipeResponseDto();
  }

  public Recipe getRecipeById(Long recipeId) {
    return recipeRepository
        .findById(recipeId)
        .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + recipeId));
  }

  public List<RecipeResponseDto> getRecipesByTitle(String title) {
    return recipeRepository.findRecipeByTitleContaining(title).stream()
        .map(Recipe::getRecipeResponseDto)
        .toList();
  }
}
