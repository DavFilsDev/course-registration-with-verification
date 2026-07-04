package com.example.demo.Entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {
  @Id @GeneratedValue private UUID id;
  private String firstName;
  private String lastName;
  private String userName;
  private String email;
}
