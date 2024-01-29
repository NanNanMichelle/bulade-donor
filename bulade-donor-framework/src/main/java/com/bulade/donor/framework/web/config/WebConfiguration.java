package com.bulade.donor.framework.web.config;

import com.bulade.donor.framework.web.core.BuladeFastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.alibaba.fastjson2.JSONWriter.Feature.WriteNullNumberAsZero;
import static com.alibaba.fastjson2.JSONWriter.Feature.WriteNullStringAsEmpty;

@Configuration
public class WebConfiguration {

    @Bean
    public CorsFilter corsFilter() {
        var config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.OPTIONS);
        config.setAllowCredentials(true);

        var configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }

    @Bean
    public HttpMessageConverter<Object> buladeFastJsonHttpMessageConverter() {
        BuladeFastJsonHttpMessageConverter converter = new BuladeFastJsonHttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        FastJsonConfig config = new FastJsonConfig();
        config.setWriterFeatures(
            WriteNullStringAsEmpty,
            WriteNullNumberAsZero
        );
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        return converter;
    }

}
