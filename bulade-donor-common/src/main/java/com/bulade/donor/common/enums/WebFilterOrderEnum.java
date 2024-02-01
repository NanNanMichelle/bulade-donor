package com.bulade.donor.common.enums;

/**
 * Web 过滤器顺序的枚举类，保证过滤器按照符合我们的预期
 */
public class WebFilterOrderEnum {

    public static int CORS_FILTER = Integer.MIN_VALUE;

    public static int TRACE_FILTER = CORS_FILTER + 1;

    public static int REQUEST_BODY_CACHE_FILTER = Integer.MIN_VALUE + 500;

    public static int TENANT_CONTEXT_FILTER = -104; // 需要保证在 ApiAccessLogFilter 前面

    public static int API_ACCESS_LOG_FILTER = -103; // 需要保证在 RequestBodyCacheFilter 后面

    public static int XSS_FILTER = -102;  // 需要保证在 RequestBodyCacheFilter 后面

    // Spring Security Filter 默认为 -100，可见 org.springframework.boot.autoconfigure.security.SecurityProperties 配置属性类

    public static int TENANT_SECURITY_FILTER = -99; // 需要保证在 Spring Security 过滤器后面

    public static int FLOWABLE_FILTER = -98; // 需要保证在 Spring Security 过滤后面

}
