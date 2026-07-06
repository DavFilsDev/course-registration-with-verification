package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User create(String firstName, String lastName, String userName, String email) {
    var user = new User(null, firstName, lastName, userName, email);
    return userRepository.save(user);
  }
}
