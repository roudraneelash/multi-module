package com.multi.module.financeRequest;

import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.domain.notifications.model.NotificationContext;
import com.multi.module.domain.notifications.model.NotificationRequest;
import com.multi.module.domain.notifications.model.Recipients;
import com.multi.module.domain.notifications.port.ObtainNotificationClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class FinanceRequestDomain {
    ObtainNotificationClient client;
    public void triggerEmail() {

        FinanceRequest financeRequest = FinanceRequest.builder()
                .financeRequestId(12L)
                .financeEntityId(1)
                .financeEntityName("ABC Finance Ltd")
                .dealerName("Benelax Motors")
                .assetName("Excavator ZX200")
                .serialNumber("ZX200-9988")
                .manufacturer("Hitachi")
                .typeOfBusiness("LOAN")
                .payoffStatus("APPROVED")
                .creator("system-user")
                .approver("manager-1")
                .disbursementDate(LocalDate.now().minusMonths(6))
                .outstandingAmount(new BigDecimal("1250000.00"))
                .internalRefNumber("REF-998877")
                .build();

        List<Recipients> recipients = Arrays.asList(Recipients.builder().emailId("ash.roudraneel@gmail.com").build(),
                Recipients.builder().emailId("ashroudra25@gmail.com").build());

        NotificationRequest notificationRequest =
                NotificationRequest.builder()
                        .recipients(recipients)
                        .context(new NotificationContext<>(financeRequest))
                        .notificationType(Notification.PAYOFF_ASSET)
                        .build();

        client.sendNotification(notificationRequest);
    }
    // Add setter for client
    public void setClient(ObtainNotificationClient client) {
        this.client = client;
    }
}


