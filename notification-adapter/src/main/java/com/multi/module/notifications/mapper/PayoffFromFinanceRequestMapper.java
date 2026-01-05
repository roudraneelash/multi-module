package com.multi.module.notifications.mapper;

import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.notifications.model.context.PayoffNotification;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Set;

@Component
public class PayoffFromFinanceRequestMapper
        implements NotificationContextMapper<FinanceRequest, PayoffNotification> {

    private static final Set<Notification> SUPPORTED_NOTIFICATIONS =
            EnumSet.of(
                    Notification.PAYOFF_COMPLETED,
                    Notification.PAYOFF_INITIATED,
                    Notification.PAYOFF_REJECTED
            );

    @Override
    public boolean supports(Notification notification) {
        return SUPPORTED_NOTIFICATIONS.contains(notification);
    }

    @Override
    public Class<FinanceRequest> sourceType() {
        return FinanceRequest.class;
    }

    @Override
    public PayoffNotification map(FinanceRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("FinanceRequest must not be null");
        }

        return PayoffNotification.builder()
                .financeEntityName(request.getFinanceEntityName())
                .payoffStatus(request.getPayoffStatus())
                .creator(request.getCreator())
                .approver(request.getApprover())
                .dealerName(request.getDealerName())
                .assetName(request.getAssetName())
                .outstandingAmount(formatAmount(request.getOutstandingAmount()))
                .build();
    }

    private String formatAmount(java.math.BigDecimal amount) {
        return amount != null ? amount.toPlainString() : null;
    }
}