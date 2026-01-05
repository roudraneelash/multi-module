package com.multi.module.notifications.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;

@ConfigurationProperties("email")
public record EmailProperties(String brandName, String logoUrl,String from) {
}