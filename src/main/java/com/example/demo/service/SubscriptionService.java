package com.example.demo.service;

import com.example.demo.entity.Subscription;
import com.example.demo.exception.AlreadySubscribedException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mail.Email;
import com.example.demo.mail.Mailer;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.repository.UserRepository;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionService {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final SubscriptionRepository subscriptionRepository;
  private final Mailer mailer;

  public Subscription subscribe(UUID userId, UUID courseId) {
    if (subscriptionRepository.existsByUserIdAndCourseId(userId, courseId)) {
      throw new AlreadySubscribedException(
          "User " + userId + " already subscribed to course " + courseId);
    }
    var user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User " + userId + " not found"));
    var course =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new NotFoundException("Course " + courseId + " not found"));

    var subscription =
        subscriptionRepository.save(new Subscription(null, user, course, Instant.now()));
    sendConfirmation(user.getEmail(), user.getFirstName(), course.getTitle());
    return subscription;
  }

  @Async
  public void sendConfirmation(String to, String firstName, String courseTitle) {
    try {
      mailer.accept(
          new Email(
              new InternetAddress(to),
              List.of(),
              List.of(),
              "Confirmation d'inscription : " + courseTitle,
              "<p>Bonjour "
                  + firstName
                  + ",</p>"
                  + "<p>Votre inscription au cours <b>"
                  + courseTitle
                  + "</b> est confirmée.</p>",
              List.of()));
    } catch (AddressException e) {
      throw new RuntimeException(e);
    }
  }
}
