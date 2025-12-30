package com.multi.module.notifications.resolver;

import com.multi.module.domain.notifications.model.Recipients;
import com.multi.module.notifications.model.EmailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmailResolver {

    @Value("${spring.mail.from}")
    private String fromEmail;

    public EmailMessage resolve(
            String html,
            List<Recipients> recipients,
            String notificationName
    ) {
        List<String> to = recipients.stream()
                .map(Recipients::getEmailId)
                .collect(Collectors.toList());

        String subject = buildSubject(notificationName);

        return EmailMessage.builder()
                .from(fromEmail)
                .to(to)
                .subject(subject)
                .htmlBody(html)
                .build();
    }

    public String buildSubject(String notificationName) {
        // Basic dynamic subject builder. Can be expanded to use templates or i18n.
        return "[Groupe BPCE] " + notificationName.replace('-', ' ').toUpperCase();
    }
}