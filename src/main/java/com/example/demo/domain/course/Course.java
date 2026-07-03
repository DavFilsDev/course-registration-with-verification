package com.example.demo.domain.course;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Getter
@NoArgsConstructor
public class Course {
  @Id @GeneratedValue private UUID id;
  private String title;
  private Instant startDate;
  private Instant endDate;
}
