package com.example.outboxpattern.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebMvcConfig implements WebMvcConfigurer {
    private final ApplicationProperties applicationProperties;

    WebMvcConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        ApplicationProperties.Cors propertiesCors = applicationProperties.getCors();
        registry.addMapping(propertiesCors.getPathPattern())
                .allowedMethods(propertiesCors.getAllowedMethods())
                .allowedHeaders(propertiesCors.getAllowedHeaders())
                .allowedOriginPatterns(propertiesCors.getAllowedOriginPatterns())
                .allowCredentials(propertiesCors.isAllowCredentials());
    }
}
