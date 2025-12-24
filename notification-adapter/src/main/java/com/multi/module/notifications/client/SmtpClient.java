package com.multi.module.notifications.client;

import com.multi.module.notifications.exception.EmailSendingException;
import com.multi.module.notifications.model.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SmtpClient {

    private static final Logger log = LoggerFactory.getLogger(SmtpClient.class);

    private final JavaMailSender mailSender;

    /**
     * Primary adapter entry point.
     */
    public void send(Email email) {
        validateEmail(email);

        try {
            MimeMessage message = createMessage(email);
            mailSender.send(message);

            log.info(
                    "Email sent | to={} | subject={}",
                    email.getTo(),
                    email.getSubject()
            );

        } catch (MessagingException ex) {
            log.error("Failed to construct email message", ex);
            throw new EmailSendingException("Failed to construct email message", ex);

        } catch (Exception ex) {
            log.error("Failed to send email", ex);
            throw new EmailSendingException("Failed to send email", ex);
        }
    }

    private MimeMessage createMessage(Email email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );

        if (hasText(email.getFrom())) {
            helper.setFrom(email.getFrom().trim());
        }

        helper.setTo(toArray(email.getTo()));

        if (!isEmpty(email.getCc())) {
            helper.setCc(toArray(email.getCc()));
        }

        if (!isEmpty(email.getBcc())) {
            helper.setBcc(toArray(email.getBcc()));
        }

        helper.setSubject(email.getSubject().trim());
        helper.setText(email.getHtmlBody(), true);

        return message;
    }

    private void validateEmail(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("Email must not be null");
        }

        if (isEmpty(email.getTo())) {
            throw new IllegalArgumentException("At least one recipient is required");
        }

        if (!hasText(email.getSubject())) {
            throw new IllegalArgumentException("Email subject must not be empty");
        }

        if (!hasText(email.getHtmlBody())) {
            throw new IllegalArgumentException("Email body must not be empty");
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