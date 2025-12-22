package com.multi.module.domain.notifications.enums;

public enum Notification {
    INSURANCE_EXPIRATION("insurance-expiration"),
    LIMIT_EXPIRATION("limit-expiration"),
    PAYOFF_ASSET("payoff-asset"),
    TRANSFORMATION_ASSET("transformation-asset");

    private final String notificationName;

    Notification(String notificationName){
        this.notificationName=notificationName;
    }
    public String getTemplateFileName() {
        return notificationName;
    }
}