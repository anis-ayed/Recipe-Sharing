package com.ayed.recipe_sharing.services.user;

import com.ayed.recipe_sharing.dtos.UserDto;
import com.ayed.recipe_sharing.entities.User;
import java.util.List;

public interface UserService {
  void deleteUserById(Long userId);

  User getUserById(Long userId);

  UserDto updateUserById(Long userId, UserDto user);

  List<UserDto> getAllUsers();
}
