package com.example.demo.domain.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    boolean existsByUserIdAndCourseId(UUID userId, UUID courseId);
}
