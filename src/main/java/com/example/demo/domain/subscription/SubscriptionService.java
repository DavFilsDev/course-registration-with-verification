package com.example.demo.domain.subscription;

import com.example.demo.domain.course.CourseRepository;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.mail.Email;
import com.example.demo.mail.Mailer;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
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
      throw new IllegalStateException("User already subscribed to this course");
    }
    var user = userRepository.findById(userId).orElseThrow();
    var course = courseRepository.findById(courseId).orElseThrow();

    var subscription =
        subscriptionRepository.save(new Subscription(null, user, course, Instant.now()));

    sendConfirmation(user.getEmail(), user.getFirstName(), course.getTitle());
    return subscription;
  }

  private void sendConfirmation(String to, String firstName, String courseTitle) {
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
