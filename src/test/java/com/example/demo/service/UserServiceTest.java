package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private UserRepository userRepository;
  @InjectMocks private UserService userService;

  @Test
  void create_withValidData_shouldReturnSavedUser() {
    var saved =
        new User(java.util.UUID.randomUUID(), "Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com");
    when(userRepository.save(any(User.class))).thenReturn(saved);

    var result = userService.create("Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com");

    assertThat(result).isEqualTo(saved);
  }

  @Test
  void create_shouldDelegateToRepositorySave() {
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    userService.create("Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com");

    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void create_shouldBuildUserWithProvidedFieldsBeforeSaving() {
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    var result = userService.create("Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com");

    assertThat(result.getFirstName()).isEqualTo("Rakoto");
    assertThat(result.getLastName()).isEqualTo("Rabe");
    assertThat(result.getEmail()).isEqualTo("rakoto@mail.com");
  }
}
