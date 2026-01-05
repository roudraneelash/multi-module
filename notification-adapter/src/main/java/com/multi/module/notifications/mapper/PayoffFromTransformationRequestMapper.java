package com.multi.module.notifications.mapper;

import com.multi.module.domain.TransformationRequest.model.TransformationRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.notifications.exception.InvalidNotificationRequestException;
import com.multi.module.notifications.model.context.PayoffNotification;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Set;

@Component
public class PayoffFromTransformationRequestMapper
        implements NotificationContextMapper<TransformationRequest, PayoffNotification> {

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
    public Class<TransformationRequest> sourceType() {
        return TransformationRequest.class;
    }

    @Override
    public PayoffNotification map(TransformationRequest request) {

        if (request == null) {
            throw new InvalidNotificationRequestException(
                    "TransformationRequest payload must not be null"
            );
        }

        return PayoffNotification.builder()
                .financeEntityName(request.getFinanceEntityName())
                .payoffStatus(request.getStatus())
                .creator(request.getInitiator())
                .approver(request.getApprover())
                .dealerName(request.getDealerName())
                .assetName(request.getAssetName())
                .outstandingAmount(
                        request.getOutstandingAmount() != null
                                ? String.valueOf(request.getOutstandingAmount())
                                : null
                )
                .build();
    }
}