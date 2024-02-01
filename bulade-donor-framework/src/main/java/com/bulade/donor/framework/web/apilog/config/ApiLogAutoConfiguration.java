//package com.bulade.donor.framework.web.apilog.config;
//
//import com.bulade.donor.common.enums.WebFilterOrderEnum;
//import com.bulade.donor.framework.security.config.SecurityProperties;
//import com.bulade.donor.framework.web.apilog.api.ApiAccessLogApi;
//import com.bulade.donor.framework.web.apilog.api.ApiErrorLogApi;
//import com.bulade.donor.framework.web.apilog.filter.ApiAccessLogFilter;
//import com.bulade.donor.framework.web.apilog.service.ApiAccessLogFrameworkService;
//import com.bulade.donor.framework.web.apilog.service.ApiAccessLogFrameworkServiceImpl;
//import com.bulade.donor.framework.web.apilog.service.ApiErrorLogFrameworkService;
//import com.bulade.donor.framework.web.apilog.service.ApiErrorLogFrameworkServiceImpl;
//import com.bulade.donor.framework.web.config.WebConfiguration;
//import jakarta.servlet.Filter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//
//@AutoConfiguration(after = WebConfiguration.class)
//public class ApiLogAutoConfiguration {
//
//    @Bean
//    public ApiAccessLogFrameworkService apiAccessLogFrameworkService(ApiAccessLogApi apiAccessLogApi) {
//        return new ApiAccessLogFrameworkServiceImpl(apiAccessLogApi);
//    }
//
//    @Bean
//    public ApiErrorLogFrameworkService apiErrorLogFrameworkService(ApiErrorLogApi apiErrorLogApi) {
//        return new ApiErrorLogFrameworkServiceImpl(apiErrorLogApi);
//    }
//
//    /**
//     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
//     */
//    @Bean
//    @ConditionalOnProperty(prefix = "donor.access-log", value = "enable", matchIfMissing = true)
//    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(
//        @Value("${spring.application.name}") String applicationName,
//        ApiAccessLogFrameworkService apiAccessLogFrameworkService,
//        SecurityProperties securityProperties
//    ) {
//        ApiAccessLogFilter filter = new ApiAccessLogFilter(applicationName, apiAccessLogFrameworkService, securityProperties);
//        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
//    }
//
//    private <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
//        FilterRegistrationBean<T> registration = new FilterRegistrationBean<>();
//        registration.setFilter(filter);
//        registration.setOrder(order);
//        return registration;
//    }
//
//}
