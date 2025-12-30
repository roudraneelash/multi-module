package com.multi.module.notifications.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmailMessage {

    private String from;
    private List<String> to;

    private String subject;
    private String htmlBody;
}