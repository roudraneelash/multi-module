package com.multi.module.notifications.model;

import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import lombok.Data;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Data
@Builder
public class Email {

    private String from;
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;

    private String subject;
    private String htmlBody;
}