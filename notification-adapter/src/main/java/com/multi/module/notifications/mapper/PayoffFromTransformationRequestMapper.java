package com.multi.module.notifications.mapper;

import com.multi.module.domain.TransformationRequest.model.TransformationRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.notifications.model.context.PayoffNotification;
import org.springframework.stereotype.Component;

@Component
public class PayoffFromTransformationRequestMapper
        implements NotificationContextMapper<TransformationRequest, PayoffNotification> {

    @Override
    public Notification notificationType() {
        return Notification.PAYOFF_COMPLETED;
    }

    @Override
    public Class<TransformationRequest> sourceType() {
        return TransformationRequest.class;
    }

    @Override
    public PayoffNotification map(TransformationRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("TransformationRequest must not be null");
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