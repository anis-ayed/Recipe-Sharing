package com.ayed.recipe_sharing.services;

import com.ayed.recipe_sharing.dtos.AuthenticationRequest;
import com.ayed.recipe_sharing.dtos.AuthenticationResponse;
import com.ayed.recipe_sharing.dtos.RegisterRequest;
import com.ayed.recipe_sharing.entities.Token;
import com.ayed.recipe_sharing.entities.User;
import com.ayed.recipe_sharing.exceptions.UserExistException;
import com.ayed.recipe_sharing.repositories.TokenRepository;
import com.ayed.recipe_sharing.repositories.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  private final TokenRepository tokenRepository;

  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {

    // check if user already exist. if exist than authenticate the user
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new UserExistException("User already exist!");
    }

    User createdUser =
        User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .role(request.getRole())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();

    User user = userRepository.save(createdUser);

    String jwt = jwtService.generateToken(user);

    saveUserToken(jwt, user);

    return new AuthenticationResponse(jwt, "User created successfully");
  }

  public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequest.getEmail(), authenticationRequest.getPassword()));

    User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
    String jwt = jwtService.generateToken(user);

    revokeAllTokenByUser(user);
    saveUserToken(jwt, user);

    return new AuthenticationResponse(jwt, "Successfully authenticated");
  }

  /**
   * Revokes all tokens associated with a given user.
   *
   * @param user The user whose tokens need to be revoked.
   */
  private void revokeAllTokenByUser(User user) {
    // Find all valid tokens associated with the user
    List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());

    // If there are no valid tokens, return
    if (validTokens.isEmpty()) {
      return;
    }

    // Mark all valid tokens as logged out
    validTokens.forEach(
        t -> {
          t.setLoggedOut(true);
        });

    tokenRepository.saveAll(validTokens);
  }

  private void saveUserToken(String jwt, User user) {
    Token token = new Token();
    token.setToken(jwt);
    token.setLoggedOut(false);
    token.setUser(user);
    tokenRepository.save(token);
  }
}
