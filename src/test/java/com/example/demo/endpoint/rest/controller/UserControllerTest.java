package com.example.demo.endpoint.rest.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private UserService userService;

  private final CreateUserRequest request =
      new CreateUserRequest("Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com");

  @Test
  void create_withValidRequest_shouldReturn201() throws Exception {
    when(userService.create("Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com"))
        .thenReturn(
            new User(UUID.randomUUID(), "Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com"));

    mockMvc
        .perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }

  @Test
  void create_withValidRequest_shouldReturnCreatedUserInBody() throws Exception {
    when(userService.create("Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com"))
        .thenReturn(
            new User(UUID.randomUUID(), "Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com"));

    mockMvc
        .perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(jsonPath("$.firstName").value("Rakoto"))
        .andExpect(jsonPath("$.email").value("rakoto@mail.com"));
  }

  @Test
  void create_shouldCallServiceWithRequestFields() throws Exception {
    when(userService.create("Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com"))
        .thenReturn(
            new User(UUID.randomUUID(), "Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com"));

    mockMvc.perform(
        post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

    verify(userService).create(eq("Rakoto"), eq("Rabe"), eq("rakoto.rabe"), eq("rakoto@mail.com"));
  }
}
