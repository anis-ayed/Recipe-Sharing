package com.ayed.recipe_sharing.repositories;

import com.ayed.recipe_sharing.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
  List<Recipe> findRecipeByTitleContaining(String title);
}
