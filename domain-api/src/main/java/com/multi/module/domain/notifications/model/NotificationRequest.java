package com.multi.module.domain.notifications.model;

import com.multi.module.domain.notifications.enums.Notification;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class NotificationRequest {
    private final Notification notificationType;
    private NotificationContext<?> context;
    private final List<Recipients> recipients;
}