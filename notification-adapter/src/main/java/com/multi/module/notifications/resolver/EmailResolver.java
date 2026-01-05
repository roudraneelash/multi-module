package com.multi.module.notifications.resolver;

import com.multi.module.domain.notifications.model.Recipients;
import com.multi.module.notifications.model.EmailMessage;
import com.multi.module.notifications.model.EmailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmailResolver {

    private final EmailProperties emailProperties;

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
                .from(emailProperties.from())
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