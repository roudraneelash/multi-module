package com.multi.module.notifications.mapper;

import com.multi.module.domain.notifications.enums.Notification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public final class ContextMapperRegistry {

    private final Map<Notification, List<NotificationContextMapper<?, ?>>> registry;

    public ContextMapperRegistry(List<NotificationContextMapper<?, ?>> mappers) {
        this.registry = mappers.stream()
                .collect(Collectors.groupingBy(NotificationContextMapper::getNotification));
    }

    @SuppressWarnings("unchecked")
    public <S,T> T resolve(Notification notification, S source){
        List<NotificationContextMapper<?,?>> candidates= registry.get(notification);
        if(candidates==null || candidates.isEmpty()){
            throw new IllegalStateException("No mappers registered for notification=" +  notification);
        }

        Class<?> sourceClass = source.getClass();
        NotificationContextMapper<S,T> mapper = (NotificationContextMapper<S, T>)
                candidates.stream()
                        .filter(m->m.sourceType().isAssignableFrom(sourceClass))
                        .findFirst()
                        .orElseThrow(()-> new IllegalStateException(
                                "No ContextMapper found for notification="+ notification +
                                        ", sourceType=" + sourceClass.getSimpleName()
                        ));

        return mapper.map(source);
    }
}