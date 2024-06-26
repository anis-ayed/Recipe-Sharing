package com.ayed.recipe_sharing.entities;

import com.ayed.recipe_sharing.dtos.UserDto;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstname;

  private String lastname;

  private String password;

  private String email;

  @Enumerated(value = EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  public UserDto getUserDto() {
    return UserDto.builder()
        .id(id)
        .email(email)
        .firstname(firstname)
        .lastname(lastname)
        .role(role)
        .build();
  }
}
