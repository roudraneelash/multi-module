package com.multi.module.notifications.client;

import com.multi.module.notifications.exception.EmailConstructionException;
import com.multi.module.notifications.exception.EmailSendingException;
import com.multi.module.notifications.exception.InvalidNotificationRequestException;
import com.multi.module.notifications.model.EmailMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmtpClient {

    private final JavaMailSender mailSender;

    /**
     * Primary adapter entry point.
     */
    public void send(EmailMessage emailMessage) {
        validateEmail(emailMessage);

        MimeMessage message;
        try {
            message = createMessage(emailMessage);
        } catch (MessagingException ex) {
            log.error("Failed to construct email message | subject={}",
                    emailMessage.getSubject(), ex);

            throw new EmailConstructionException(
                    "Failed to construct email message for subject=" +
                            emailMessage.getSubject(),
                    ex
            );
        }

        try {
            mailSender.send(message);

            log.info(
                    "Email sent | to={} | subject={}",
                    emailMessage.getTo(),
                    emailMessage.getSubject()
            );

        } catch (Exception ex) {
            log.error(
                    "Failed to send email | to={} | subject={}",
                    emailMessage.getTo(),
                    emailMessage.getSubject(),
                    ex
            );

            throw new EmailSendingException(
                    "Failed to send email to=" + emailMessage.getTo(),
                    ex
            );
        }
    }

    private MimeMessage createMessage(EmailMessage emailMessage)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                StandardCharsets.UTF_8.name()
        );

        if (hasText(emailMessage.getFrom())) {
            helper.setFrom(emailMessage.getFrom().trim());
        }

        helper.setTo(toArray(emailMessage.getTo()));
        helper.setSubject(emailMessage.getSubject().trim());
        helper.setText(emailMessage.getHtmlBody(), true);

        return message;
    }

    private void validateEmail(EmailMessage emailMessage) {

        if (emailMessage == null) {
            throw new InvalidNotificationRequestException(
                    "EmailMessage must not be null"
            );
        }

        if (isEmpty(emailMessage.getTo())) {
            throw new InvalidNotificationRequestException(
                    "At least one recipient is required"
            );
        }

        if (!hasText(emailMessage.getSubject())) {
            throw new InvalidNotificationRequestException(
                    "Email subject must not be empty"
            );
        }

        if (!hasText(emailMessage.getHtmlBody())) {
            throw new InvalidNotificationRequestException(
                    "Email body must not be empty"
            );
        }
    }

    private String[] toArray(List<String> addresses) {
        return addresses.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}