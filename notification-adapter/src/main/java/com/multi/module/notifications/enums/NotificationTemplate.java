package com.multi.module.notifications.enums;

import com.multi.module.domain.notifications.enums.Notification;

import java.util.EnumMap;
import java.util.Map;

public enum NotificationTemplate {

    INSURANCE_EXPIRATION(Notification.INSURANCE_EXPIRY, "insurance-expiration"),
    LIMIT_EXPIRATION(Notification.LIMIT_EXPIRY, "limit-expiration"),
    PAYOFF_ASSET(Notification.PAYOFF_ASSET, "payoff-asset"),
    TRANSFORMATION_ASSET(Notification.TRANSFORMATION_ASSET, "transformation-asset");

    private final Notification domainType;
    private final String templateFileName;

    NotificationTemplate(Notification domainType, String templateFileName) {
        this.domainType = domainType;
        this.templateFileName = templateFileName;
    }

    public Notification getDomainType() {
        return domainType;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    private static final Map<Notification, NotificationTemplate> LOOKUP = new EnumMap<>(Notification.class);

    static {
        for (NotificationTemplate t : values()) {
            LOOKUP.put(t.domainType, t);
        }
    }

    public static NotificationTemplate from(Notification notification) {
        NotificationTemplate template = LOOKUP.get(notification);
        if (template == null) {
            throw new IllegalStateException(
                    "No template mapped for notification " + notification
            );
        }
        return template;
    }
}