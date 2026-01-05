package com.multi.module.notifications.mapper;

import com.multi.module.domain.TransformationRequest.model.TransformationRequest;
import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.notifications.model.context.PayoffNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class NotificationMapperTest {

    private PayoffFromFinanceRequestMapper financeRequestMapper;
    private PayoffFromTransformationRequestMapper transformationRequestMapper;

    @BeforeEach
    void setUp() {
        financeRequestMapper = new PayoffFromFinanceRequestMapper();
        transformationRequestMapper = new PayoffFromTransformationRequestMapper();
    }

    // ------------------------------------------------------------------
    // FinanceRequest → PayoffNotification
    // ------------------------------------------------------------------

    @Test
    void shouldMapFinanceRequestToPayoffNotification() {
        FinanceRequest request = buildFinanceRequest();

        PayoffNotification notification = financeRequestMapper.map(request);

        assertNotNull(notification);
        assertEquals("Test Finance Entity", notification.getFinanceEntityName());
        assertEquals("PAYOFF_COMPLETED", notification.getPayoffStatus());
        assertEquals("creator@example.com", notification.getCreator());
        assertEquals("approver@example.com", notification.getApprover());
        assertEquals("Test Dealer", notification.getDealerName());
        assertEquals("Test Asset", notification.getAssetName());
        assertEquals("1000.00", notification.getOutstandingAmount());
    }

    // ------------------------------------------------------------------
    // TransformationRequest → PayoffNotification
    // ------------------------------------------------------------------

    @Test
    void shouldMapTransformationRequestToPayoffNotification() {
        TransformationRequest request = buildTransformationRequest();

        PayoffNotification notification = transformationRequestMapper.map(request);

        assertNotNull(notification);
        assertEquals("Test Finance Entity", notification.getFinanceEntityName());
        assertEquals("INITIATED", notification.getPayoffStatus());
        assertEquals("initiator@example.com", notification.getCreator());
        assertEquals("approver@example.com", notification.getApprover());
        assertEquals("Test Dealer", notification.getDealerName());
        assertEquals("Test Asset", notification.getAssetName());
        assertEquals("1500.0", notification.getOutstandingAmount());
    }

    // ------------------------------------------------------------------
    // Mapper metadata
    // ------------------------------------------------------------------

    @Test
    void shouldReturnCorrectNotificationType() {
        assertTrue(financeRequestMapper.supports(Notification.PAYOFF_COMPLETED));
        assertTrue(transformationRequestMapper.supports(Notification.PAYOFF_COMPLETED));
    }

    @Test
    void shouldReturnCorrectSourceType() {
        assertEquals(FinanceRequest.class, financeRequestMapper.sourceType());
        assertEquals(TransformationRequest.class, transformationRequestMapper.sourceType());
    }

    // ------------------------------------------------------------------
    // Test Fixtures
    // ------------------------------------------------------------------

    private FinanceRequest buildFinanceRequest() {
        return FinanceRequest.builder()
                .financeEntityName("Test Finance Entity")
                .dealerName("Test Dealer")
                .assetName("Test Asset")
                .payoffStatus("PAYOFF_COMPLETED")
                .creator("creator@example.com")
                .approver("approver@example.com")
                .outstandingAmount(new BigDecimal("1000.00"))
                .build();
    }

    private TransformationRequest buildTransformationRequest() {
        return TransformationRequest.builder()
                .financeEntityName("Test Finance Entity")
                .dealerName("Test Dealer")
                .assetName("Test Asset")
                .status("INITIATED")
                .initiator("initiator@example.com")
                .approver("approver@example.com")
                .outstandingAmount(1500.0)
                .build();
    }
}