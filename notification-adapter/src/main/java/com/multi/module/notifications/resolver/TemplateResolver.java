package com.multi.module.notifications.resolver;

import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.domain.notifications.model.NotificationContext;
import com.multi.module.notifications.enums.NotificationTemplate;
import com.multi.module.notifications.mapper.ContextMapperRegistry;
import com.multi.module.notifications.model.EmailProperties;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
public class TemplateResolver {

    private final SpringTemplateEngine templateEngine;
    private final ContextMapperRegistry contextMapperRegistry;
    private final EmailProperties emailProperties;

    public String resolveTemplate(NotificationContext<?> notificationContext, Notification notification) {

        NotificationTemplate template = NotificationTemplate.getTemplate(notification);

        var contextModel =
                contextMapperRegistry.resolve(
                        notification,
                        notificationContext.getPayload()
                );

        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("data", contextModel);

        thymeleafContext.setVariable("bodyFragment", "body/" + template.getTemplateFileName());
        thymeleafContext.setVariable("logoUrl", emailProperties.logoUrl());
        return templateEngine.process("layout", thymeleafContext);
    }
}