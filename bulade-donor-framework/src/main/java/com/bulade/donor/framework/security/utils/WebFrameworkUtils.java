package com.bulade.donor.framework.security.utils;

import com.bulade.donor.common.core.CommonResponse;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 专属于 web 包的工具类
 */
public class WebFrameworkUtils {

    private WebFrameworkUtils() {
    }

    public static final String REQUEST_ATTRIBUTE_LOGIN_USER_TYPE = "login_user_type";

    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_ID = "login_user_id";

    private static final String REQUEST_ATTRIBUTE_COMMON_RESULT = "common_result";

    public static Integer getLoginUserType(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // 1. 优先，从 Attribute 中获取
        return 1;
    }

    public static Integer getLoginUserType() {
        HttpServletRequest request = getRequest();
        return getLoginUserType(request);
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }

    public static Long getLoginUserId() {
        HttpServletRequest request = getRequest();
        return getLoginUserId(request);
    }

    public static Long getLoginUserId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return 1L;
    }

    public static CommonResponse<?> getCommonResult(ServletRequest request) {
        return (CommonResponse<?>) request.getAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT);
    }

}

