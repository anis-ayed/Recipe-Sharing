package com.ayed.recipe_sharing.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "token")
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "token")
  private String token;

  @Column(name = "is_logged_out")
  private boolean loggedOut;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
