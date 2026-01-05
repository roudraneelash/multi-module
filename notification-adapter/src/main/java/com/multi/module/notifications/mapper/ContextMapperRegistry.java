package com.multi.module.notifications.mapper;

import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.notifications.exception.InvalidNotificationRequestException;
import com.multi.module.notifications.exception.NotificationMappingException;
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
        if (notification == null) {
            throw new InvalidNotificationRequestException(
                    "Notification type must not be null"
            );
        }

        if (payload == null) {
            throw new InvalidNotificationRequestException(
                    "Notification payload must not be null for notification=" + notification
            );
        }

        Class<?> payloadType = payload.getClass();
        List<NotificationContextMapper<?, ?>> matchingMappers =
                mappers.stream()
                        .filter(mapper -> mapper.supports(notification))
                        .filter(mapper -> mapper.sourceType().isAssignableFrom(payloadType))
                        .toList();

        if (matchingMappers.isEmpty()) {
            throw new NotificationMappingException(
                    notification,
                    payloadType,
                    "No mapper found for notification=" + notification +
                            ", payloadType=" + payloadType.getSimpleName()
            );
        }

        if (matchingMappers.size() > 1) {
            throw new NotificationMappingException(
                    notification,
                    payloadType,
                    "Multiple mappers found for notification=" + notification +
                            ", payloadType=" + payloadType.getSimpleName() +
                            ", mappers=" + matchingMappers.stream()
                            .map(m -> m.getClass().getSimpleName())
                            .toList()
            );
        }

        NotificationContextMapper<S, T> mapper =
                (NotificationContextMapper<S, T>) matchingMappers.get(0);
        return mapper.map(payload);
    }
}