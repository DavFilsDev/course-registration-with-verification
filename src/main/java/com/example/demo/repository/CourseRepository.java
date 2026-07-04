package com.example.demo.repository;

import java.util.UUID;

import com.example.demo.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, UUID> {}
