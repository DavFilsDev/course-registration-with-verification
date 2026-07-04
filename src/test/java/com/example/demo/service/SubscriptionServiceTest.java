package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.exception.AlreadySubscribedException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mail.Email;
import com.example.demo.mail.Mailer;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

  @Mock private UserRepository userRepository;
  @Mock private CourseRepository courseRepository;
  @Mock private SubscriptionRepository subscriptionRepository;
  @Mock private Mailer mailer;
  @InjectMocks private SubscriptionService subscriptionService;

  private User rakoto;
  private Course javaCourse;

  @BeforeEach
  void setUp() {
    rakoto = new User(UUID.randomUUID(), "Rakoto", "Rabe", "rakoto.rabe", "rakoto@mail.com");
    javaCourse = new Course(UUID.randomUUID(), "Java avancé", null, null);
  }

  @Test
  void subscribe_withValidUserAndCourse_shouldSaveSubscriptionAndSendEmail() {
    when(subscriptionRepository.existsByUserIdAndCourseId(rakoto.getId(), javaCourse.getId()))
        .thenReturn(false);
    when(userRepository.findById(rakoto.getId())).thenReturn(Optional.of(rakoto));
    when(courseRepository.findById(javaCourse.getId())).thenReturn(Optional.of(javaCourse));
    when(subscriptionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    var subscription = subscriptionService.subscribe(rakoto.getId(), javaCourse.getId());

    assertThat(subscription.getUser()).isEqualTo(rakoto);
    assertThat(subscription.getCourse()).isEqualTo(javaCourse);
    verify(mailer).accept(any(Email.class));
  }

  @Test
  void subscribe_withNonExistingUser_shouldThrowNotFoundException() {
    var unknownUserId = UUID.randomUUID();
    when(subscriptionRepository.existsByUserIdAndCourseId(unknownUserId, javaCourse.getId()))
        .thenReturn(false);
    when(userRepository.findById(unknownUserId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> subscriptionService.subscribe(unknownUserId, javaCourse.getId()))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  void subscribe_withAlreadySubscribedUser_shouldThrowAlreadySubscribedException() {
    when(subscriptionRepository.existsByUserIdAndCourseId(rakoto.getId(), javaCourse.getId()))
        .thenReturn(true);

    assertThatThrownBy(() -> subscriptionService.subscribe(rakoto.getId(), javaCourse.getId()))
        .isInstanceOf(AlreadySubscribedException.class);
  }
}
