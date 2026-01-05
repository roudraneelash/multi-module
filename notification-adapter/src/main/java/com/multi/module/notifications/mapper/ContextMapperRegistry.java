package com.multi.module.notifications.mapper;

import com.multi.module.domain.notifications.enums.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContextMapperRegistry {

    private final List<NotificationContextMapper<?, ?>> mappers;

    @SuppressWarnings("unchecked")
    public <S, T> T resolve(Notification notification, S payload) {
        log.debug("Available mappers: {}", mappers);

        if (payload == null) {
            throw new IllegalArgumentException("Payload must not be null");
        }

        Class<?> payloadType = payload.getClass();

        var matchingMappers = mappers.stream()
                .peek(mapper -> log.debug("Checking mapper: {} for notification: {}",
                        mapper.getClass().getSimpleName(), notification))
                .filter(mapper -> {
                    boolean supports = mapper.supports(notification);
                    log.debug("Mapper {} supports notification {}: {}",
                            mapper.getClass().getSimpleName(), notification, supports);
                    return supports;
                })
                .filter(mapper -> {
                    boolean assignable = mapper.sourceType().isAssignableFrom(payloadType);
                    log.debug("Mapper {} can handle payload type {}: {}",
                            mapper.getClass().getSimpleName(), payloadType.getSimpleName(), assignable);
                    return assignable;
                })
                .toList();

        log.info("Found {} matching mappers for notification={}, payloadType={}: {}",
                matchingMappers.size(), notification, payloadType.getSimpleName(), matchingMappers);

        if (matchingMappers.isEmpty()) {
            String error = String.format(
                    "No mapper found for notification=%s, payloadType=%s",
                    notification, payloadType.getSimpleName()
            );
            log.error(error);
            throw new IllegalStateException(error);
        }

        if (matchingMappers.size() > 1) {
            String error = String.format(
                    "Multiple mappers found for notification=%s, payloadType=%s. Mappers: %s",
                    notification, payloadType.getSimpleName(), matchingMappers
            );
            log.error(error);
            throw new IllegalStateException(error);
        }

        NotificationContextMapper<S, T> mapper =
                (NotificationContextMapper<S, T>) matchingMappers.get(0);

        log.info("Using mapper: {} for notification: {}",
                mapper.getClass().getSimpleName(), notification);

        return mapper.map(payload);
    }
}