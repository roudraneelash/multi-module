package com.multi.module.boot.config;

import com.multi.module.domain.notifications.port.ObtainNotificationClient;
import com.multi.module.financeRequest.FinanceRequestDomain;
import com.multi.module.transformationRequest.TransformationRequestDomain;
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
    @Bean
    public TransformationRequestDomain TransformationRequestDomain(ObtainNotificationClient notificationClient) {
        TransformationRequestDomain transformationRequestDomain = new TransformationRequestDomain();
        transformationRequestDomain.setClient(notificationClient);
        return transformationRequestDomain;
    }
}