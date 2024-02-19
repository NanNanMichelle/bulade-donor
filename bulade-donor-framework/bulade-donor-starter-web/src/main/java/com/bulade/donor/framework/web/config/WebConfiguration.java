package com.bulade.donor.framework.web.config;

import com.bulade.donor.common.enums.WebFilterOrderEnum;
import com.bulade.donor.framework.web.apilog.service.ApiErrorLogFrameworkService;
import com.bulade.donor.framework.web.core.BuladeFastJsonHttpMessageConverter;
import com.bulade.donor.framework.web.core.filter.CacheRequestBodyFilter;
import com.bulade.donor.framework.web.core.handler.GlobalExceptionHandler;
import com.bulade.donor.framework.web.utils.WebFrameworkUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.alibaba.fastjson2.support.config.FastJsonConfig;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.alibaba.fastjson2.JSONWriter.Feature.WriteNullNumberAsZero;
import static com.alibaba.fastjson2.JSONWriter.Feature.WriteNullStringAsEmpty;

@AutoConfiguration
@EnableConfigurationProperties(WebProperties.class)
public class WebConfiguration implements WebMvcConfigurer {

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private WebProperties webProperties;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurePathMatch(configurer, webProperties.getAdminApi());
    }

    /**
     * 设置 API 前缀，仅仅匹配 controller 包下的
     *
     * @param configurer 配置
     * @param api        API 配置
     */
    private void configurePathMatch(PathMatchConfigurer configurer, WebProperties.Api api) {
        AntPathMatcher antPathMatcher = new AntPathMatcher(".");
        configurer.addPathPrefix(api.getPrefix(), clazz -> clazz.isAnnotationPresent(RestController.class)
            && antPathMatcher.match(api.getController(), clazz.getPackage().getName())); // 仅仅匹配 controller 包
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(ApiErrorLogFrameworkService apiErrorLogFrameworkService) {
        return new GlobalExceptionHandler(applicationName, apiErrorLogFrameworkService);
    }

    @Bean
    public WebFrameworkUtils webFrameworkUtils(WebProperties webProperties) {
        return new WebFrameworkUtils(webProperties);
    }

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

    /**
     * 创建 RequestBodyCacheFilter Bean，可重复读取请求内容
     */
    @Bean
    public FilterRegistrationBean<CacheRequestBodyFilter> requestBodyCacheFilter() {
        return createFilterBean(new CacheRequestBodyFilter(), WebFilterOrderEnum.REQUEST_BODY_CACHE_FILTER);
    }

    private <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(order);
        return registration;
    }

    /**
     * 创建 RestTemplate 实例
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

}
