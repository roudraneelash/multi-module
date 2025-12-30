//package com.multi.module.acc;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

import com.multi.module.domain.TransformationRequest.model.TransformationRequest;
import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.domain.notifications.model.NotificationContext;
import com.multi.module.domain.notifications.model.NotificationRequest;
import com.multi.module.domain.notifications.model.Recipients;

import com.multi.module.notifications.NotificationService;
import config.GreenMailTestConfig;
import config.TestNotificationConfig;
import config.ThymeleafTestConfig;

import jakarta.mail.Address;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        TestNotificationConfig.class,
        GreenMailTestConfig.class,
        ThymeleafTestConfig.class
})
class NotificationServiceIT {

    @RegisterExtension
    static GreenMailExtension greenMail =
            new GreenMailExtension(ServerSetupTest.SMTP)
                    .withConfiguration(
                            GreenMailConfiguration.aConfig()
                                    .withUser("test@example.com", "testpass")
                    )
                    .withPerMethodLifecycle(false);

    @Autowired
    private NotificationService notificationService;

    // ---------------------------------------------------------
    // Scenario 1: FinanceRequest + PAYOFF_COMPLETED
    // ---------------------------------------------------------

    @Test
    void shouldSendPayoffCompletedEmail_forFinanceRequest() {

        FinanceRequest financeRequest = FinanceRequest.builder()
                .financeEntityName("ABC Finance Ltd")
                .dealerName("Benelax Motors")
                .assetName("Excavator ZX200")
                .payoffStatus("PAYOFF_COMPLETED")
                .creator("john.doe")
                .approver("manager.user")
                .outstandingAmount(new BigDecimal("1250000.00"))
                .disbursementDate(LocalDate.now().minusMonths(6))
                .build();

        NotificationRequest request = buildNotificationRequest(
                Notification.PAYOFF_COMPLETED,
                financeRequest
        );

        notificationService.sendNotification(request);

        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(2, messages.length);

        assertRecipients(messages, "r1@test.com", "r2@test.com");

        String body = GreenMailUtil.getBody(messages[0]);
        assertTrue(body.contains("ABC Finance Ltd"));
        assertTrue(body.contains("Excavator ZX200"));
        assertTrue(body.contains("1250000.00"));
        assertTrue(body.contains("PAYOFF_COMPLETED"));
    }

    // ---------------------------------------------------------
    // Scenario 2: TransformationRequest + PAYOFF_INITIATED
    // ---------------------------------------------------------

    @Test
    void shouldSendPayoffInitiatedEmail_forTransformationRequest() {

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

        NotificationRequest request = buildNotificationRequest(
                Notification.PAYOFF_COMPLETED,
                transformationRequest
        );

        notificationService.sendNotification(request);

        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(2, messages.length);

        assertRecipients(messages, "r1@test.com", "r2@test.com");

        String body = GreenMailUtil.getBody(messages[0]);
        assertTrue(body.contains("XYZ Finance Corp"));
        assertTrue(body.contains("Bulldozer D85"));
        assertTrue(body.contains("850000.0"));
        assertTrue(body.contains("PAYOFF_INITIATED"));
    }

    // ---------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------

    private NotificationRequest buildNotificationRequest(
            Notification notification,
            Object payload
    ) {
        return NotificationRequest.builder()
                .notificationType(notification)
                .recipients(List.of(
                        Recipients.builder().emailId("r1@test.com").build(),
                        Recipients.builder().emailId("r2@test.com").build()
                ))
                .context(new NotificationContext<>(payload))
                .build();
    }

    private void assertRecipients(MimeMessage[] messages, String... expectedEmails) {

        List<String> actualRecipients =
                Arrays.stream(messages)
                        .flatMap(m -> {
                            try {
                                return Arrays.stream(m.getAllRecipients());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .map(Address::toString)
                        .toList();

        for (String expected : expectedEmails) {
            assertTrue(actualRecipients.contains(expected));
        }
    }
}