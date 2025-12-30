package com.multi.module.transformationRequest;

import com.multi.module.domain.TransformationRequest.model.TransformationRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.domain.notifications.model.NotificationContext;
import com.multi.module.domain.notifications.model.NotificationRequest;
import com.multi.module.domain.notifications.model.Recipients;
import com.multi.module.domain.notifications.port.ObtainNotificationClient;

import java.util.Arrays;
import java.util.List;

public class TransformationRequestDomain {
    ObtainNotificationClient client;
    public void triggerEmail(){
        TransformationRequest transformationRequest =
                TransformationRequest.builder()
                        .financeEntityName("XYZ Finance Corp")
                        .dealerName("Metro Dealers")
                        .assetName("Bulldozer D85")
                        .status("PAYOFF_INITIATED")
                        .initiator("alice.user")
                        .approver("senior.manager")
                        .outstandingAmount(850000.0)
                        .build();

        List<Recipients> recipients = Arrays.asList(Recipients.builder().emailId("ash.roudraneel@gmail.com").build(),
                Recipients.builder().emailId("ashroudra25@gmail.com").build());

        NotificationRequest notificationRequest =
                NotificationRequest.builder()
                        .recipients(recipients)
                        .context(new NotificationContext<>(transformationRequest))
                        .notificationType(Notification.PAYOFF_COMPLETED)
                        .build();
        client.sendNotification(notificationRequest);
    }
    // Add setter for client
    public void setClient(ObtainNotificationClient client) {
        this.client = client;
    }
}
