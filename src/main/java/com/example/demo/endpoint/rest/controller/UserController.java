package com.example.demo.endpoint.rest.controller;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<User> create(@RequestBody CreateUserRequest request) {
    var user =
        userService.create(
            request.firstName(), request.lastName(), request.userName(), request.email());
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }
}
