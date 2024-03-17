package com.ayed.recipe_sharing.entities;

import com.ayed.recipe_sharing.dtos.UserDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String password;
  private String email;
  private String fullName;

  public UserDto getUserDto() {
    return UserDto.builder().id(id).email(email).fullName(fullName).build();
  }
}
