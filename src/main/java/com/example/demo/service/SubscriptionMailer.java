package com.example.demo.service;

import com.example.demo.mail.Email;
import com.example.demo.mail.Mailer;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SubscriptionMailer {

  private final Mailer mailer;

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
