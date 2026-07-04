package com.example.demo.endpoint.rest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.exception.AlreadySubscribedException;
import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.SubscriptionService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({SubscriptionController.class, GlobalExceptionHandler.class})
class SubscriptionControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private SubscriptionService subscriptionService;

  @Test
  void subscribe_withValidUserAndCourse_shouldReturn201() throws Exception {
    var userId = UUID.randomUUID();
    var courseId = UUID.randomUUID();

    mockMvc
        .perform(post("/users/" + userId + "/courses/" + courseId + "/subscribe"))
        .andExpect(status().isCreated());
  }

  @Test
  void subscribe_withNonExistingUser_shouldReturn404() throws Exception {
    var userId = UUID.randomUUID();
    var courseId = UUID.randomUUID();
    when(subscriptionService.subscribe(userId, courseId))
        .thenThrow(new NotFoundException("User " + userId + " not found"));

    mockMvc
        .perform(post("/users/" + userId + "/courses/" + courseId + "/subscribe"))
        .andExpect(status().isNotFound());
  }

  @Test
  void subscribe_withAlreadySubscribedUser_shouldReturn409() throws Exception {
    var userId = UUID.randomUUID();
    var courseId = UUID.randomUUID();
    when(subscriptionService.subscribe(userId, courseId))
        .thenThrow(new AlreadySubscribedException("Already subscribed"));

    mockMvc
        .perform(post("/users/" + userId + "/courses/" + courseId + "/subscribe"))
        .andExpect(status().isConflict());
  }
}
