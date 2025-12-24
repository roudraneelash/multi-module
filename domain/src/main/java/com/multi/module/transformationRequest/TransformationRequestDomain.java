package com.multi.module.transformationRequest;

import com.multi.module.domain.TransformationRequest.model.TransformationRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.domain.notifications.model.NotificationContext;
import com.multi.module.domain.notifications.model.NotificationRequest;
import com.multi.module.domain.notifications.model.Recipients;

import java.util.Arrays;
import java.util.List;

public class TransformationRequestDomain {
    public void triggerEmail(){
        TransformationRequest transformationRequest =
                TransformationRequest.builder()
                        .tranformationrequestId(1L)
                        .dealerName("Benelax")
                        .build();

        List<Recipients> recipients = Arrays.asList(Recipients.builder().emailId("ash.roudraneel@gmail.com").build(),
                Recipients.builder().emailId("ashroudra25@gmail.com").build());

        NotificationRequest notificationRequest =
                NotificationRequest.builder()
                        .recipients(recipients)
                        .context(new NotificationContext<>(transformationRequest))
                        .notificationType(Notification.TRANSFORMATION_ASSET)
                        .build();

    }
}
