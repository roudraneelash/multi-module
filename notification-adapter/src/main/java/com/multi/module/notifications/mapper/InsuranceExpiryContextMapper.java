package com.multi.module.notifications.mapper;

import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.notifications.model.context.InsuranceExpiry;
import org.springframework.stereotype.Component;

@Component
public class InsuranceExpiryContextMapper
        implements NotificationContextMapper<FinanceRequest, InsuranceExpiry> {

    @Override
    public Notification getNotification() {
        return Notification.INSURANCE_EXPIRY;
    }

    @Override
    public Class<FinanceRequest> sourceType() {
        return FinanceRequest.class;
    }

    @Override
    public InsuranceExpiry map(FinanceRequest fr) {
        return InsuranceExpiry.builder()
                .financeEntity_name(fr.getFinanceEntityName())
                .dealer_name(fr.getDealerName())
                .expiry_Date(fr.getInsuranceExpiryDate().toString())
                .build();
    }
}