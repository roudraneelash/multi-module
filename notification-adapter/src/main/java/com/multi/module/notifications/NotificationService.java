package com.multi.module.notifications;

import com.multi.module.domain.notifications.model.NotificationRequest;
import com.multi.module.domain.notifications.port.ObtainNotificationClient;
import com.multi.module.notifications.client.SmtpClient;
import com.multi.module.notifications.mapper.ContextMapperRegistry;
import com.multi.module.notifications.model.Email;
import com.multi.module.notifications.resolver.EmailResolver;
import com.multi.module.notifications.resolver.TemplateResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService implements ObtainNotificationClient {
    private final ContextMapperRegistry contextMapperRegistry;
    private final EmailResolver emailResolver;
    private final TemplateResolver templateResolver;
    private final SmtpClient smtpClient;

    @Override
    public void sendNotification(NotificationRequest request) {
        String html = templateResolver.resolveTemplate(
                request.getContext(),
                request.getNotificationType()
        );
        Email email = emailResolver.resolve(
                html,
                request.getRecipients(),
                request.getNotificationType().name()
        );
        smtpClient.send(email);
    }
}