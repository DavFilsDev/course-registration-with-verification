package com.example.demo.endpoint.rest.controller.subscription;

import com.example.demo.domain.subscription.SubscriptionService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/courses/{courseId}")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(
            @PathVariable UUID userId, @PathVariable UUID courseId) {
        subscriptionService.subscribe(userId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}