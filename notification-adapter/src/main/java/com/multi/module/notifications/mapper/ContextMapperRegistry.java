package com.multi.module.notifications.mapper;

import com.multi.module.domain.notifications.enums.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContextMapperRegistry {

    private final List<NotificationContextMapper<?, ?>> mappers;

    @SuppressWarnings("unchecked")
    public <S, T> T resolve(Notification notification, S payload) {

        if (payload == null) {
            throw new IllegalArgumentException("Payload must not be null");
        }

        Class<?> payloadType = payload.getClass();

        var matchingMappers = mappers.stream()
                .filter(m -> m.notificationType() == notification)
                .filter(m -> m.sourceType().isAssignableFrom(payloadType))
                .toList();

        if (matchingMappers.isEmpty()) {
            throw new IllegalStateException(
                    "No mapper found for notification=" + notification +
                    ", payloadType=" + payloadType.getSimpleName()
            );
        }

        if (matchingMappers.size() > 1) {
            throw new IllegalStateException(
                    "Multiple mappers found for notification=" + notification +
                    ", payloadType=" + payloadType.getSimpleName()
            );
        }

        NotificationContextMapper<S, T> mapper =
                (NotificationContextMapper<S, T>) matchingMappers.get(0);

        return mapper.map(payload);
    }
}