package com.multi.module.notifications.mapper;

import com.multi.module.domain.notifications.enums.Notification;

public interface NotificationContextMapper<S,T> {

    Notification notificationType();

    Class<S> sourceType();

    T map(S source);
}