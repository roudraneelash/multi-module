package com.multi.module.notifications.resolver;

import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.domain.notifications.model.NotificationContext;
import com.multi.module.notifications.enums.NotificationTemplate;
import com.multi.module.notifications.mapper.ContextMapperRegistry;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TemplateResolver {

    private final TemplateEngine templateEngine;
    private final ContextMapperRegistry contextMapperRegistry;

    @Value("${app.email.logo-url}")
    private String logoUrl;

    @Value("${app.email.copyright-url}")
    private String copyrightUrl;

    @Value("${app.email.contact-url}")
    private String contactUrl;

    public String resolveTemplate(
            NotificationContext<?> notificationContext,
            Notification notification
    ) {
        NotificationTemplate template =
                NotificationTemplate.from(notification);

        Object contextModel =
                contextMapperRegistry.resolve(
                        notification,
                        notificationContext.getPayload()
                );

        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("data", contextModel);

        thymeleafContext.setVariable("bodyFragment",
                "emails/body/" + template.getTemplateFileName().replace(".html", "")
        );

        return templateEngine.process("emails/layout", thymeleafContext);
    }
}