package com.ayed.recipe_sharing.services;

import com.ayed.recipe_sharing.dtos.UserDto;
import com.ayed.recipe_sharing.entities.User;
import java.util.List;

public interface UserService {
  UserDto createUser(User user);

  void deleteUserById(Long userId);

  User getUserById(Long userId);

  UserDto updateUserById(Long userId, User user);

  List<UserDto> getAllUsers();
}
