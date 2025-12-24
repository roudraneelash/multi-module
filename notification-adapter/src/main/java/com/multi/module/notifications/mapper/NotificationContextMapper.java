package com.multi.module.notifications.mapper;

import com.multi.module.domain.notifications.enums.Notification;

public interface NotificationContextMapper<S, T> {

    Notification getNotificationType();   // DOMAIN enum

    Class<S> sourceType();

    T map(S source);
}