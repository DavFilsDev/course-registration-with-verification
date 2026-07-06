package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepository;
import java.time.Instant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {

  private final CourseRepository courseRepository;

  public Course create(String title, Instant startDate, Instant endDate) {
    var course = new Course(null, title, startDate, endDate);
    return courseRepository.save(course);
  }
}
