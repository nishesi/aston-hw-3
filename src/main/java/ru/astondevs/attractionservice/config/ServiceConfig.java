package ru.astondevs.attractionservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "ru.astondevs.attractionservice.service",
        "ru.astondevs.attractionservice.mapper"
})
public class ServiceConfig {
}
