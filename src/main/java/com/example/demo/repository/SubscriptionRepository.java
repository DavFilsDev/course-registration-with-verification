package com.example.demo.repository;

import java.util.UUID;

import com.example.demo.Entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
  boolean existsByUserIdAndCourseId(UUID userId, UUID courseId);
}
