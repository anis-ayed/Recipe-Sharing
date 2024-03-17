package com.ayed.recipe_sharing.exceptions;

public class UserExistException extends RuntimeException {
  public UserExistException(String message) {
    super(message);
  }
}
