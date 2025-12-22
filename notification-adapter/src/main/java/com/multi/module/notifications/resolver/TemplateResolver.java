package com.multi.module.notifications.resolver;

import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.domain.notifications.model.NotificationContext;
import com.multi.module.notifications.enums.NotificationTemplate;
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
    private final ContextResolver contextResolver;

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
                NotificationTemplate.fromDomain(notification);

        Map<String, Object> variables =
                contextResolver.resolve(notificationContext, template);

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(variables);
        thymeleafContext.setVariable("logoUrl", logoUrl);
        thymeleafContext.setVariable("copyrightUrl", copyrightUrl);
        thymeleafContext.setVariable("contactUrl", contactUrl);

        String fileName = template.getTemplateFileName();
        if (fileName != null && fileName.endsWith(".html")) {
            fileName = fileName.substring(0, fileName.length() - 5);
        }
        thymeleafContext.setVariable("bodyFragment", "emails/body/" + fileName);

        return templateEngine.process(
                "emails/layout",
                thymeleafContext
        );
    }
}