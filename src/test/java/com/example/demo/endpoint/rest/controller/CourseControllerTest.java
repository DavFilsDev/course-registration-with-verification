package com.example.demo.endpoint.rest.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.CreateCourseRequest;
import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private CourseService courseService;

  private final Instant start = Instant.parse("2026-09-01T00:00:00Z");
  private final Instant end = Instant.parse("2026-12-01T00:00:00Z");
  private final CreateCourseRequest request = new CreateCourseRequest("Java avancé", start, end);

  @Test
  void create_withValidRequest_shouldReturn201() throws Exception {
    when(courseService.create("Java avancé", start, end))
        .thenReturn(new Course(UUID.randomUUID(), "Java avancé", start, end));

    mockMvc
        .perform(
            post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }

  @Test
  void create_withValidRequest_shouldReturnCreatedCourseInBody() throws Exception {
    when(courseService.create("Java avancé", start, end))
        .thenReturn(new Course(UUID.randomUUID(), "Java avancé", start, end));

    mockMvc
        .perform(
            post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(jsonPath("$.title").value("Java avancé"));
  }

  @Test
  void create_shouldCallServiceWithRequestFields() throws Exception {
    when(courseService.create("Java avancé", start, end))
        .thenReturn(new Course(UUID.randomUUID(), "Java avancé", start, end));

    mockMvc.perform(
        post("/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

    verify(courseService).create(eq("Java avancé"), eq(start), eq(end));
  }
}
