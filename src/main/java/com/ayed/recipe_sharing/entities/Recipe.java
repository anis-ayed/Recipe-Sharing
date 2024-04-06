package com.ayed.recipe_sharing.entities;

import com.ayed.recipe_sharing.dtos.RecipeDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private User user;

  private String image;

  private String description;

  private boolean vegetarian;

  private LocalDateTime createdAt;

  @ElementCollection private List<Long> likes = new ArrayList<>();

  public RecipeDto getRecipeDto() {
    return RecipeDto.builder()
        .id(id)
        .title(title)
        .userId(user.getId())
        .image(image)
        .description(description)
        .vegetarian(vegetarian)
        .likes(likes)
        .build();
  }
}
