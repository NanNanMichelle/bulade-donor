package com.bulade.donor.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

// CHECKSTYLE:OFF
@Configuration
public class VersionConfiguration {
// CHECKSTYLE:ON

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        var propsConfig = new PropertySourcesPlaceholderConfigurer();
        propsConfig.setLocation(new ClassPathResource("git.properties"));
        propsConfig.setIgnoreResourceNotFound(true);
        propsConfig.setIgnoreUnresolvablePlaceholders(true);
        return propsConfig;
    }

}
