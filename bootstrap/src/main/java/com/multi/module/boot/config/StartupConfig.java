package com.multi.module.boot.config;

import com.multi.module.domain.notifications.port.ObtainNotificationClient;
import com.multi.module.financeRequest.FinanceRequestDomain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {

    @Bean
    public FinanceRequestDomain financeRequestDomain(ObtainNotificationClient notificationClient) {
        FinanceRequestDomain financeRequestDomain = new FinanceRequestDomain();
        financeRequestDomain.setClient(notificationClient);
        return financeRequestDomain;
    }
}