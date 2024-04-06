package com.ayed.recipe_sharing.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ayed.recipe_sharing.controllers.recipe.RecipeController;
import com.ayed.recipe_sharing.dtos.RecipeDto;
import com.ayed.recipe_sharing.services.recipe.RecipeService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {
  private static final List<Long> likes = new ArrayList<>();
  private static final RecipeDto recipeDto1 =
      RecipeDto.builder()
          .id(1L)
          .title("Recipe 1")
          .description("description recipe 1")
          .userId(1L)
          .vegetarian(false)
          .build();

  private static final RecipeDto recipeDto2 =
      RecipeDto.builder()
          .id(1L)
          .title("Recipe 2")
          .description("description recipe 2")
          .userId(1L)
          .vegetarian(true)
          .build();
  @Autowired private MockMvc mockMvc;

  @Mock private RecipeService recipeService;

  @InjectMocks private RecipeController recipeController;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
  }

  @Test
  public void testGetAllRecipes() throws Exception {
    List<RecipeDto> recipes = Arrays.asList(recipeDto1, recipeDto2);

    when(recipeService.getAllRecipes()).thenReturn(recipes);

    mockMvc
        .perform(get("/api/v1/recipes").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  public void testGetRecipeById() throws Exception {
    Long recipeId = 1L;
    when(recipeService.getRecipeById(recipeId)).thenReturn(null);

    mockMvc
        .perform(
            get("/api/v1/recipes/{recipeId}", recipeId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(recipeId));
  }
}
