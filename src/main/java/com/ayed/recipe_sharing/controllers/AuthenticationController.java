package com.ayed.recipe_sharing.controllers;

import com.ayed.recipe_sharing.dtos.AuthenticationRequest;
import com.ayed.recipe_sharing.dtos.AuthenticationResponse;
import com.ayed.recipe_sharing.dtos.RegisterRequest;
import com.ayed.recipe_sharing.entities.User;
import com.ayed.recipe_sharing.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class AuthenticationController {

  private final AuthenticationService authService;

  /**
   * Crée un nouvel utilisateur.
   *
   * @param user Les détails de l'utilisateur à créer.
   * @return ResponseEntity avec la AuthenticationResponse.
   */
  @Operation(summary = "Create user", description = "Create a new user.")
  @ApiResponses(
      value = {@ApiResponse(responseCode = "201", description = "User created successfully")})
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @Parameter(description = "User details", required = true) @Valid @RequestBody
          RegisterRequest user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(user));
  }

  /**
   * Authenticates a user.
   *
   * @param authenticationRequest The authentication request containing username and password.
   * @return ResponseEntity with the authentication response.
   */
  @Operation(summary = "Authenticate user")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
      })
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(
      @RequestBody AuthenticationRequest authenticationRequest) {
    return ResponseEntity.ok(authService.authenticate(authenticationRequest));
  }
}
