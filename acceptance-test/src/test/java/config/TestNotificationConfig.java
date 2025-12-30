// src/test/java/config/TestNotificationConfig.java
package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.multi.module.notifications")
public class TestNotificationConfig {
}
