package com.ayed.recipe_sharing.services.user;

import com.ayed.recipe_sharing.dtos.UserDto;
import com.ayed.recipe_sharing.entities.User;
import com.ayed.recipe_sharing.exceptions.ResourceNotFoundException;
import com.ayed.recipe_sharing.repositories.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public void deleteUserById(Long userId) {
    userRepository.delete(getUserById(userId));
  }

  public UserDto updateUserById(Long userId, UserDto userDto) {
    User existingUser = getUserById(userId);
    existingUser.setFirstName(userDto.getFirstName());
    existingUser.setLastName(userDto.getLastName());
    existingUser.setEmail(userDto.getEmail());
    existingUser.setRole(userDto.getRole());
    return saveUser(existingUser);
  }

  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream().map(User::getUserDto).toList();
  }

  public User getUserById(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
  }

  private UserDto saveUser(User user) {
    return userRepository.save(user).getUserDto();
  }
}
