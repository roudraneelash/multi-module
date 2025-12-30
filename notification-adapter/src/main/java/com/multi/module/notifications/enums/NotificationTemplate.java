package com.multi.module.notifications.enums;

import com.multi.module.domain.notifications.enums.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum NotificationTemplate {
    // Insurance related
    INSURANCE_EXPIRATION(Notification.INSURANCE_EXPIRY, "insurance-expiration"),

    // Limit related
    LIMIT_EXPIRATION(Notification.LIMIT_EXPIRY, "limit-expiration"),

    // Payoff related
    PAYOFF_ASSET_COMPLETED(Notification.PAYOFF_COMPLETED, "payoff-asset"),
    PAYOFF_ASSET_REJECTED(Notification.PAYOFF_REJECTED, "payoff-asset"),
    PAYOFF_ASSET_INITIATED(Notification.PAYOFF_INITIATED, "payoff-asset"),

    // Transformation related
    TRANSFORMATION_ASSET(Notification.TRANSFORMATION_ASSET, "transformation-asset");

    private final Notification notificationType;
    private final String templateFileName;

    public static NotificationTemplate getTemplate(Notification notification) {
        return Arrays.stream(values())
                .filter(template -> template.getNotificationType() == notification)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No template found for notification: " + notification
                ));
    }
}