package com.example.demo.domain.subscription;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
  boolean existsByUserIdAndCourseId(UUID userId, UUID courseId);
}
