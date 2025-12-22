package com.multi.module.notifications.client;

import com.multi.module.notifications.model.Email;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class SmtpClient {

    private final JavaMailSender mailSender;

    public void send(MimeMessage message) {
        mailSender.send(message);
    }

    public void send(Email email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            if (email.getFrom() != null) {
                helper.setFrom(email.getFrom());
            }

            if (email.getTo() != null && !email.getTo().isEmpty()) {
                helper.setTo(email.getTo().toArray(new String[0]));
            }

            if (email.getCc() != null && !email.getCc().isEmpty()) {
                helper.setCc(email.getCc().toArray(new String[0]));
            }

            if (email.getBcc() != null && !email.getBcc().isEmpty()) {
                helper.setBcc(email.getBcc().toArray(new String[0]));
            }

            helper.setSubject(email.getSubject());
            helper.setText(email.getHtmlBody(), true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send email", e);
        }
    }
}
