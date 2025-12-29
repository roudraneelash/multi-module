package com.multi.module.notifications;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.domain.notifications.model.NotificationContext;
import com.multi.module.domain.notifications.model.NotificationRequest;
import com.multi.module.domain.notifications.model.Recipients;

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

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
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

    @Test
    void shouldSendPayoffAssetEmail() {

        FinanceRequest financeRequest = FinanceRequest.builder()
                .financeEntityName("ABC Finance Ltd")
                .dealerName("Benelax Motors")
                .assetName("Excavator ZX200")
                .payoffStatus("APPROVED")
                .outstandingAmount(new BigDecimal("1250000.00"))
                .disbursementDate(LocalDate.now().minusMonths(6))
                .build();

        NotificationRequest request =
                NotificationRequest.builder()
                        .notificationType(Notification.PAYOFF_ASSET)
                        .recipients(List.of(
                                Recipients.builder().emailId("r1@test.com").build(),
                                Recipients.builder().emailId("r2@test.com").build()
                        ))
                        .context(new NotificationContext<>(financeRequest))
                        .build();

        notificationService.sendNotification(request);

        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(2, messages.length);

        List<String> recipients =
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

        assertTrue(recipients.contains("r1@test.com"));
        assertTrue(recipients.contains("r2@test.com"));

        String body = GreenMailUtil.getBody(messages[0]);
        assertTrue(body.contains("ABC Finance Ltd"));
        assertTrue(body.contains("Excavator ZX200"));
        assertTrue(body.contains("1250000.00"));
    }
}