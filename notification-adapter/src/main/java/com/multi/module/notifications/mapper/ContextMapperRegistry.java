package com.multi.module.notifications.mapper;

import com.multi.module.domain.notifications.enums.Notification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ContextMapperRegistry {

    private final Map<Key, NotificationContextMapper<?, ?>> registry;

    public ContextMapperRegistry(List<NotificationContextMapper<?, ?>> mappers) {
        this.registry = mappers.stream()
                .collect(Collectors.toMap(
                        m -> new Key(m.getNotificationType(), m.sourceType()),
                        Function.identity()
                ));
    }

    @SuppressWarnings("unchecked")
    public <S, T> T resolve(Notification notification, Object source) {

        Key key = new Key(notification, source.getClass());

        NotificationContextMapper<S, T> mapper =
                (NotificationContextMapper<S, T>) registry.get(key);

        if (mapper == null) {
            throw new IllegalStateException(
                    "No ContextMapper found for notification=" +
                            notification + ", source=" + source.getClass().getSimpleName()
            );
        }

        return mapper.map((S) source);
    }

    private record Key(Notification notification, Class<?> sourceType) {}
}