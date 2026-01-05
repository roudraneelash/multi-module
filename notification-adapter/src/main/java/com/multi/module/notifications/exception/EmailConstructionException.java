package com.multi.module.notifications.exception;

public class EmailConstructionException extends NotificationException {

    public EmailConstructionException(String message) {
        super(message);
    }

    public EmailConstructionException(String message, Throwable cause) {
        super(message, cause);
    }
}