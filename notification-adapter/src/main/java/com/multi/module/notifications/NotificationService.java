package com.multi.module.notifications;

import com.multi.module.domain.notifications.model.NotificationRequest;
import com.multi.module.domain.notifications.port.ObtainNotificationClient;
import com.multi.module.notifications.model.Email;
import com.multi.module.notifications.resolver.EmailResolver;
import com.multi.module.notifications.resolver.TemplateResolver;
import com.multi.module.notifications.client.SmtpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService implements ObtainNotificationClient {

    private final TemplateResolver templateResolver;
    private final EmailResolver emailResolver;
    private final SmtpClient smtpClient;

    @Override
    public void sendNotification(NotificationRequest request) {
        // 1️⃣ Generate HTML from template
        String html = templateResolver.resolveTemplate(
                request.getContext(),
                request.getNotificationType()
        );

        // 2️⃣ Build Email model (subject is built dynamically inside resolver)
        Email email = emailResolver.resolve(
                html,
                request.getRecipients(),
                request.getNotificationType().name()
        );

        // 3️⃣ Send email via SMTP client (which encapsulates JavaMailSender usage)
        smtpClient.send(email);
    }
}