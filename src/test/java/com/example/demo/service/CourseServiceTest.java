package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepository;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

  @Mock private CourseRepository courseRepository;
  @InjectMocks private CourseService courseService;

  private final Instant start = Instant.parse("2026-09-01T00:00:00Z");
  private final Instant end = Instant.parse("2026-12-01T00:00:00Z");

  @Test
  void create_withValidData_shouldReturnSavedCourse() {
    var saved = new Course(UUID.randomUUID(), "Java avancé", start, end);
    when(courseRepository.save(any(Course.class))).thenReturn(saved);

    var result = courseService.create("Java avancé", start, end);

    assertThat(result).isEqualTo(saved);
  }

  @Test
  void create_shouldDelegateToRepositorySave() {
    when(courseRepository.save(any(Course.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    courseService.create("Java avancé", start, end);

    verify(courseRepository, times(1)).save(any(Course.class));
  }

  @Test
  void create_shouldBuildCourseWithProvidedDatesBeforeSaving() {
    when(courseRepository.save(any(Course.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    var result = courseService.create("Java avancé", start, end);

    assertThat(result.getTitle()).isEqualTo("Java avancé");
    assertThat(result.getStartDate()).isEqualTo(start);
    assertThat(result.getEndDate()).isEqualTo(end);
  }
}
