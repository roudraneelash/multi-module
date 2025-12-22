package com.multi.module.domain.notifications.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public class NotificationContext<T> {

    private final T payload;

    public NotificationContext(T payload) {
        this.payload = payload;
    }
}
