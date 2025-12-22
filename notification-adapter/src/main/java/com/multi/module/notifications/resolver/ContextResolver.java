package com.multi.module.notifications.resolver;

import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.domain.notifications.model.NotificationContext;
import com.multi.module.notifications.enums.NotificationTemplate;
import com.multi.module.notifications.mapper.ContextMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.multi.module.domain.notifications.enums.Notification.PAYOFF_ASSET;

@Component
public class ContextResolver {

    public Map<String, Object> resolve(
            NotificationContext<?> notificationContext,
            NotificationTemplate template
    ) {

        Object payload = notificationContext.getPayload();

        return switch (template) {

            case LIMIT_EXPIRATION -> {
                FinanceRequest fr = cast(payload, FinanceRequest.class);
                yield Map.of("data",
                        ContextMapper.mapToLimitExpiryContext(fr));
            }

            case INSURANCE_EXPIRATION -> {
                FinanceRequest fr = cast(payload, FinanceRequest.class);
                yield Map.of("data",
                        ContextMapper.mapToInsuranceExpiryContext(fr));
            }

            case PAYOFF_ASSET -> {
                FinanceRequest fr = cast(payload, FinanceRequest.class);
                yield Map.of("data",
                        ContextMapper.mapToPayoffAssetContext(fr));
            }

            case TRANSFORMATION_ASSET -> {
                FinanceRequest fr = cast(payload, FinanceRequest.class);
                yield Map.of("data",
                        ContextMapper.mapToPayoffAssetContext(fr));
            }

            default -> throw new IllegalStateException(
                    "Unsupported template " + template
            );
        };
    }

    private <T> T cast(Object payload, Class<T> type) {
        if (!type.isInstance(payload)) {
            throw new IllegalArgumentException(
                    "Invalid payload type. Expected " + type.getSimpleName()
            );
        }
        return type.cast(payload);
    }
}