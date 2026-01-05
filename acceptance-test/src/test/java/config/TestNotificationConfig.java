// src/test/java/config/TestNotificationConfig.java
package config;

import com.multi.module.notifications.model.EmailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.multi.module.notifications")
@EnableConfigurationProperties(EmailProperties.class)
public class TestNotificationConfig {
}
