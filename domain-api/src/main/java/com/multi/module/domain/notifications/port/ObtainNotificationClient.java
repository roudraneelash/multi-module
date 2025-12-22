package com.multi.module.domain.notifications.port;

import com.multi.module.domain.notifications.model.NotificationRequest;

public interface ObtainNotificationClient {
    public void sendNotification(NotificationRequest request);
}
