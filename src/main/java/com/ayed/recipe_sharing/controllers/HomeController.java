package com.ayed.recipe_sharing.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

  @GetMapping
  public String homeController() {
    return "<h1>Welcome to Recipe Sharing project.</h1>";
  }
}
