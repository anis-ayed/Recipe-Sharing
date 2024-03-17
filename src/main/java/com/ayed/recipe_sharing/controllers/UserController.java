package com.ayed.recipe_sharing.controllers;

import com.ayed.recipe_sharing.dtos.UserDto;
import com.ayed.recipe_sharing.entities.User;
import com.ayed.recipe_sharing.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService userService;

  /**
   * Récupère un utilisateur par son ID.
   *
   * @param userId L'ID de l'utilisateur à récupérer.
   * @return ResponseEntity avec le DTO de l'utilisateur.
   */
  @Operation(summary = "Get user by ID", description = "Get a user by their unique ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "404", description = "User not found")
      })
  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getUserById(
      @Parameter(description = "User ID", required = true) @PathVariable Long userId) {
    return ResponseEntity.ok(userService.getUserById(userId).getUserDto());
  }

  /**
   * Récupère tous les utilisateurs.
   *
   * @return ResponseEntity contenant la liste des DTO des utilisateurs.
   */
  @Operation(summary = "Get all users", description = "Retrieve all users.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation")})
  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  /**
   * Crée un nouvel utilisateur.
   *
   * @param user Les détails de l'utilisateur à créer.
   * @return ResponseEntity avec le DTO de l'utilisateur créé.
   */
  @Operation(summary = "Create user", description = "Create a new user.")
  @ApiResponses(
      value = {@ApiResponse(responseCode = "200", description = "User created successfully")})
  @PostMapping
  public ResponseEntity<UserDto> createUser(
      @Parameter(description = "User details", required = true) @RequestBody User user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
  }

  /**
   * Met à jour un utilisateur existant.
   *
   * @param userId L'ID de l'utilisateur à mettre à jour.
   * @param user Les nouvelles informations de l'utilisateur.
   * @return ResponseEntity avec le DTO de l'utilisateur mis à jour.
   */
  @Operation(summary = "Update user", description = "Update an existing user.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
      })
  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updateUser(
      @Parameter(description = "User ID", required = true) @PathVariable Long userId,
      @Parameter(description = "User details", required = true) @RequestBody User user) {
    return ResponseEntity.ok(userService.updateUserById(userId, user));
  }

  /**
   * Supprime un utilisateur existant.
   *
   * @param userId L'ID de l'utilisateur à supprimer.
   * @return ResponseEntity indiquant le succès de la suppression.
   */
  @Operation(summary = "Delete user", description = "Delete an existing user.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
      })
  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(
      @Parameter(description = "User ID", required = true) @PathVariable Long userId) {
    userService.deleteUserById(userId);
    return ResponseEntity.noContent().build();
  }
}
