package com.multi.module.notifications.enums;
import com.multi.module.domain.notifications.enums.Notification;

public enum NotificationTemplate {

    INSURANCE_EXPIRATION("insurance-expiration", "insurance-expiration.html"),
    LIMIT_EXPIRATION("limit-expiration", "limit-expiration.html"),
    TRANSFORMATION_ASSET("transformation-asset", "transformation-asset.html"),
    PAYOFF_ASSET("payoff-asset", "payoff-asset.html");

    private final String notificationName;
    private final String templateFileName;

    NotificationTemplate(String notificationName, String templateFileName) {
        this.notificationName = notificationName;
        this.templateFileName = templateFileName;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public static NotificationTemplate fromDomain(Notification notification) {
        return NotificationTemplate.valueOf(notification.name());
    }
}