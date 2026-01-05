package com.multi.module.notifications.exception;

public class EmailSendingException extends NotificationException {

    public EmailSendingException(String message) {
        super(message);
    }

    public EmailSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}