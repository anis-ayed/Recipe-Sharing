package com.ayed.recipe_sharing.controllers.recipe;

import com.ayed.recipe_sharing.dtos.RecipeDto;
import com.ayed.recipe_sharing.exceptions.ResourceNotFoundException;
import com.ayed.recipe_sharing.services.recipe.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Validated
public class RecipeController {
  private final RecipeService recipeService;

  /**
   * Récupère toutes les recettes.
   *
   * @return ResponseEntity contenant la liste des DTO des recettes.
   */
  @Operation(summary = "Get all recipes", description = "Retrieve all recipes.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation")})
  @GetMapping
  public ResponseEntity<List<RecipeDto>> getAllRecipes() {
    return ResponseEntity.ok(recipeService.getAllRecipes());
  }

  /**
   * Récupère une recette par son ID.
   *
   * @param recipeId L'ID de la recette à récupérer.
   * @return ResponseEntity contenant le DTO de la recette.
   */
  @Operation(summary = "Get recipe by ID", description = "Retrieve a recipe by its unique ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "404", description = "Recipe not found")
      })
  @GetMapping("/{recipeId}")
  public ResponseEntity<RecipeDto> getRecipeById(
      @Parameter(description = "Recipe ID", required = true) @PathVariable Long recipeId) {
    return ResponseEntity.ok(recipeService.getRecipeById(recipeId).getRecipeDto());
  }

  /**
   * Crée une nouvelle recette.
   *
   * @param recipeDto Les détails de la recette à créer.
   * @return ResponseEntity contenant le DTO de la recette créée.
   */
  @Operation(summary = "Create recipe", description = "Create a new recipe.")
  @ApiResponses(
      value = {@ApiResponse(responseCode = "201", description = "Recipe created successfully")})
  @PostMapping
  public ResponseEntity<RecipeDto> createRecipe(@Valid @RequestBody RecipeDto recipeDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(recipeService.createRecipe(recipeDto.getUserId(), recipeDto));
  }

  /**
   * Met à jour une recette existante par son ID.
   *
   * @param recipeId L'ID de la recette à mettre à jour.
   * @param recipeDto Les nouvelles informations de la recette.
   * @return ResponseEntity contenant le DTO de la recette mise à jour.
   */
  @Operation(
      summary = "Update recipe by ID",
      description = "Update an existing recipe by its unique ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Recipe updated successfully"),
        @ApiResponse(responseCode = "404", description = "Recipe not found")
      })
  @PutMapping("/{recipeId}")
  public ResponseEntity<RecipeDto> updateRecipeById(
      @Parameter(description = "Recipe ID", required = true) @PathVariable Long recipeId,
      @Parameter(description = "Updated recipe details", required = true) @RequestBody
          RecipeDto recipeDto) {
    return ResponseEntity.ok(recipeService.updateRecipe(recipeId, recipeDto));
  }

  /**
   * Deletes a recipe by its unique identifier.
   *
   * <p>This endpoint removes a recipe from the system based on the provided `recipeId`.
   *
   * @param recipeId The unique identifier of the recipe to be deleted. Must be a valid Long value.
   * @return A `ResponseEntity` with an empty body and a 204 No Content status code on successful
   *     deletion. If the recipe is not found, a 404 Not Found status code is returned.
   * @throws ResourceNotFoundException If the recipe with the provided ID is not found.
   */
  @DeleteMapping(value = "/{recipeId}")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Recipe deleted successfully."),
        @ApiResponse(responseCode = "404", description = "Recipe not found.")
      })
  public ResponseEntity<?> deleteRecipeById(
      @Parameter(description = "Recipe ID", required = true) @PathVariable Long recipeId) {
    recipeService.deleteRecipeById(recipeId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Likes a recipe for a specific user.
   *
   * @param recipeId The ID of the recipe to like.
   * @param userId The ID of the user who is liking the recipe.
   * @return ResponseEntity containing the liked RecipeDto if successful, or an appropriate error
   *     response.
   */
  @Operation(summary = "Likes a recipe for a specific user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation. Returns the liked RecipeDto"),
        @ApiResponse(responseCode = "404", description = "Recipe or User not found."),
      })
  @PutMapping("/like/{recipeId}/{userId}")
  public ResponseEntity<RecipeDto> likeRecipe(
      @Parameter(description = "ID of the recipe to like", required = true) @PathVariable
          Long recipeId,
      @Parameter(description = "ID of the user who is liking the recipe", required = true)
          @PathVariable
          Long userId) {
    return ResponseEntity.ok(recipeService.likeRecipe(recipeId, userId));
  }
}
