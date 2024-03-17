package com.ayed.recipe_sharing.services;

import com.ayed.recipe_sharing.dtos.UserDto;
import com.ayed.recipe_sharing.entities.User;
import com.ayed.recipe_sharing.exceptions.UserExistException;
import com.ayed.recipe_sharing.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserDto createUser(User user) {
    if (emailExists(user.getEmail())) {
      throw new UserExistException("Email already exists in the database!");
    }
    return saveUser(user);
  }

  public void deleteUserById(Long userId) {
    userRepository.delete(getUserById(userId));
  }

  public UserDto updateUserById(Long userId, User user) {
    User existingUser = getUserById(userId);
    existingUser.setFullName(user.getFullName());
    existingUser.setEmail(user.getEmail());
    return saveUser(existingUser);
  }

  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream().map(User::getUserDto).toList();
  }

  public User getUserById(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
  }

  private boolean emailExists(String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  private UserDto saveUser(User user) {
    return userRepository.save(user).getUserDto();
  }
}
