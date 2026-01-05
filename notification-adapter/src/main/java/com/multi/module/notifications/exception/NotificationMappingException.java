package com.multi.module.notifications.exception;

import com.multi.module.domain.notifications.enums.Notification;
import lombok.Getter;

@Getter
public class NotificationMappingException extends NotificationException {

    private final Notification notification;
    private final Class<?> payloadType;

    public NotificationMappingException(
            Notification notification,
            Class<?> payloadType,
            String message
    ) {
        super(message);
        this.notification = notification;
        this.payloadType = payloadType;
    }

}