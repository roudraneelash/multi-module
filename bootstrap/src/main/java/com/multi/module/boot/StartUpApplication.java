package com.multi.module.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.multi.module")
@ConfigurationPropertiesScan("com.multi.module.notifications.model")
public class StartUpApplication {
    public static void main(String[] args){
        SpringApplication.run(StartUpApplication.class,args);
    }
}
