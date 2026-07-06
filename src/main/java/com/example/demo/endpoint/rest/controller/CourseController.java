package com.example.demo.endpoint.rest.controller;

import com.example.demo.dto.CreateCourseRequest;
import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

  private final CourseService courseService;

  @PostMapping
  public ResponseEntity<Course> create(@RequestBody CreateCourseRequest request) {
    var course = courseService.create(request.title(), request.startDate(), request.endDate());
    return ResponseEntity.status(HttpStatus.CREATED).body(course);
  }
}
