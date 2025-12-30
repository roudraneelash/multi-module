package com.multi.module.notifications.mapper;

import com.multi.module.domain.TransformationRequest.model.TransformationRequest;
import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.notifications.model.context.PayoffNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContextMapperRegistryTest {

    private ContextMapperRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new ContextMapperRegistry(
                List.of(
                        new PayoffFromFinanceRequestMapper(),
                        new PayoffFromTransformationRequestMapper()
                )
        );
    }

    // ---------------------------------------------------------
    // Positive cases
    // ---------------------------------------------------------

    @Test
    void shouldResolveFinanceRequestMapper() {
        FinanceRequest request = buildFinanceRequest();

        PayoffNotification result =
                registry.resolve(Notification.PAYOFF_COMPLETED, request);

        assertNotNull(result);
        assertEquals("ABC Finance Ltd", result.getFinanceEntityName());
        assertEquals("PAYOFF_COMPLETED", result.getPayoffStatus());
        assertEquals("john.doe", result.getCreator());
        assertEquals("manager.user", result.getApprover());
        assertEquals("XYZ Motors", result.getDealerName());
        assertEquals("Excavator ZX200", result.getAssetName());
        assertEquals("1250000.00", result.getOutstandingAmount());
    }

    @Test
    void shouldResolveTransformationRequestMapper() {
        TransformationRequest request = buildTransformationRequest();

        PayoffNotification result =
                registry.resolve(Notification.PAYOFF_COMPLETED, request);

        assertNotNull(result);
        assertEquals("ABC Finance Ltd", result.getFinanceEntityName());
        assertEquals("INITIATED", result.getPayoffStatus());
        assertEquals("alice.user", result.getCreator());
        assertEquals("manager.user", result.getApprover());
        assertEquals("XYZ Motors", result.getDealerName());
        assertEquals("Excavator ZX200", result.getAssetName());
        assertEquals("1500000.0", result.getOutstandingAmount());
    }

    // ---------------------------------------------------------
    // Negative / fail-fast cases
    // ---------------------------------------------------------

    @Test
    void shouldFailWhenNoMapperFound() {
        FinanceRequest request = buildFinanceRequest();

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> registry.resolve(Notification.INSURANCE_EXPIRY, request)
        );

        assertTrue(ex.getMessage().contains("No mapper found"));
    }

    @Test
    void shouldFailWhenPayloadIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> registry.resolve(Notification.PAYOFF_COMPLETED, null)
        );

        assertEquals("Payload must not be null", ex.getMessage());
    }

    // ---------------------------------------------------------
    // Test Fixtures
    // ---------------------------------------------------------

    private FinanceRequest buildFinanceRequest() {
        return FinanceRequest.builder()
                .financeEntityName("ABC Finance Ltd")
                .dealerName("XYZ Motors")
                .assetName("Excavator ZX200")
                .payoffStatus("PAYOFF_COMPLETED")
                .creator("john.doe")
                .approver("manager.user")
                .outstandingAmount(new BigDecimal("1250000.00"))
                .build();
    }

    private TransformationRequest buildTransformationRequest() {
        return TransformationRequest.builder()
                .financeEntityName("ABC Finance Ltd")
                .dealerName("XYZ Motors")
                .assetName("Excavator ZX200")
                .status("INITIATED")
                .initiator("alice.user")
                .approver("manager.user")
                .outstandingAmount(1500000.0)
                .build();
    }
}